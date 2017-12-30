package com.robot.cation.robotapplication.robot.singlechip;

import com.robot.cation.robotapplication.robot.push.bean.PushBean;

import java.util.List;

/**
 * 流程监听
 */
public interface FlowListener {
    /**
     * 启动PushBean数据和制作流程
     * 用在刷新界面数据和提示用户开始制作
     *
     * @param pushBean
     */
    void startFlow(PushBean pushBean);

    /**
     * 发送命令
     *
     * @param singleChipType 发送多个的指令类型
     * @param tips           需要的提示 作用在语音提示上面
     */
    void sendCommand(List<Integer> singleChipType, List<String> tips);

    /**
     * 完成命令
     *
     * @param singleChipType 发送多个的指令类型
     * @param tips           完成之后的提示
     */
    void complete(List<Integer> singleChipType, List<String> tips);

    /**
     * 失败
     *
     * @param singleChipType 接收指令类型
     * @param tips           失败之后的提示
     */
    void onFailed(List<Integer> singleChipType, List<String> tips);

    void nextFlows(List info);
}
