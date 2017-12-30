package com.robot.cation.robotapplication.robot.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.robot.cation.robotapplication.robot.constant.TimeConstants;

/**
 * Created by THINK on 2017/12/25.
 */

public class BackgroundService extends Service {
    private static final int ON = 1;
    private static final int OFF = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        backgroundTask();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long triggerAtTime = SystemClock.elapsedRealtime() + TimeConstants.HOUR;
        Intent i = new Intent(this, AlarmsReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * 执行的任务列表
     */
    private void backgroundTask() {
        synchronizationTime();

    }

    /**
     * 同步时间
     */
    private void synchronizationTime() {

    }

}
