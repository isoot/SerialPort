package com.robot.cation.robotapplication.robot.robot.sharedchargingpoint;

import com.robot.cation.robotapplication.robot.controller.Controller;
import com.robot.cation.robotapplication.robot.crc.CRC16X25Util;
import com.robot.cation.robotapplication.robot.robot.connector.ControllerRobot;
import com.robot.cation.robotapplication.robot.robot.teawithmilk.TeaWithMilkConfig;
import com.robot.cation.robotapplication.robot.utils.HexUtil;
import com.robot.cation.robotapplication.robot.utils.LogUtils;

import java.util.Arrays;

import static com.robot.cation.robotapplication.robot.robot.connector.ControllerRobot.ADDRESS_SIZE;
import static com.robot.cation.robotapplication.robot.robot.connector.ControllerRobot.DATA_LENGTH;
import static com.robot.cation.robotapplication.robot.robot.connector.ControllerRobot.END_SIZE;
import static com.robot.cation.robotapplication.robot.robot.connector.ControllerRobot.FUNCTION_CODE_SIZE;
import static com.robot.cation.robotapplication.robot.robot.connector.ControllerRobot.HEAD_SIZE;
import static com.robot.cation.robotapplication.robot.robot.connector.ControllerRobot.TEA_WITH_MILK;

/**
 * 共享充电宝
 */

public class SharedChargingPoint {

    public static void sharedChargingPointStart(int data) {
        byte[] head = new byte[HEAD_SIZE];
        byte[] address = new byte[ADDRESS_SIZE];
        byte[] functionCode = new byte[FUNCTION_CODE_SIZE];
        byte[] length = new byte[DATA_LENGTH];
        byte[] end = new byte[END_SIZE];

        byte[] data_byte = new byte[TEA_WITH_MILK];
        byte[] data_head = HexUtil.intToByteArray(ControllerRobot.DATA_HEAD);
        head[0] = data_head[2];
        head[1] = data_head[3];

        address[0] = HexUtil.intToByteArray(ControllerRobot.MILK_TEA_MACHINE)[3];

        functionCode[0] = HexUtil.intToByteArray((int) TeaWithMilkConfig.teaWithMilkConfig.get("fruit_juice"))[3];

        byte[] data_end = HexUtil.intToByteArray(ControllerRobot.DATA_END);
        end[0] = data_end[2];
        end[1] = data_end[3];
        data_byte[0] = HexUtil.intToByteArray(data)[3];

        length[0] = HexUtil.intToByteArray(CRC16X25Util.concatAll(head, address, functionCode, length, data_byte, end).length)[3];

        byte[] submit = CRC16X25Util.concatAll(head, address, functionCode, length, data_byte, end);

        LogUtils.w("CRC校验之前的原始数据:" + Arrays.toString(submit));

        //开始CRC校验
        submit = CRC16X25Util.setParamCRC(submit);

        LogUtils.w("CRC校验之后的有符号数据:" + Arrays.toString(submit));

//        submit = unsignedByte(submit);

//        LogUtils.w("CRC校验之后的无符号数据:" + Arrays.toString(submit));

        Controller.startWrite(submit, ControllerRobot.POWER_BANK, "您的充电宝正在出货中请稍后\n");
    }
}
