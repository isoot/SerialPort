package com.robot.cation.robotapplication.robot.http.request;

import com.robot.cation.robotapplication.robot.BaseApplication;
import com.robot.cation.robotapplication.robot.utils.DeviceUtils;

import okhttp3.Request;

/**
 * Created by THINK on 2017/12/12.
 */

public class RequestBase {

    private RequestBase() throws Exception {
        throw new Exception("not instance!");
    }

    /**
     * 每一次请求都需要带上AndroidID 序列号
     *
     * @return
     */
    public static Request.Builder getBaseRequest() {
        Request.Builder builder = new Request.Builder();
        builder.addHeader("deviceNumber", DeviceUtils.getAndroidID());
        builder.addHeader("mac", DeviceUtils.getMacAddress());
        builder.addHeader("lon", String.valueOf(BaseApplication.longitude));//经度
        builder.addHeader("lat", String.valueOf(BaseApplication.latitude));//纬度
        return builder;
    }
}
