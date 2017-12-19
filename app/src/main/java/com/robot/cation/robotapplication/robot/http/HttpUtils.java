package com.robot.cation.robotapplication.robot.http;


import com.robot.cation.robotapplication.robot.utils.LogUtils;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {
    public static final MediaType JSON
        = MediaType.parse("application/json; charset=utf-8");

    private HttpUtils() throws Exception {
        throw new Exception("not instance!");
    }

    public static void requestGetString(final String url, final CallBack callBack) {
        new Thread() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                        .url(url)
                        .build();
                    Response response = client.newCall(request).execute();
                    callBack.onSuccess(response.body().string());
                } catch (Exception e) {
                    callBack.onError("请求失败");
                    LogUtils.w(e);
                }
            }
        }.start();
    }


    public static void requestPostString(final String url, final String json, final CallBack callBack) {
        new Thread() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = RequestBody.create(JSON, json);
                    Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                    Response response = client.newCall(request).execute();
                    callBack.onSuccess(response.body().string());
                } catch (Exception e) {
                    callBack.onError("请求失败");
                    LogUtils.w(e);
                }
            }
        }.start();
    }


}
