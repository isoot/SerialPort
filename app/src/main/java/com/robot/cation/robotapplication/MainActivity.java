package com.robot.cation.robotapplication;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.robot.cation.robotapplication.robot.BaseApplication;
import com.robot.cation.robotapplication.robot.activity.PlayerActivity;
import com.robot.cation.robotapplication.robot.controller.Controller;
import com.robot.cation.robotapplication.robot.http.CallBack;
import com.robot.cation.robotapplication.robot.http.remot.Remote;
import com.robot.cation.robotapplication.robot.model.InitBean;
import com.robot.cation.robotapplication.robot.service.LogCheckService;
import com.robot.cation.robotapplication.robot.utils.DeviceUtils;
import com.robot.cation.robotapplication.robot.utils.LogUtils;
import com.robot.cation.robotapplication.robot.utils.SPUtils;

import cn.wch.ch34xuartdriver.CH34xUARTDriver;

public class MainActivity extends AppCompatActivity {
    private static final String ACTION_USB_PERMISSION = "cn.wch.wchusbdriver.USB_PERMISSION";

    private Button advertising;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BaseApplication.driver = new CH34xUARTDriver(
            (UsbManager) getSystemService(Context.USB_SERVICE), this,
            ACTION_USB_PERMISSION);
        // 判断系统是否支持USB HOST
        if (BaseApplication.driver.UsbFeatureSupported()) {
            Controller.getInstance().configSerialPort();
        }
        Intent intent = new Intent(this, LogCheckService.class);

        initView();
        //启动广告
        startService(intent);
        startActivity(new Intent(MainActivity.this, PlayerActivity.class));

        //启动AR摄像头

        remoteInit();
    }

    /**
     * 初始化
     */
    private void remoteInit() {
        if (SPUtils.getInstance().getBoolean("installed")) {
            SPUtils.getInstance().put("installed", true);
            Remote remote = new Remote();
            InitBean initBean = new InitBean();
            initBean.setDeviceNumber(DeviceUtils.getAndroidID());
            initBean.setMac(DeviceUtils.getMacAddress());
            remote.init(initBean, new CallBack() {
                @Override
                public void onSuccess(String body) {
                    LogUtils.w("初始化成功:" + body);
                }

                @Override
                public void onError(String body) {
                    LogUtils.w("初始化失败:" + body);
                }
            });
        }
    }

    private void initView() {
        advertising = findViewById(R.id.advertising);

    }

    @Override
    protected void onDestroy() {
        BaseApplication.driver.CloseDevice();
        super.onDestroy();
    }

}
