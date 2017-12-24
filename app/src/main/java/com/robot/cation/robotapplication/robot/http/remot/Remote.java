package com.robot.cation.robotapplication.robot.http.remot;

import com.robot.cation.robotapplication.robot.constant.ConstantsUrl;
import com.robot.cation.robotapplication.robot.http.CallBack;
import com.robot.cation.robotapplication.robot.http.HttpUtils;
import com.robot.cation.robotapplication.robot.utils.GsonUtil;

/**
 * Created by THINK on 2017/12/23.
 */

public class Remote implements IRemote {

    @Override
    public void init(Object o, CallBack callBack) {
        HttpUtils.requestPostString(ConstantsUrl.url + ConstantsUrl.INIT, GsonUtil.string(0), callBack);
    }

    @Override
    public void uploadOrderMsg(Object o, CallBack callBack) {
        HttpUtils.requestPostString(ConstantsUrl.url + ConstantsUrl.UPLOAD_ORDER_MSG, GsonUtil.string(0), callBack);
    }

    @Override
    public void upload(Object o, CallBack callBack) {
        HttpUtils.requestPostString(ConstantsUrl.url + ConstantsUrl.UPLOAD, GsonUtil.string(0), callBack);
    }

    @Override
    public void uploadBattery(Object o, CallBack callBack) {
        HttpUtils.requestPostString(ConstantsUrl.url + ConstantsUrl.UPLOAD_BATTERY, GsonUtil.string(0), callBack);
    }

}
