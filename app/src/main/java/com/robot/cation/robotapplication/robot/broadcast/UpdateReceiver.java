package com.robot.cation.robotapplication.robot.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.robot.cation.robotapplication.robot.utils.CloseUtils;
import com.robot.cation.robotapplication.robot.utils.LogUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class UpdateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.w("Receiver system Broadcast");
        if (intent.getAction().equals("android.intent.action.PACKAGE_REPLACED")) {
            LogUtils.w("start MainActivity.class");
            startActivity();
        }

    }

    /**
     * 启动Activity
     */
    public static void startActivity() {
        String[] args = {"am start -n com.robot.cation.robotapplication/com.robot.cation.robotapplication.MainActivity"};
        String result = "";
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        Process process = null;
        InputStream errIs = null;
        InputStream inIs = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int read = -1;
            process = processBuilder.start();
            errIs = process.getErrorStream();
            while ((read = errIs.read()) != -1) {
                baos.write(read);
            }
            baos.write(Integer.parseInt("/n"));
            inIs = process.getInputStream();
            while ((read = inIs.read()) != -1) {
                baos.write(read);
            }
            byte[] data = baos.toByteArray();
            result = new String(data);
        } catch (IOException e) {
            LogUtils.w(e);
        } catch (Exception e) {
            LogUtils.w(e);
        } finally {
            CloseUtils.closeIO(errIs, inIs);
            if (process != null) {
                process.destroy();
            }
        }
    }
}