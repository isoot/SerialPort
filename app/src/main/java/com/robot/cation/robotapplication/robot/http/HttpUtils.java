package com.robot.cation.robotapplication.robot.http;


import com.robot.cation.robotapplication.robot.BaseApplication;
import com.robot.cation.robotapplication.robot.utils.DeviceUtils;
import com.robot.cation.robotapplication.robot.utils.LogUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {
    public static final MediaType JSON
        = MediaType.parse("application/json; charset=utf-8");
    private static OkHttpClient okHttpClient = new OkHttpClient();

    private HttpUtils() throws Exception {
        throw new Exception("not instance!");
    }

    public static void requestGetString(final String url, final CallBack callBack) {
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.w(e);
                callBack.onError("请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callBack.onSuccess(response.body().string());
            }
        });
    }


    public static void requestPostString(final String url, final Map<String, String> json, final CallBack callBack) {
//        RequestBody body = RequestBody.create(JSON, json);
        FormBody.Builder builder1 = new FormBody.Builder();
        Set set = json.keySet();
        for (Map.Entry<String, String> entry : json.entrySet()) {
            builder1.add(entry.getKey(), entry.getValue());
        }
        RequestBody body = builder1.build();
        Request.Builder builder = new Request.Builder();
        builder.addHeader("deviceNumber", DeviceUtils.getAndroidID());
        builder.addHeader("mac", DeviceUtils.getMacAddress());
        builder.addHeader("lon", String.valueOf(BaseApplication.longitude));//经度
        builder.addHeader("lat", String.valueOf(BaseApplication.latitude));//纬度
        builder.url(url);
        builder.post(body);
        Request request = builder.build();
        LogUtils.w("HttpUtils requestPostString:" + request.toString() + " data: " + json);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.w(e);
                callBack.onError("请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callBack.onSuccess(response.body().string());
            }
        });
    }


}
