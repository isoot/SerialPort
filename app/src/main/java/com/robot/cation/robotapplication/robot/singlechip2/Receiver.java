package com.robot.cation.robotapplication.robot.singlechip2;


import android.widget.TextView;

import com.robot.cation.robotapplication.robot.BaseApplication;
import com.robot.cation.robotapplication.robot.constant.ComConfig;
import com.robot.cation.robotapplication.robot.crc.CRC16X25Util;
import com.robot.cation.robotapplication.robot.utils.HexUtil;
import com.robot.cation.robotapplication.robot.utils.LogUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.robot.cation.robotapplication.robot.constant.SingleChipConstant.ADDRESS_SIZE;
import static com.robot.cation.robotapplication.robot.constant.SingleChipConstant.DATA_LENGTH;
import static com.robot.cation.robotapplication.robot.constant.SingleChipConstant.END_SIZE;
import static com.robot.cation.robotapplication.robot.constant.SingleChipConstant.FUNCTION_CODE_SIZE;
import static com.robot.cation.robotapplication.robot.constant.SingleChipConstant.HEAD_SIZE;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipType.ADVERTISING;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipType.COFFEE;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipType.ELECTRICALLY_OPERATED_GATE;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipType.ICE_CREAM;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipType.POWER_BANK;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipType.TEA_WITH_MILK;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipType.TISSUE;

/**
 * 负责接收单片机传过来的数据
 */
public class Receiver {

    //数据长度位置
    public static final int DATA_LENGTH_INDEX = HEAD_SIZE + ADDRESS_SIZE + FUNCTION_CODE_SIZE;
    //数据的基础长度值
    private static final int PACKAGE_BASE_LENGTH = DATA_LENGTH_INDEX + DATA_LENGTH + END_SIZE;
    //====================================配置串口===================================================
    private static int BAUD_RATE = ComConfig.Backdates.BACKDATE_115200.getValue();
    private static byte DATA_BIT = (byte) ComConfig.DataBits.DataBits_8.getValue();
    private static byte STOP_BIT = (byte) ComConfig.StopBits.SHOPLIFTS_1.getValue();
    private static byte PARITY = (byte) ComConfig.Parity.PARITY_NONE_NONE.getValue();
    private static byte FLOW_CONTROL = (byte) ComConfig.FlowControl.FLOW_CONTROL_NONE.getValue();

    //每次读取数据的长度
    private static final int READ_BYTE_SIZE = 500;

    //每一次读取多留出来的数据整备下一次整合 在判断
    private static List<byte[]> redundantData = new ArrayList<>();

    //设备否是开启
    private static boolean isOpen;

    //拦击器集合
    private static List<Interceptor> interceptors = Collections.synchronizedList(new ArrayList());


