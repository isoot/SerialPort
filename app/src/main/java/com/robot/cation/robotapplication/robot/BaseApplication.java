package com.robot.cation.robotapplication.robot;

import android.app.Application;
import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.danikula.videocache.HttpProxyCacheServer;
import com.robot.cation.robotapplication.robot.constant.MemoryConstants;
import com.robot.cation.robotapplication.robot.http.CallBack;
import com.robot.cation.robotapplication.robot.http.remot.Remote;
import com.robot.cation.robotapplication.robot.location.LocationOption;
import com.robot.cation.robotapplication.robot.model.InitBean;
import com.robot.cation.robotapplication.robot.player.BaseFileNameGenerator;
import com.robot.cation.robotapplication.robot.utils.CrashUtils;
import com.robot.cation.robotapplication.robot.utils.DeviceUtils;
import com.robot.cation.robotapplication.robot.utils.FileUtils;
import com.robot.cation.robotapplication.robot.utils.LogUtils;
import com.robot.cation.robotapplication.robot.utils.SPUtils;
import com.robot.cation.robotapplication.robot.utils.Utils;

import java.io.File;

import cn.jpush.android.api.JPushInterface;
import cn.wch.ch34xuartdriver.CH34xUARTDriver;

public class BaseApplication extends Application {
    private LocationListener listener = new LocationListener();
    public static double latitude = 0f;    //获取纬度信息
    public static double longitude = 0f;    //获取经度信息

    /**
     * 设备串口
     */
    public static CH34xUARTDriver driver;

    private HttpProxyCacheServer proxy;

    /**
     * 配置
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        JPushInterface.setAlias(this, 0, DeviceUtils.getAndroidID());
        initLog();
        CrashUtils.init(FileUtils.getBaseFileCrashPath());
        LocationOption.initLocationOption(this, listener);
    }



    /**
     * 配置LOG 初始化信息
     */
    private void initLog() {
        LogUtils.Config mConfig = LogUtils.getConfig();
        mConfig.setLogSwitch(true)
            .setConsoleSwitch(true)
            .setGlobalTag("RobotApplication")
            .setLogHeadSwitch(true)
            .setLog2FileSwitch(true)
            .setDir(FileUtils.getBaseFileLogPath())
            .setBorderSwitch(true)
            .setConsoleFilter(LogUtils.V)
            .setFileFilter(LogUtils.V);
    }


    public class LocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            latitude = location.getLatitude();    //获取纬度信息
            longitude = location.getLongitude();    //获取经度信息
            float radius = location.getRadius();    //获取定位精度，默认值为0.0f
            String coorType = location.getCoorType();
            int errorCode = location.getLocType();
            LogUtils.w("error location errorCode code is :" + errorCode + " error location coorType code is :" + coorType);
        }
    }

    public static HttpProxyCacheServer getProxy(Context context) {
        BaseApplication app = (BaseApplication) context.getApplicationContext();
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }

    private HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer.Builder(this)
            .cacheDirectory(new File(FileUtils.getBaseFileVideoPath()))
            .maxCacheFilesCount(20)
            .maxCacheSize(MemoryConstants.KB * MemoryConstants.KB * MemoryConstants.KB)
            .fileNameGenerator(new BaseFileNameGenerator())
            .build();
    }
}
