package com.robot.cation.robotapplication.robot.controller;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.robot.cation.robotapplication.robot.BaseApplication;
import com.robot.cation.robotapplication.robot.utils.LogUtils;

/**
 * 数据格式
 * 包头+设备地址+功能码+长度+数据+包尾
 * 长度=等于整个包长度
 * 包头为固定：0xFFFE 包尾：0xFEFF
 */
public class Controller {
    private static Controller instance;
    private boolean isOpen;

    private Controller() {
        readThread = new ReadThread(readHandler);
        writeThread = new WriteThread(writeHandler);
    }

    public static Controller getInstance() {
        if (null == instance) {
            instance = new Controller();
        }
        return instance;
    }

    private ReadThread readThread;

    private Handler readHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            readMessage(msg);
        }
    };

    private WriteThread writeThread;

    private Handler writeHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            writeMessage(msg);
        }

    };


    public void configSerialPort() {
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
    private void setOpen(int baudRate, byte dataBit, byte stopBit, byte parity, byte flowControl) {
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
                readThread.readStart(isOpen);//开启读线程读取串口接收的数据
                writeThread.startWrite("1");
            } else {
                LogUtils.w("未授权限 请核实");
            }
        }

        if (isOpen && BaseApplication.driver.SetConfig(baudRate, dataBit, stopBit, parity,//配置串口波特率，函数说明可参照编程手册
            flowControl)) {
            LogUtils.w("串口设置成功!");
        } else {
            LogUtils.w("串口设置失败!");
        }
    }

    /**
     * 处理获得的数据
     *
     * @param msg
     */
    private void readMessage(Message msg) {
        if (msg.what == ReadThread.READ_SUCCEED) {
            //成功
            LogUtils.w("read data:" + (String) msg.obj);
        } else {
            //失败
        }
    }

    /**
     * 写数据获得结果
     *
     * @param msg
     */
    private void writeMessage(Message msg) {
        if (msg.what == WriteThread.WRITE_SUCCEED) {
            //成功

        } else {
            //失败
        }
    }


}
