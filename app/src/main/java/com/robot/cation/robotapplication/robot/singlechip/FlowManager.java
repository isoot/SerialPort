package com.robot.cation.robotapplication.robot.singlechip;


import com.robot.cation.robotapplication.robot.constant.SingleChipConstant;
import com.robot.cation.robotapplication.robot.push.bean.PushBean;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.robot.cation.robotapplication.robot.constant.SingleChipConstant.OTHER_LENGTH;
import static com.robot.cation.robotapplication.robot.constant.SingleChipConstant.POWER_BANK_LENGTH;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipTips.MECHANICAL_ARM;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipTips.PICK_UP_CUP;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipType.ADVERTISING;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipType.COFFEE;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipType.ICE_CREAM;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipType.POWER_BANK;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipType.TEA_WITH_MILK;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipType.TISSUE;

/**
 * 流程管理者
 */
public class FlowManager {

    private static Queue<PushBean.DataBean.OrderGoodsBean> oderGoods = new LinkedList();

    /**
     * 发起一个流程
     *
     * @param pushBean
     * @param flowListener
     */
    public static void startFlow(PushBean pushBean, FlowListener flowListener) {
        //获得商品集合 每一个商品都是一个指令和流程
        List<PushBean.DataBean.OrderGoodsBean> orderGoods = pushBean.getData().getOrderGoods();
        //循环去发送指令
        for (int i = 0; i < orderGoods.size(); i++) {
            PushBean.DataBean.OrderGoodsBean orderGoodsBean = orderGoods.get(i);
            int addressNumber = orderGoodsBean.getFunctionNumber();
            int functionNumber = orderGoodsBean.getGoodsNumber();
            int dataNumber = orderGoodsBean.getCount();
            int dataByteSize = addressNumber == POWER_BANK.getValue() ? POWER_BANK_LENGTH : OTHER_LENGTH;
            //如果是纸巾 充电宝 充电线 广告 直接可以下发指令
            if (addressNumber == POWER_BANK.getValue() || addressNumber == TISSUE.getValue() || addressNumber == ADVERTISING.getValue()) {
                //获得指令数据
                byte[] command = Command.assembleCommand(addressNumber, functionNumber, dataNumber, dataByteSize);
                SingleChipSend.sendSingleChip(command, addressNumber, functionNumber, flowListener);
            } else {
                oderGoods.add(orderGoodsBean);
            }
        }

        //循环完成 筛选出需要复杂流程的商品
        SingleChipReceive.MCOB = false;
        SingleChipReceive.index = 0;
        startFlow2(flowListener);
    }

    /**
     * 开始执行下一个流程 一个一个的执行
     */
    public static void startFlow2(final FlowListener flowListener) {
        if (oderGoods.size() <= 0) {
            return;
        }
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                PushBean.DataBean.OrderGoodsBean peek = oderGoods.peek();
                int addressNumber = peek.getFunctionNumber();
                int functionNumber = peek.getGoodsNumber();
                int dataNumber = peek.getCount();
                dataNumber = dataNumber - 1;
                if (dataNumber <= 0) {
                    //如果这个商品的个数为零 我们将把这个商品弹出去 不再执行这项任务
                    oderGoods.poll();
                } else if (dataNumber > 0) {
                    //若果商品的个数大于零设置标记 表示当前正在制作的商品个数
                    SingleChipReceive.index = dataNumber + 1;
                    //设置标志位表示这个为多个商品
                    SingleChipReceive.MCOB = true;
                }
                //将这个商品赋值到接收者中 在里面需要执行获取其中的数据
                SingleChipReceive.orderGoodsBean = peek;
                if (addressNumber == TEA_WITH_MILK.getValue()) {
                    //表示在制作奶茶流程
                    SingleChipReceive.type = TEA_WITH_MILK.getValue();
                    //设置步骤为第一步
                    SingleChipReceive.STEP = SingleChipConstant.START_ROTATING_ARM;
                    //奶茶流程 第一步接杯子 发送指令给机械臂到指定位置开始工作
                    byte[] command = Command.assembleCommand(ICE_CREAM.getValue(), SingleChipTips.mainpulatorConfigType.get(MECHANICAL_ARM),
                        SingleChipTips.mainpulatorConfig.get(PICK_UP_CUP), 1);
                    SingleChipSend.sendSingleChip2(command, ICE_CREAM.getValue(), SingleChipTips.mainpulatorConfigType.get(MECHANICAL_ARM), flowListener
                        , SingleChipTips.getInterfaceStartTips(addressNumber, functionNumber));
                } else if (addressNumber == ICE_CREAM.getValue()) {
                    SingleChipReceive.type = ICE_CREAM.getValue();
                    SingleChipReceive.STEP = SingleChipConstant.START_ROTATING_ARM;
                    //冰淇淋流程 第一步接杯子
                    byte[] command = Command.assembleCommand(ICE_CREAM.getValue(), SingleChipTips.mainpulatorConfigType.get(MECHANICAL_ARM),
                        SingleChipTips.mainpulatorConfig.get(PICK_UP_CUP), 1);
                    //发送指令
                    SingleChipSend.sendSingleChip2(command, ICE_CREAM.getValue(), SingleChipTips.mainpulatorConfigType.get(MECHANICAL_ARM), flowListener
                        , SingleChipTips.getInterfaceStartTips(addressNumber, functionNumber));
                } else if (addressNumber == COFFEE.getValue()) {
                    SingleChipReceive.type = COFFEE.getValue();
                    SingleChipReceive.STEP = SingleChipConstant.START_ROTATING_ARM;
                    //咖啡流程 第一步接杯子
                    byte[] command = Command.assembleCommand(ICE_CREAM.getValue(), SingleChipTips.mainpulatorConfigType.get(MECHANICAL_ARM),
                        SingleChipTips.mainpulatorConfig.get(PICK_UP_CUP), 1);
                    //发送指令
                    SingleChipSend.sendSingleChip2(command, ICE_CREAM.getValue(), SingleChipTips.mainpulatorConfigType.get(MECHANICAL_ARM), flowListener
                        , SingleChipTips.getInterfaceStartTips(addressNumber, functionNumber));
                }
                e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe();
    }
}
