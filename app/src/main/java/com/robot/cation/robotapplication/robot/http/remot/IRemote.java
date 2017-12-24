package com.robot.cation.robotapplication.robot.http.remot;

import com.robot.cation.robotapplication.robot.http.CallBack;

/**
 * Created by THINK on 2017/12/23.
 */

public interface IRemote {

    /**
     * 初始化
     * @param o 参数
     * @param callBack
     */
    void init(Object o, CallBack callBack);

    /**
     *上传商品订单信息:
     * @param o
     * @param callBack
     */
    void uploadOrderMsg(Object o, CallBack callBack);

    /**
     *参数上传：
     * @param o
     * @param callBack
     */
    void upload(Object o, CallBack callBack);

    /**
     *
     * @param o
     * @param callBack
     */
    void uploadBattery(Object o, CallBack callBack);
}
