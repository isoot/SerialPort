package com.robot.cation.robotapplication.robot.constant;

import com.robot.cation.robotapplication.BuildConfig;

/**
 * 网络请求URL
 */

public class ConstantsUrl {

    /**
     * 初始化
     */
    public static final String INIT = "/android/device/init";


    /**
     * 设备上传商品订单信息
     */
    public static final String UPLOAD_ORDER_MSG = "/android/device/uploadOrderMsg";


    /**
     * 设备参数上传
     */
    public static final String UPLOAD = "/android/device/upload";

    /**
     * 设备上传充电宝订单信息
     */
    public static final String UPLOAD_BATTERY = "/android/device/uploadBattery";

    /**
     * 生成环境和测试环境
     */
    public static final String url = BuildConfig.LOG_DEBUG ? "http://192.168.01" : "http://www.baidu.com";
}
