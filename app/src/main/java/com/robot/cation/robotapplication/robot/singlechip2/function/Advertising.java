package com.robot.cation.robotapplication.robot.singlechip2.function;


import com.robot.cation.robotapplication.robot.push.bean.PushBean;
import com.robot.cation.robotapplication.robot.singlechip2.CommandAssemble;
import com.robot.cation.robotapplication.robot.singlechip2.CommandSend;
import com.robot.cation.robotapplication.robot.singlechip2.FlowPathListener;
import com.robot.cation.robotapplication.robot.singlechip2.Interceptor;

import static com.robot.cation.robotapplication.robot.constant.SingleChipConstant.OTHER_LENGTH;
import static com.robot.cation.robotapplication.robot.constant.SingleChipConstant.POWER_BANK_LENGTH;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipType.POWER_BANK;

/**
 * 广告
 */
public class Advertising implements SingleChipeInfo, Interceptor.DataCallBack {
    private static Advertising instance;

    private String tipsText;

    private String voicePrompt;

    private String tipsTextFailed;

    private String voicePromptFailed;

    private FlowPathListener flowPathListener;

    private Advertising() {
        //Do nothing
    }

    public static Advertising getInstance() {
        if (instance == null) {
            instance = new Advertising();
        }
        return instance;
    }


    @Override
    public void start(PushBean.DataBean.OrderGoodsBean orderGoodsBean, FlowPathListener flowPathListener) {
        this.flowPathListener = flowPathListener;
        sendCommand(orderGoodsBean);
    }

    @Override
    public void sendCommand(PushBean.DataBean.OrderGoodsBean orderGoodsBean) {
        int addressNumber = orderGoodsBean.getFunctionNumber();
        int functionNumber = orderGoodsBean.getGoodsNumber();
        int dataNumber = orderGoodsBean.getCount();
        int dataByteSize = addressNumber == POWER_BANK.getValue() ? POWER_BANK_LENGTH : OTHER_LENGTH;
        voicePrompt = tipsText = "您的" + orderGoodsBean.getGoodsName() + "正在生效中请稍后!";
        voicePromptFailed = tipsTextFailed = "您的" + orderGoodsBean.getGoodsName() + "生效失败!";
        //获得指令数据
        byte[] command = CommandAssemble.assembleCommand(addressNumber, functionNumber, dataNumber, dataByteSize);
        Interceptor interceptor = new Interceptor(addressNumber, functionNumber, this);
        CommandSend.sendCommand(command, interceptor);
    }

    @Override
    public void interceptor(byte[] command) {
        flowPathListener.complete(tipsText, voicePrompt);
    }

    @Override
    public void onFailedSendCommand(int code) {
        flowPathListener.onFailed(tipsTextFailed, voicePromptFailed);
    }
}
