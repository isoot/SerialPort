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
}
