package com.robot.cation.robotapplication.robot.singlechip;


import com.robot.cation.robotapplication.robot.BaseApplication;

import java.util.Arrays;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 发送指令者
 */
public class SingleChipSend {


    /**
     * 写数据
     *
     * @param command       指令
     * @param addressNumber 地址
     * @param flowListener  流程监听
     */
    public static void sendSingleChip(final byte[] command, final int addressNumber, final int functionNumber, final FlowListener flowListener) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                flowListener.sendCommand(Arrays.asList(addressNumber), Arrays.asList(SingleChipTips.getInterfaceStartTips(addressNumber, functionNumber)));
                int real = BaseApplication.driver.WriteData(command, command.length);//写数据，第一个参数为需要发送的字节数组，第二个参数为需要发送的字节长度，返回实际发送的字节长度
                if (real <= 0) {
                    flowListener.onFailed(Arrays.asList(functionNumber), Arrays.asList(SingleChipTips.getFailedTips(addressNumber, functionNumber)));
                }
                e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe();
    }

    /**
     * 写数据
     *
     * @param command       指令
     * @param addressNumber 地址
     * @param flowListener  流程监听
     */
    public static void sendSingleChip2(final byte[] command, final int addressNumber, final int functionNumber, final FlowListener flowListener, final String tips) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                if (tips == "" || tips == null) {
                    flowListener.sendCommand(Arrays.asList(addressNumber), null);
                } else {
                    flowListener.sendCommand(Arrays.asList(addressNumber), Arrays.asList(tips));
                }
                int real = BaseApplication.driver.WriteData(command, command.length);
                //写数据，第一个参数为需要发送的字节数组，第二个参数为需要发送的字节长度，返回实际发送的字节长度
                if (real <= 0) {
                    flowListener.onFailed(Arrays.asList(functionNumber), Arrays.asList(SingleChipTips.getFailedTips(SingleChipReceive.orderGoodsBean.getFunctionNumber(), SingleChipReceive.orderGoodsBean.getGoodsNumber())));
                }
                e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe();
    }
}
