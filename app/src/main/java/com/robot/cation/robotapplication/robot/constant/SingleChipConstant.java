package com.robot.cation.robotapplication.robot.constant;


/**
 * 单片机常量
 */
public class SingleChipConstant {
    //包头
    public static final int DATA_HEAD = 0xFFFE;
    //包尾
    public static final int DATA_END = 0xFEFF;
    //包头长度
    public static final int HEAD_SIZE = 2;
    //设备地址长度
    public static final int ADDRESS_SIZE = 1;
    //功能长度
    public static final int FUNCTION_CODE_SIZE = 1;
    //数据长度
    public static final int DATA_LENGTH = 1;
    //包尾长度
    public static final int END_SIZE = 2;
    //充电宝的数据长度
    public static final int POWER_BANK_LENGTH = 2;
    //其他的类型数据长度
    public static final int OTHER_LENGTH = 1;


    //=============================================================步骤=====================================================
    //转动机械臂
    public static final int START_ROTATING_ARM = 1;

    //开始落杯
    public static final int START_FOLLING_CUP = 2;

    //转动机械臂到接收区
    public static final int START_ROTATING_ARM_DESTINATION = 3;

    //开始生产
    public static final int START_PRODUCE = 4;

    //生产完成
    public static final int START_PRODUCE_END = 5;

    //转动机械臂到门口
    public static final int START_ROTATING_ARM_GATE = 6;

    //电动门打开
    public static final int START_OPEN_GATE = 7;

    //用户拿走商品
    public static final int START_USER_TAKES = 8;

    //关闭门口
    public static final int START_CLOSE_GATE = 9;
}
