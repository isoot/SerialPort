package com.robot.cation.robotapplication;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.robot.cation.robotapplication.robot.BaseApplication;
import com.robot.cation.robotapplication.robot.activity.PlayerActivity;
import com.robot.cation.robotapplication.robot.controller.Controller;

import cn.wch.ch34xuartdriver.CH34xUARTDriver;

public class MainActivity extends AppCompatActivity {
    private static final String ACTION_USB_PERMISSION = "cn.wch.wchusbdriver.USB_PERMISSION";

    private Button button;

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

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
                startActivity(intent);
            }
        });
        Intent intent = new Intent(this, IntentService.class);
        startService(intent);
    }


    @Override
    protected void onDestroy() {
        BaseApplication.driver.CloseDevice();
        super.onDestroy();
    }

}
