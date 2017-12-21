package com.robot.cation.robotapplication.robot.controller;


import android.os.Handler;
import android.os.Message;

import com.robot.cation.robotapplication.robot.BaseApplication;
import com.robot.cation.robotapplication.robot.utils.BytesHexStrTranslate;
import com.robot.cation.robotapplication.robot.utils.LogUtils;

import java.util.Arrays;

/**
 * 读数据线程
 */
public class ReadThread extends Thread {
    private static final int READ_BYTE_SIZE = 500;
    public static final int READ_ERROR = 1;
    public static final int READ_SUCCEED = 2;
    private boolean canRead;
    private Handler handler;

    public ReadThread(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {

        byte[] buffer = new byte[READ_BYTE_SIZE];

        while (true) {
            Message msg = Message.obtain();
            if (!canRead) {
                break;
            }
            int length = BaseApplication.driver.ReadData(buffer, READ_BYTE_SIZE);
            if (length > 0) {
                msg.what = READ_SUCCEED;
                msg.obj = toHexString(buffer, length);
            } else {
                msg.what = READ_ERROR;
            }
            handler.sendMessage(msg);
        }
    }

    public void readStart(boolean canRead) {
        this.canRead = canRead;
        this.start();
    }

    /**
     * 将byte[]数组转化为String类型
     *
     * @param arg    需要转换的byte[]数组
     * @param length 需要转换的数组长度
     * @return 转换后的String队形
     */

    private String toHexString(byte[] arg, int length) {
        String result = null;
        if (arg != null) {
            for (int i = 0; i < length; i++) {
                LogUtils.w("原数据:" + Arrays.toString(arg));
                byte[] temp = new byte[length];
                System.arraycopy(arg, 0, temp, 0, length);
                byte[] bytes = BytesHexStrTranslate.GBK_UTF8(temp);
                result = new String(bytes);
            }
            return result;
        }
        return "";
    }
}
