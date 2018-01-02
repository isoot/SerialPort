package com.robot.cation.robotapplication.robot.singlechip2;

import com.robot.cation.robotapplication.robot.push.bean.PushBean;

/**
 * 流程监听
 */
public interface FlowPathListener {

    /**
     * 启动PushBean数据和制作流程
     * 用在刷新界面数据和提示用户开始制作
     *
     * @param pushBean
     */
    void startFlow(PushBean pushBean);

    /**
     * 开始流程
     *
     * @param tipsText    文字提示
     * @param voicePrompt 语音提示
     */
    void start(String tipsText, String voicePrompt);

    /**
     * 开始流程
     *
     * @param tipsText    文字提示
     * @param voicePrompt 语音提示
     */
    void tips(String tipsText, String voicePrompt);

    /**
     * 开始流程
     *
     * @param tipsText    文字提示
     * @param voicePrompt 语音提示
     */
    void complete(String tipsText, String voicePrompt);

    /**
     * 开始流程
     *
     * @param tipsText    文字提示
     * @param voicePrompt 语音提示
     */
    void onFailed(String tipsText, String voicePrompt);

    /**
     * 开始流程
     *
     * @param tipsText    文字提示
     * @param voicePrompt 语音提示
     */
    void nextFlows(String tipsText, String voicePrompt);
}
