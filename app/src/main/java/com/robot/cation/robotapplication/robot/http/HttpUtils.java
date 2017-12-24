package com.robot.cation.robotapplication.robot.http;


import com.robot.cation.robotapplication.robot.utils.LogUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
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


    public static void requestPostString(final String url, final String json, final CallBack callBack) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url) .post(body).build();
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
