package com.robot.cation.robotapplication.robot.controller;

/**
 * 1.一帧最长32字节，数据长度最长32-5=27字节
 * 2.数据头：判断此帧数据作用于什么位置；包头
 * 3.目标：相当于地址，目标匹配时才执行操作；
 * 4.命令：write or read？读或写；
 * 5.长度：该帧数据数据体长度；
 * 6.数据体：具体要执行功能的数据；
 * 7.和校验：将 数据头、目标、命令、长度、数据体进行和校验。
 * 【数据头】（2）【目标】（设备地址）【命令】(write or read)【长度】（单个包总长度）【数据体】【和校验】（crc16  2个字节）
 * 0	1	2	3     len+4     len+5
 *
 */

public class Controller {

    public static final int INTERVAL = 5;
    public static final int FRAME_SIZE_MAX = 32;
    public static final int DATA_SIZE = FRAME_SIZE_MAX - INTERVAL;

    public static String test() {

       return "";
    }
}