    /**
     * 开始接收数据
     * 不需要任何参数
     */
    public static boolean startReceive(TextView info) {
        return setParameterApparatus(BAUD_RATE, DATA_BIT, STOP_BIT, PARITY, FLOW_CONTROL, info);
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
    private static boolean setParameterApparatus(int baudRate, byte dataBit, byte stopBit, byte parity, byte flowControl, TextView info) {
        boolean is_ok = false;
        int rectal = BaseApplication.driver.ResumeUsbList();
        // ResumeUsbList方法用于枚举CH34X设备以及打开相关设备
        if (rectal == -1) {
            info.append("打开设备失败!\n");
        } else if (rectal == 0) {
            //对串口设备进行初始化操作
            if (!BaseApplication.driver.UartInit()) {
                info.append("设备初始化失败!......................................\n");
            } else {
                info.append("打开设备成功!\n");
                isOpen = true;
                //配置串口波特率
                if (isOpen && BaseApplication.driver.SetConfig(baudRate, dataBit, stopBit, parity,
                    flowControl)) {
                    info.append("串口设置成功!.........................................\n");
                } else {
                    info.append("串口设置失败!............................................\n");
                }
                readSerialData();//开启读线程读取串口接收的数据
            }
        } else {
            info.append("没有发现设备，请检查你的设备是否正常................................\n");
        }
        return is_ok;
    }

    /**
     * 开始读取数据
     */
    private static void readSerialData() {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                while (true) {
                    byte[] buffer = new byte[READ_BYTE_SIZE];
                    int length = BaseApplication.driver.ReadData(buffer, READ_BYTE_SIZE);
                    if (length > 0) {
                        //截取数组有效长度
                        buffer = Arrays.copyOfRange(buffer, 0, length);
                        disposeData(buffer, length);
                    }
                }
            }
        }).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe();
    }

    /**
     * 处理读取到的数据
     *
     * @param buffer 获取的byte数组
     * @param length 有效长度
     */
    private static void disposeData(byte[] buffer, int length) {
        while (true) {
            //读取这第一包数据的长度
            byte[] len = Arrays.copyOfRange(buffer, DATA_LENGTH_INDEX, DATA_LENGTH_INDEX + DATA_LENGTH);
            //补成四个字节的数据
            byte[] int_len = new byte[]{0, 0, 0, len[0]};
            int dataLength = HexUtil.byteArrayToInt(int_len);
            //如果这组数据等于零则数据无效
            if (dataLength == 0) {
                break;
            }
            //整个包的数据长度 由数据的长度加上两位校验值长度
            int packageLength = dataLength + END_SIZE;
            byte[] packageData = Arrays.copyOfRange(buffer, 0, packageLength);
            //CRC校验部分
            boolean passCRC = CRC16X25Util.isPassCRC(packageData, dataLength);
            if (passCRC) {
                circulationData(packageData);//校验通过
            } else {
                LogUtils.w("校验失败数据:" + Arrays.toString(packageData));
            }
            //控制循环条件
            if (buffer.length > packageLength + PACKAGE_BASE_LENGTH) {
                //还有其他数据
                buffer = Arrays.copyOfRange(buffer, packageLength, buffer.length);
            } else if (buffer.length > packageLength) {
                //TODO 多余的数据处理放在下一次处理中
                buffer = Arrays.copyOfRange(buffer, packageLength, buffer.length);
                break;
            } else {
                break;
            }
        }
    }

    /**
     * 处理接收到的数据
     */
    private static void circulationData(byte[] packageData) {
        List<Interceptor> removeAll = new ArrayList<>();
        //实行拦击功能
        for (int i = 0; i < interceptors.size(); i++) {
            Interceptor interceptor = interceptors.get(i);
            if (interceptor.interceptor(packageData)) {
                //如果是的情况
                removeAll.add(interceptor);
                interceptor.getCallBack().interceptor(packageData);
            }
        }
        interceptors.removeAll(removeAll);
        baseCirculationData(packageData);
    }

    /**
     * 添加拦击器
     *
     * @param interceptor
     */
    public static void addInterceptor(Interceptor interceptor) {
        interceptors.add(interceptor);
    }

    /**
     * 删除拦击器
     *
     * @param interceptor
     */
    public static void removeInterceptor(Interceptor interceptor) {
        interceptors.remove(interceptor);
    }

    /**
     * 添加数据类型处理
     */
    public static void clearSingleType() {
        interceptors.clear();
    }

    /**
     * 有效的基础处理单片机主动发送数据处理
     *
     * @param data
     */
    private static void baseCirculationData(byte[] data) {
        byte address = data[HEAD_SIZE];
        if (address == TEA_WITH_MILK.getValue()) {

        } else if (address == TISSUE.getValue()) {

        } else if (address == ICE_CREAM.getValue()) {

        } else if (address == POWER_BANK.getValue()) {

        } else if (address == ADVERTISING.getValue()) {

        } else if (address == COFFEE.getValue()) {

        } else if (address == ELECTRICALLY_OPERATED_GATE.getValue()) {

        }
    }
}
