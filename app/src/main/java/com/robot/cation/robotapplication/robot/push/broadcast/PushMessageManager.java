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

    public List<PushCallBack> getList() {
        return list;
    }

    private Queue<PushBean> queue = new LinkedList<PushBean>();

    /**
     * 是否正在执行中
     */
    public static boolean isExecuting = false;

    /**
     * 执行命令 每次调用只能执行一次
     */
    public void execute() {
        if (!isExecuting) {
            isExecuting = true;
            try {
                PushBean poll = queue.poll();
                if (poll != null) {
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).push(poll);
                    }
                    upDataPush();
                    List<PushBean.DataBean.OrderGoodsBean> orderGoods = poll.getData().getOrderGoods();
                    for (int i = 0; i < orderGoods.size(); i++) {
                        PushBean.DataBean.OrderGoodsBean orderGoodsBean = orderGoods.get(i);
                        switch (Integer.valueOf(orderGoodsBean.getFunctionNumber())) {
                            case MILK_TEA_MACHINE:
                                //奶茶
                                ControllerRobot.getInstance().teaWithMilk(orderGoodsBean.getCount(), orderGoodsBean.getGoodsNumber());
                                break;
                            case PAPER_TOWEL_MACHINE:
                                ControllerRobot.getInstance().tissue(orderGoodsBean.getCount(), orderGoodsBean.getGoodsNumber());
                                //纸巾
                                break;
                            case ICE_CREAM_MACHINE:
                                //冰淇淋
//                                ControllerRobot.getInstance().iceCream(orderGoodsBean.getCount());
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
                    isExecuting = false;
                }
            } catch (Exception el) {
                isExecuting = false;
                List<PushCallBack> list = PushMessageManager.getInstance().getList();
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).onFailed("很抱歉的通知你我们制作出现了问题，正在检查...!\n");
                }
                ToastUtils.showShort("订单错误，请核实你的订单信息");
                LogUtils.w("制作出现错误:" + el);
            }
        } else {
            //正在制作中

        }
    }

    public void informPush(final String message) {
        PushBean pushBean = GsonUtil.toBean(message, PushBean.class);
        queue.add(pushBean);
        execute();
        upDataPush();
    }

    /**
     * 等待队列提示
     */
    private void upDataPush() {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                List<PushCallBack> list = PushMessageManager.getInstance().getList();
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).nextOrder(queue);
                }
                e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe();
    }
}
