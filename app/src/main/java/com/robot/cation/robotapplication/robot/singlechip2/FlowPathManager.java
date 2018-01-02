package com.robot.cation.robotapplication.robot.singlechip2;


import com.robot.cation.robotapplication.robot.push.bean.PushBean;
import com.robot.cation.robotapplication.robot.singlechip2.function.Advertising;
import com.robot.cation.robotapplication.robot.singlechip2.function.Coffee;
import com.robot.cation.robotapplication.robot.singlechip2.function.IceCream;
import com.robot.cation.robotapplication.robot.singlechip2.function.PowerBank;
import com.robot.cation.robotapplication.robot.singlechip2.function.TeaWithMilk;
import com.robot.cation.robotapplication.robot.singlechip2.function.Tissue;

import java.util.List;

import static com.robot.cation.robotapplication.robot.singlechip2.SingleChipType.ADVERTISING;
import static com.robot.cation.robotapplication.robot.singlechip2.SingleChipType.COFFEE;
import static com.robot.cation.robotapplication.robot.singlechip2.SingleChipType.ICE_CREAM;
import static com.robot.cation.robotapplication.robot.singlechip2.SingleChipType.POWER_BANK;
import static com.robot.cation.robotapplication.robot.singlechip2.SingleChipType.TEA_WITH_MILK;
import static com.robot.cation.robotapplication.robot.singlechip2.SingleChipType.TISSUE;

/**
 * 流程管理者
 */
public class FlowPathManager {


    /**
     * 发起一个流程
     *
     * @param pushBean
     */
    public static void startFlow(PushBean pushBean, FlowPathListener flowPathListener) {
        //获得商品集合 每一个商品都是一个指令和流程
        List<PushBean.DataBean.OrderGoodsBean> orderGoods = pushBean.getData().getOrderGoods();
        //循环去发送指令
        for (int i = 0; i < orderGoods.size(); i++) {
            PushBean.DataBean.OrderGoodsBean orderGoodsBean = orderGoods.get(i);
            int addressNumber = orderGoodsBean.getFunctionNumber();
            //如果是纸巾 充电宝 充电线 广告 直接可以下发指令
            if (addressNumber == POWER_BANK.getValue()) {
                PowerBank.getInstance().start(orderGoodsBean,flowPathListener);
            } else if (addressNumber == TISSUE.getValue()) {
                Tissue.getInstance().start(orderGoodsBean,flowPathListener);
            } else if (addressNumber == ADVERTISING.getValue()) {
                Advertising.getInstance().start(orderGoodsBean,flowPathListener);
            } else if (addressNumber == TEA_WITH_MILK.getValue()) {
                TeaWithMilk.getInstance().start(orderGoodsBean,flowPathListener);
            } else if (addressNumber == ICE_CREAM.getValue()) {
                IceCream.getInstance().start(orderGoodsBean,flowPathListener);
            } else if (addressNumber == COFFEE.getValue()) {
                Coffee.getInstance().start(orderGoodsBean,flowPathListener);
            }
        }

    }

}
