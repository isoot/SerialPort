package com.robot.cation.robotapplication.robot.push.broadcast;

import com.robot.cation.robotapplication.robot.push.bean.PushBean;
import com.robot.cation.robotapplication.robot.robot.connector.ControllerRobot;
import com.robot.cation.robotapplication.robot.utils.GsonUtil;
import com.robot.cation.robotapplication.robot.utils.LogUtils;
import com.robot.cation.robotapplication.robot.utils.ToastUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.robot.cation.robotapplication.robot.robot.connector.ControllerRobot.ADVISEMENT_PLAYER;
import static com.robot.cation.robotapplication.robot.robot.connector.ControllerRobot.COFFEE_MAKER;
import static com.robot.cation.robotapplication.robot.robot.connector.ControllerRobot.ICE_CREAM_MACHINE;
import static com.robot.cation.robotapplication.robot.robot.connector.ControllerRobot.MILK_TEA_MACHINE;
import static com.robot.cation.robotapplication.robot.robot.connector.ControllerRobot.PAPER_TOWEL_MACHINE;
import static com.robot.cation.robotapplication.robot.robot.connector.ControllerRobot.POWER_BANK;

/**
 * Created by THINK on 2017/12/20.
 */

public class PushMessageManager {

    private static PushMessageManager instance;

    private PushMessageManager() {
        runnable.start();
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

    public List<PushCallBack> getList() {
        return list;
    }

    private Queue<PushBean> queue = new LinkedList<PushBean>();


    private Thread runnable = new Thread() {
        @Override
        public void run() {
            while (true) {
                try {
                    PushBean poll = queue.poll();
                    if (poll != null) {
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).push(poll);
                        }
                        List<PushBean.DataBean.OrderGoodsBean> orderGoods = poll.getData().getOrderGoods();
                        for (int i = 0; i < orderGoods.size(); i++) {
                            PushBean.DataBean.OrderGoodsBean orderGoodsBean = orderGoods.get(i);
                            switch (Integer.valueOf(orderGoodsBean.getFunctionNumber())) {
                                case MILK_TEA_MACHINE:
                                    //奶茶
                                    ControllerRobot.getInstance().teaWithMilk(orderGoodsBean.getCount());
                                    break;
                                case PAPER_TOWEL_MACHINE:
                                    ControllerRobot.getInstance().tissue(orderGoodsBean.getCount());
                                    //纸巾
                                    break;
                                case ICE_CREAM_MACHINE:
                                    //冰淇淋
                                    break;
                                case POWER_BANK:
                                    //充电宝
                                    break;
                                case COFFEE_MAKER:
                                    //咖啡
                                    break;
                                case ADVISEMENT_PLAYER:
                                    //广告
                                    break;
                            }
                        }
                    } else {
                        sleep(1000);
                    }
                } catch (Exception el) {
                    List<PushCallBack> list = PushMessageManager.getInstance().getList();
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).onFailed("很抱歉的通知你我们制作出现了问题，正在检查...!\n");
                    }
                    ToastUtils.showShort("订单错误，请核实你的订单信息");
                    LogUtils.w("制作出现错误:" + el);
                }

            }
        }

    };

    public void informPush(final String message) {
        PushBean pushBean = GsonUtil.toBean(message, PushBean.class);
        queue.add(pushBean);
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
