package com.robot.cation.robotapplication.robot.http;

import com.robot.cation.robotapplication.robot.http.locality.Locality;
import com.robot.cation.robotapplication.robot.http.remot.IRemote;
import com.robot.cation.robotapplication.robot.http.remot.Remote;

/**
 * Created by THINK on 2017/12/24.
 */

public class Repository implements IRemote {
    private Remote remote = new Remote();
    private Locality locality = new Locality();

    private static Repository instance;

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    @Override
    public void init(Object o, CallBack callBack) {
        remote.init(o, callBack);
    }

    @Override
    public void uploadOrderMsg(Object o, CallBack callBack) {
        remote.uploadOrderMsg(o, callBack);
    }

    @Override
    public void upload(Object o, CallBack callBack) {
        remote.upload(o, callBack);
    }

    @Override
    public void uploadBattery(Object o, CallBack callBack) {
        remote.uploadBattery(o, callBack);
    }
}
