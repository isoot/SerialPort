package com.robot.cation.robotapplication.robot;

import android.app.Application;
import android.content.SharedPreferences;
import android.serialport.SerialPort;
import android.serialport.SerialPortFinder;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.robot.cation.robotapplication.robot.http.DownloadUtil;
import com.robot.cation.robotapplication.robot.utils.CrashUtils;
import com.robot.cation.robotapplication.robot.utils.FileUtils;
import com.robot.cation.robotapplication.robot.utils.LogUtils;
import com.robot.cation.robotapplication.robot.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;

import cn.jpush.android.api.JPushInterface;
import cn.wch.ch34xuartdriver.CH34xUARTDriver;

public class BaseApplication extends Application {
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    public static double latitude = 0f;    //获取纬度信息
    public static double longitude = 0f;    //获取经度信息

    /**
     * 设备串口
     */
    public static CH34xUARTDriver driver;
    /**
     * 串口通信
     */
    public SerialPortFinder mSerialPortFinder = new SerialPortFinder();
    private SerialPort mSerialPort = null;

    public SerialPort getSerialPort()
        throws SecurityException, IOException, InvalidParameterException {
        if (mSerialPort == null) {
            /* Read serial port parameters */

            String packageName = getPackageName();
            SharedPreferences sp = getSharedPreferences(packageName + "_preferences", MODE_PRIVATE);
            String path = sp.getString("DEVICE", "");
            LogUtils.w("地址:" + path);
            int baudrate = Integer.decode(sp.getString("BAUDRATE", "-1"));
            LogUtils.w("串口波特率:" + baudrate);
            /* Check parameters */
            if ((path.length() == 0) || (baudrate == -1)) {
                throw new InvalidParameterException();
            }

			/* Open the serial port */
            mSerialPort = new SerialPort(new File(path), baudrate, 0);
        }
        return mSerialPort;
    }

    public void closeSerialPort() {
        if (mSerialPort != null) {
            mSerialPort.close();
            mSerialPort = null;
        }
    }

    /**
     * 配置
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        initLog();
        CrashUtils.init(FileUtils.getBaseFileCrashPath());
        initLocationOption();
    }

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

    private void initLocationOption() {

        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数

        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；

        option.setCoorType("bd09ll");
        //可选，设置返回经纬度坐标类型，默认gcj02
        //gcj02：国测局坐标；
        //bd09ll：百度经纬度坐标；
        //bd09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标

        option.setScanSpan(600000);
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效

        option.setOpenGps(true);
        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(true);
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);
        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(false);
        //可选，设置是否收集Crash信息，默认收集，即参数为false

        option.setWifiCacheTimeOut(5 * 60 * 1000);
        //可选，7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位

        option.setEnableSimulateGps(false);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        mLocationClient.setLocOption(option);
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明

        mLocationClient.start();
        //mLocationClient为第二步初始化过的LocationClient对象
        //调用LocationClient的start()方法，便可发起定位请求

        //静默安装和自启动
        DownloadUtil.get().download("http://wap.apk.anzhi.com//apk/201712/11/cn.goapk.market_1512983811_75432300.apk", FileUtils.getBaseFileAPKPath(), new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(String filePath) {
                //
                // SilentInstall.becomeSilentInstall(filePath);
            }

            @Override
            public void onDownloadFailed() {
            }
        });

    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            latitude = location.getLatitude();    //获取纬度信息
            longitude = location.getLongitude();    //获取经度信息
            float radius = location.getRadius();    //获取定位精度，默认值为0.0f

            String coorType = location.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            int errorCode = location.getLocType();
            LogUtils.w("error location errorCode code is :" + errorCode + " error location coorType code is :" + coorType);
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
        }
    }

}
