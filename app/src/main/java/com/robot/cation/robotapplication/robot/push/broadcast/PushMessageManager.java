package com.robot.cation.robotapplication.robot.push.broadcast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by THINK on 2017/12/20.
 */

public class PushMessageManager {

    private static PushMessageManager instance;

    private PushMessageManager() {
        //Do nothing
    }

    public static PushMessageManager getInstance() {
        if (null == instance) {
            instance = new PushMessageManager();
        }
        return instance;
    }

    private List<PushCallBack> list = new ArrayList<>();

    public void addCallBack(PushCallBack callBack) {
        list.add(callBack);
    }

    public void informPush(String message) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).push(message);
        }
    }
}
