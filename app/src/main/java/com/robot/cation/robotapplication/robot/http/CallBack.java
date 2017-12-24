package com.robot.cation.robotapplication.robot.http;

/**
 * Created by THINK on 2017/12/12.
 */

public interface CallBack {

    void onSuccess(String body);

    void onError(String body);
}
