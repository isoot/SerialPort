package com.robot.cation.robotapplication;

import android.content.Context;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.robot.cation.robotapplication.robot.BaseApplication;
import com.robot.cation.robotapplication.robot.controller.Controller;
import com.robot.cation.robotapplication.robot.utils.LogUtils;

import cn.wch.ch34xuartdriver.CH34xUARTDriver;

public class MainActivity extends AppCompatActivity {
    private static final String ACTION_USB_PERMISSION = "cn.wch.wchusbdriver.USB_PERMISSION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BaseApplication.driver = new CH34xUARTDriver(
            (UsbManager) getSystemService(Context.USB_SERVICE), this,
            ACTION_USB_PERMISSION);
        if (!BaseApplication.driver.UsbFeatureSupported())// 判断系统是否支持USB HOST
        {
            LogUtils.w("控制器不支持 USB HOST");
        }
        Controller.getInstance().configSerialPort();
    }


    @Override
    protected void onDestroy() {
        BaseApplication.driver.CloseDevice();
        super.onDestroy();
    }

}
