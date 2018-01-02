package com.robot.cation.robotapplication.robot.singlechip2.function;


import com.robot.cation.robotapplication.robot.push.bean.PushBean;
import com.robot.cation.robotapplication.robot.singlechip2.CommandAssemble;
import com.robot.cation.robotapplication.robot.singlechip2.CommandSend;
import com.robot.cation.robotapplication.robot.singlechip2.FlowPathListener;
import com.robot.cation.robotapplication.robot.singlechip2.Interceptor;
import com.robot.cation.robotapplication.robot.singlechip2.TipsAndType;

import static com.robot.cation.robotapplication.robot.constant.SingleChipConstant.OTHER_LENGTH;
import static com.robot.cation.robotapplication.robot.constant.SingleChipConstant.POWER_BANK_LENGTH;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipType.POWER_BANK;
import static com.robot.cation.robotapplication.robot.singlechip2.TipsAndType.GATE_LOCATION;
import static com.robot.cation.robotapplication.robot.singlechip2.TipsAndType.ICE_CREAM_POSITION;
import static com.robot.cation.robotapplication.robot.singlechip2.TipsAndType.PICK_UP_CUP;
import static com.robot.cation.robotapplication.robot.singlechip2.TipsAndType.RESTORATION;

/**
 * 冰淇淋
 */
public class IceCream extends BaseFun implements SingleChipeInfo {
    private static IceCream instance;

    private String tipsText;

    private String voicePrompt;

    private String tipsTextFailed;

    private String voicePromptFailed;

    private boolean mcob;

    private int mcobNumber = 1;

    private FlowPathListener flowPathListener;

    private IceCream() {
        //Do nothing
    }

    public static IceCream getInstance() {
        if (instance == null) {
            instance = new IceCream();
        }
        return instance;
    }


    @Override
    public void start(PushBean.DataBean.OrderGoodsBean orderGoodsBean, FlowPathListener flowPathListener) {
        mcobNumber = 1;
        mcob = false;
        this.flowPathListener = flowPathListener;
        sendCommand(orderGoodsBean);
    }

    @Override
    public void sendCommand(final PushBean.DataBean.OrderGoodsBean orderGoodsBean) {
        final int addressNumber = orderGoodsBean.getFunctionNumber();
        final int functionNumber = orderGoodsBean.getGoodsNumber();
        final int dataNumber = orderGoodsBean.getCount();
        final int dataByteSize = addressNumber == POWER_BANK.getValue() ? POWER_BANK_LENGTH : OTHER_LENGTH;
        if (dataNumber > 1) {
            mcob = true;
        } else if (mcobNumber == dataNumber) {
            //所有流程走完 开始通知下一个流程开始
            flowPathListener.complete(tipsText, voicePrompt);
            return;
        }
        if (mcob) {
            voicePrompt = tipsText = "您的" + orderGoodsBean.getGoodsName() + "第" + mcobNumber + "正在制作中请稍后!";
            voicePromptFailed = tipsTextFailed = "您的" + orderGoodsBean.getGoodsName() + "第" + mcobNumber + "制作失败!";
        } else {
            voicePrompt = tipsText = "您的" + orderGoodsBean.getGoodsName() + "正在出制作请稍后!";
            voicePromptFailed = tipsTextFailed = "您的" + orderGoodsBean.getGoodsName() + "制作失败!";
        }
        //调动机械臂
        mechanicalArm(TipsAndType.mainpulatorConfig.get(PICK_UP_CUP), new Interceptor.DataCallBack() {
            @Override
            public void interceptor(byte[] command) {
                //接到杯子
                follCupMachine(new Interceptor.DataCallBack() {
                    @Override
                    public void interceptor(byte[] command) {
                        //落完杯子
                        mechanicalArm(TipsAndType.mainpulatorConfig.get(ICE_CREAM_POSITION), new Interceptor.DataCallBack() {
                            @Override
                            public void interceptor(byte[] command) {
                                //杯子已经抵达冰淇淋位置 开始制作冰淇淋
                                byte[] commandTemp = CommandAssemble.assembleCommand(addressNumber, functionNumber, dataNumber, dataByteSize);
                                Interceptor interceptor = new Interceptor(addressNumber, functionNumber, new Interceptor.DataCallBack() {
                                    @Override
                                    public void interceptor(byte[] command) {
                                        //冰淇淋制作完成 让机械臂到门口
                                        mechanicalArm(TipsAndType.mainpulatorConfig.get(GATE_LOCATION), new Interceptor.DataCallBack() {
                                            @Override
                                            public void interceptor(byte[] command) {
                                                //机械臂抵达门口 放下杯子 开始让机械臂复位
                                                mechanicalArm(TipsAndType.mainpulatorConfig.get(RESTORATION), new Interceptor.DataCallBack() {
                                                    @Override
                                                    public void interceptor(byte[] command) {
                                                        //机械臂已经复位，开始通知门口打开
                                                        electricallyOperatedGate(new Interceptor.DataCallBack() {
                                                            @Override
                                                            public void interceptor(byte[] command) {
                                                                //电动门已经打开
                                                                flowPathListener.tips(tipsText, voicePrompt);
                                                                sendCommand(orderGoodsBean);
                                                            }

                                                            @Override
                                                            public void onFailedSendCommand(int code) {
                                                                flowPathListener.onFailed(tipsTextFailed, voicePromptFailed);
                                                            }
                                                        });

                                                    }

                                                    @Override
                                                    public void onFailedSendCommand(int code) {
                                                        flowPathListener.onFailed(tipsTextFailed, voicePromptFailed);
                                                    }
                                                });
                                            }

                                            @Override
                                            public void onFailedSendCommand(int code) {
                                                flowPathListener.onFailed(tipsTextFailed, voicePromptFailed);
                                            }
                                        });
                                    }

                                    @Override
                                    public void onFailedSendCommand(int code) {
                                        flowPathListener.onFailed(tipsTextFailed, voicePromptFailed);
                                    }
                                });
                                CommandSend.sendCommand(commandTemp, interceptor);
                            }

                            @Override
                            public void onFailedSendCommand(int code) {
                                flowPathListener.onFailed(tipsTextFailed, voicePromptFailed);
                            }
                        });
                    }

                    @Override
                    public void onFailedSendCommand(int code) {
                        flowPathListener.onFailed(tipsTextFailed, voicePromptFailed);
                    }
                });
            }

            @Override
            public void onFailedSendCommand(int code) {
                flowPathListener.onFailed(tipsTextFailed, voicePromptFailed);
            }
        });

    }
}
