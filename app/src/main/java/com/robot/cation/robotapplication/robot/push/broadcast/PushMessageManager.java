package com.robot.cation.robotapplication.robot.push.broadcast;

import com.robot.cation.robotapplication.robot.push.bean.PushBean;
import com.robot.cation.robotapplication.robot.singlechip.FlowListener;
import com.robot.cation.robotapplication.robot.singlechip.FlowManager;
import com.robot.cation.robotapplication.robot.singlechip.SingleChipReceive;
import com.robot.cation.robotapplication.robot.singlechip.SingleChipTips;
import com.robot.cation.robotapplication.robot.utils.GsonUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by THINK on 2017/12/20.
 */

public class PushMessageManager {

    private static PushMessageManager instance;

    //任务队列
    private Queue<PushBean> queue = new LinkedList<PushBean>();

    //显示下单队列
    private List<PushBean> orderList = Collections.synchronizedList(new ArrayList());

    /**
     * 是否正在执行中
     */
    public static boolean isExecuting = false;

    private FlowListener flowListener;

    public FlowListener getFlowListener() {
        return flowListener;
    }

    public void setFlowListener(FlowListener flowListener) {
        this.flowListener = flowListener;
    }

    private PushMessageManager() {
        //Do nothing
    }

    public static PushMessageManager getInstance() {
        if (null == instance) {
            instance = new PushMessageManager();
        }
        return instance;
    }


    /**
     * 执行命令 每次调用只能执行一次
     */
    public void execute() {
        if (!isExecuting) {
            isExecuting = true;
            PushBean poll = null;
            try {
                poll = queue.poll();
                orderList.remove(poll);
                upDataPush();
                if (poll != null && flowListener != null) {
                    flowListener.startFlow(poll);
                    FlowManager.startFlow(poll, flowListener);
                } else {
                    SingleChipReceive.clearSingleType();
                    isExecuting = false;
                }
            } catch (Exception el) {
                SingleChipReceive.clearSingleType();
                isExecuting = false;
            }
        } else {
            upDataPush();
        }
    }

    /**
     * 把推送过来的数据添加到队列里面
     *
     * @param message
     */
    public void informPush(final String message) {
        PushBean pushBean = GsonUtil.toBean(message, PushBean.class);
        queue.offer(pushBean);
        orderList.add(pushBean);
        execute();
    }

    /**
     * 等待队列提示
     */
    private void upDataPush() {
        if (flowListener != null) {
            //通知有
            List info = new ArrayList();
            for (int i = 0; i < orderList.size(); i++) {
                PushBean pushBean = orderList.get(i);
                StringBuffer buffer = new StringBuffer();
                buffer.append("亲爱的" + pushBean.getData().getNikeName() + "购买了:");
                List<PushBean.DataBean.OrderGoodsBean> orderGoods = pushBean.getData().getOrderGoods();
                for (int j = 0; j < orderGoods.size(); j++) {
                    PushBean.DataBean.OrderGoodsBean orderGoodsBean = orderGoods.get(j);
                    buffer.append(SingleChipTips.getBuyInfo(orderGoodsBean.getFunctionNumber(), orderGoodsBean.getGoodsNumber(), orderGoodsBean.getCount()
                        , orderGoodsBean.getGoodsName()) + "、");
                }
                info.add(buffer.toString() + "\n");
            }
            flowListener.nextFlows(info);
        }
    }
}
