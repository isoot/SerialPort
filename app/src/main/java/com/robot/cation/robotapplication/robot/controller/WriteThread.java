package com.robot.cation.robotapplication.robot.controller;

import android.os.Handler;
import android.os.Message;

import com.robot.cation.robotapplication.robot.BaseApplication;
import com.robot.cation.robotapplication.robot.utils.LogUtils;

/**
 * Created by THINK on 2017/12/19.
 */

public class WriteThread extends Thread {

    public static final int WRITE_ERROR = 1;
    public static final int WRITE_SUCCEED = 2;
    private Handler handler;
    private byte[] content;

    public WriteThread(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
//        byte[] to_send = BytesHexStrTranslate.UTF8_GBK(content.getBytes());
        int real = BaseApplication.driver.WriteData(content, content.length);//写数据，第一个参数为需要发送的字节数组，第二个参数为需要发送的字节长度，返回实际发送的字节长度
        Message message = Message.obtain();
        if (real < 0) {
            LogUtils.w("写数据失败!");
            message.what = WRITE_ERROR;
        } else {
            message.what = WRITE_SUCCEED;
        }
        handler.sendMessage(message);
    }

    public void startWrite(byte[] content) {
        this.content = content;
        this.start();
    }

}
