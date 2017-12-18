package com.robot.cation.robotapplication.robot.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.robot.cation.robotapplication.MainActivity;
import com.robot.cation.robotapplication.robot.utils.LogUtils;

public class UpdateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.w("receiver broadcast application install info");
        if (intent.getAction().equals("android.intent.action.PACKAGE_REPLACED")){
            LogUtils.w("start MainActivity.class");
            Intent intent2 = new Intent(context, MainActivity.class);
            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent2);

        }
        //接收安装广播
        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            String packageName = intent.getDataString();
            LogUtils.w("android.intent.action.PACKAGE_ADDED install application package name is "+packageName);
        }
        //接收卸载广播
        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            String packageName = intent.getDataString();
            LogUtils.w("android.intent.action.PACKAGE_REMOVED uninstall application package name is : "+packageName);

        }

    }
}