package com.robot.cation.robotapplication.robot.controller;


import com.robot.cation.robotapplication.robot.BaseApplication;
import com.robot.cation.robotapplication.robot.crc.CRC16X25Util;
import com.robot.cation.robotapplication.robot.utils.HexUtil;
import com.robot.cation.robotapplication.robot.utils.LogUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.robot.cation.robotapplication.robot.robot.connector.ControllerRobot.ADDRESS_SIZE;
import static com.robot.cation.robotapplication.robot.robot.connector.ControllerRobot.DATA_LENGTH;
import static com.robot.cation.robotapplication.robot.robot.connector.ControllerRobot.END_SIZE;
import static com.robot.cation.robotapplication.robot.robot.connector.ControllerRobot.FUNCTION_CODE_SIZE;
import static com.robot.cation.robotapplication.robot.robot.connector.ControllerRobot.HEAD_SIZE;


public class Controller {
    public static final int DATA_LENGTH_INDEX_START = HEAD_SIZE + ADDRESS_SIZE + FUNCTION_CODE_SIZE;
    public static final int PACKAGE_BASE_LENGTH = DATA_LENGTH_INDEX_START + DATA_LENGTH + END_SIZE;
    private static boolean isOpen;

    private static final int READ_BYTE_SIZE = 500;

    private static List<byte[]> redundantData = new ArrayList<>();

    /**
     * 配置串口
     */
    public static void configSerialPort() {
        setOpen(ComConfig.Backdates.BACKDATE_115200.getValue(), (byte) ComConfig.DataBits.DataBits_8.getValue(), (byte) ComConfig.StopBits.SHOPLIFTS_1.getValue(), (byte) ComConfig.Parity.PARITY_NONE_NONE.getValue(), (byte) ComConfig.FlowControl.FLOW_CONTROL_NONE.getValue());
    }

    /**
     * 打开设备和配置设备
     *
     * @param baudRate
     * @param dataBit
     * @param stopBit
     * @param parity
     * @param flowControl
     */
    private static void setOpen(int baudRate, byte dataBit, byte stopBit, byte parity, byte flowControl) {
        if (!isOpen) {
            int rectal = BaseApplication.driver.ResumeUsbList();
            if (rectal == -1)// ResumeUsbList方法用于枚举CH34X设备以及打开相关设备
            {
                LogUtils.w("打开设备失败!");
            } else if (rectal == 0) {
                if (!BaseApplication.driver.UartInit()) {//对串口设备进行初始化操作
                    LogUtils.w("设备初始化失败!");
                    return;
                }
                LogUtils.w("打开设备成功!");
                isOpen = true;
                readData();//开启读线程读取串口接收的数据
            } else {
                LogUtils.w("未授权限 请核实");
            }
        }

        //配置串口波特率
        if (isOpen && BaseApplication.driver.SetConfig(baudRate, dataBit, stopBit, parity,
            flowControl)) {
            LogUtils.w("串口设置成功!");
        } else {
            LogUtils.w("串口设置失败!");
        }
    }


    public static void readData() {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                while (true) {
                    byte[] buffer = new byte[READ_BYTE_SIZE];
                    int length = BaseApplication.driver.ReadData(buffer, READ_BYTE_SIZE);
                    if (length > 0) {
                        //合并上一次遗留的数据
                        if (redundantData.size() > 0) {
                            buffer = CRC16X25Util.concatAll(buffer, redundantData.get(0));
                        }
                        toHexString(buffer, length);
                    } else {
                        redundantData.clear();
                    }
                }
            }
        }).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe();
    }


    /**
     * 将byte[]数组转化为String类型
     *
     * @param arg    需要转换的byte[]数组
     * @param length 需要转换的数组长度
     * @return 转换后的String队形
     */
    private static void toHexString(byte[] arg, int length) {
        redundantData.clear();
        while (true) {
            //取出一个字节的数据
            byte[] len = Arrays.copyOfRange(arg, DATA_LENGTH_INDEX_START, DATA_LENGTH_INDEX_START + DATA_LENGTH);
            //补成四个字节的数据
            byte[] int_len = new byte[]{0, 0, 0, len[0]};
            int dataLength = HexUtil.byteArrayToInt(int_len);
            if (dataLength == 0) {
                break;
            }
            int packageLength = dataLength + END_SIZE;
            byte[] packageData = Arrays.copyOfRange(arg, 0, packageLength);
            //CRC校验部分
            LogUtils.w("接收到单片机数据:" + Arrays.toString(packageData));
            boolean passCRC = CRC16X25Util.isPassCRC(packageData, dataLength);
            if (passCRC) {
                //校验通过
                ReceiveController.addReceive(packageData);
            } else {
                LogUtils.w("校验失败数据:" + Arrays.toString(packageData));
            }

            //控制循环条件
            if (arg.length > packageLength + PACKAGE_BASE_LENGTH) {
                //还有其他数据
                arg = Arrays.copyOfRange(arg, packageLength, arg.length);
            } else if (arg.length > packageLength) {
                //多余的数据处理放在下一次处理中
                arg = Arrays.copyOfRange(arg, packageLength, arg.length);
                redundantData.add(arg);
                break;
            } else {
                break;
            }
        }
    }

    /**
     * 写数据
     *
     * @param content
     */
    public static void startWrite(final byte[] content) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                //byte[] to_send = BytesHexStrTranslate.UTF8_GBK(content);//ff fe 02 01 0b 00 00 00 01 fe ff 0c a3
                LogUtils.e("发送数据:" + Arrays.toString(content));
                int real = BaseApplication.driver.WriteData(content, content.length);//写数据，第一个参数为需要发送的字节数组，第二个参数为需要发送的字节长度，返回实际发送的字节长度
                e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe();
    }

}
