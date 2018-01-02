package com.robot.cation.robotapplication.robot.singlechip2.function;


import com.robot.cation.robotapplication.robot.push.bean.PushBean;
import com.robot.cation.robotapplication.robot.singlechip2.FlowPathListener;

/**
 * 所有的功能类的方法统一
 */
public interface SingleChipeInfo {

    void start(PushBean.DataBean.OrderGoodsBean orderGoodsBean, FlowPathListener flowPathListener);

   void sendCommand(PushBean.DataBean.OrderGoodsBean orderGoodsBean);

}
