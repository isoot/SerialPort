package com.robot.cation.robotapplication.robot.controller;

import com.robot.cation.robotapplication.robot.push.broadcast.PushCallBack;
import com.robot.cation.robotapplication.robot.push.broadcast.PushMessageManager;

import java.util.ArrayList;
import java.util.Collections;
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
import static com.robot.cation.robotapplication.robot.robot.connector.ControllerRobot.HEAD_SIZE;
import static com.robot.cation.robotapplication.robot.robot.connector.ControllerRobot.ICE_CREAM_MACHINE;
import static com.robot.cation.robotapplication.robot.robot.connector.ControllerRobot.MILK_TEA_MACHINE;
import static com.robot.cation.robotapplication.robot.robot.connector.ControllerRobot.PAPER_TOWEL_MACHINE;
import static com.robot.cation.robotapplication.robot.robot.connector.ControllerRobot.POWER_BANK;

/**
 * 接收数据处理中心
 */
public class ReceiveController {
    //接收到的数据会存储在这里
    private static Queue<byte[]> queue = new LinkedList<byte[]>();

    private static List<Integer> list = Collections.synchronizedList(new ArrayList());

    public static void addType(int type) {
        list.add(type);
    }

    public static void startDisposeQueue() {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                while (true) {
                    //处理数据
                    byte[] poll = queue.poll();
                    if (poll != null) {
                        byte address = poll[HEAD_SIZE];
                        switch (address) {
                            case MILK_TEA_MACHINE:
                                if (list.contains(MILK_TEA_MACHINE)) {
                                    list.remove(Integer.valueOf(MILK_TEA_MACHINE));
                                    //奶茶机
                                    List<PushCallBack> list = PushMessageManager.getInstance().getList();
                                    for (int i = 0; i < list.size(); i++) {
                                        list.get(i).complete("奶茶制作完成请取走的你商品!\n");
                                    }
                                } else {

                                }
                                break;
                            case PAPER_TOWEL_MACHINE:
                                //纸巾机
                                if (list.contains(PAPER_TOWEL_MACHINE)) {
                                    list.remove(Integer.valueOf(PAPER_TOWEL_MACHINE));
                                    List<PushCallBack> list = PushMessageManager.getInstance().getList();
                                    for (int i = 0; i < list.size(); i++) {
                                        list.get(i).complete("纸巾出货完成请取走的你商品!\n");
                                    }
                                } else {

                                }
                                break;
                            case ICE_CREAM_MACHINE:
                                //冰淇淋机

                                break;
                            case POWER_BANK:
                                //充电宝

                                break;
                            case ADVISEMENT_PLAYER:
                                //广告机

                                break;
                            case COFFEE_MAKER:
                                //咖啡机

                                break;
                        }
                    }
                }
            }
        }).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe();
    }

    public static void addReceive(byte[] data) {
        //添加数据
        boolean offer = queue.offer(data);
    }


}
