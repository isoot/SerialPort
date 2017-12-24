package com.robot.cation.robotapplication.robot.push.broadcast;

import com.robot.cation.robotapplication.robot.robot.connector.ControllerRobot;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
        ControllerRobot.getInstance().tissue(message);
    }

    /**
     * 更新数据库
     */
    private void upDataLocation(String message) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
//                GsonUtil.toBean() 解析JSON
//                LocalDataManipulation.getInstance().insertPlayerVideoUrl();//插入数据库
            }
        }).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe();
    }
}
