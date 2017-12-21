package com.robot.cation.robotapplication.robot.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.robot.cation.robotapplication.robot.utils.FileUtils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by THINK on 2017/12/19.
 */

public class LogCheckService extends IntentService {


    public static final int LOG_SIZE_1G = 1;

    public LogCheckService(){
        super("LogCheckService");

    }
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public LogCheckService(String name) {
        super(name);
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        long logSize = FileUtils.getDirectoryLength(FileUtils.getBaseFileLogPath());
        long crashSize = FileUtils.getDirectoryLength(FileUtils.getBaseFileCrashPath());
        FileUtils.deleteDir(FileUtils.getBaseFileAPKPath());
        int size = FileUtils.byteFitMemorySizeInt(logSize);
        if (size > LOG_SIZE_1G) {
            //删除log日志 MMddHH
            Date now = new Date(System.currentTimeMillis());
            Format FORMAT = new SimpleDateFormat("MMddHH", Locale.getDefault());
            String time = FORMAT.format(now);
            FileUtils.deleteDir(FileUtils.getBaseFileLogPath(), Integer.parseInt(time), 3);
        }
        size = FileUtils.byteFitMemorySizeInt(crashSize);
        if (size > LOG_SIZE_1G) {
            //删除crash日志
            //删除log日志 MMddHH
            Date now = new Date(System.currentTimeMillis());
            Format FORMAT = new SimpleDateFormat("MMddHHmm", Locale.getDefault());
            String time = FORMAT.format(now);
            FileUtils.deleteDir(FileUtils.getBaseFileCrashPath(), Integer.parseInt(time), 3);
        }
    }

}
