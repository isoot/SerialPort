package com.robot.cation.robotapplication.robot.broadcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.robot.cation.robotapplication.robot.utils.ActivityUtils;
import com.robot.cation.robotapplication.robot.utils.LogUtils;

public class UpdateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.w("Receiver system Broadcast");
        if (intent.getAction().equals("android.intent.action.PACKAGE_REPLACED")) {
            LogUtils.w("start MainActivity.class");
            startActivity(context);
        }

    }

    /**
     * 启动Activity
     */
    public void startActivity(Context context) {
        Activity topActivity = ActivityUtils.getTopActivity();
        if (topActivity == null) {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(new ComponentName("com.robot.cation.robotapplication", "com.robot.cation.robotapplication.MainActivity"));
            context.startActivity(intent);
        } else {
            LogUtils.w("存在com.robot.cation.robotapplication.MainActivity");
        }
    }
}