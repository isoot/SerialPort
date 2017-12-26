package com.robot.cation.robotapplication.robot.push.broadcast;

import com.robot.cation.robotapplication.robot.push.bean.PushBean;

/**
 * Created by THINK on 2017/12/20.
 */

public interface PushCallBack {
    void push(PushBean pushBean);

    void inProduction(String message);

    void complete(String message);

    void onFailed(String message);
}
