package com.robot.cation.robotapplication.robot.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.robot.cation.robotapplication.robot.constant.TimeConstants;
import com.robot.cation.robotapplication.robot.http.CallBack;
import com.robot.cation.robotapplication.robot.http.Repository;

/**
 * Created by THINK on 2017/12/25.
 */

public class BackgroundService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        upload();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long triggerAtTime = SystemClock.elapsedRealtime() + TimeConstants.MIN;
        Intent i = new Intent(this, AlarmsReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    private void upload() {



        Repository.getInstance().upload(null, new CallBack() {
            @Override
            public void onSuccess(String body) {

            }

            @Override
            public void onError(String body) {

            }
        });
    }
}
