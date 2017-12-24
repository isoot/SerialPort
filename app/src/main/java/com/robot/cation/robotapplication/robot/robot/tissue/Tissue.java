package com.robot.cation.robotapplication.robot.robot.tissue;

import com.robot.cation.robotapplication.robot.controller.Controller;
import com.robot.cation.robotapplication.robot.crc.CrcOperateUtil;
import com.robot.cation.robotapplication.robot.robot.connector.ControllerRobot;
import com.robot.cation.robotapplication.robot.utils.HexUtil;
import com.robot.cation.robotapplication.robot.utils.LogUtils;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * 纸巾
 */

public class Tissue {

    public static final int UNSIGNED_MAX_VALUE = 127;
    public static final int COVERING_POSITION = 1;
    public static final int HEAD_SIZE = 2;
    public static final int ADDRESS_SIZE = 1;
    public static final int FUNCTION_CODE_SIZE = 1;
    public static final int DATA_LENGTH = 1;
    public static final int END_SIZE = 2;

    public static void TissueStart(String data) {
        //定义数据大小
        byte[] head = new byte[HEAD_SIZE];
        byte[] address = new byte[ADDRESS_SIZE];
        byte[] functionCode = new byte[FUNCTION_CODE_SIZE];
        byte[] length = new byte[DATA_LENGTH];
        byte[] end = new byte[END_SIZE];

        //填充数据
        byte[] data_head = HexUtil.intToByteArray(ControllerRobot.DATA_HEAD);
        head[0] = data_head[2];
        head[1] = data_head[3];

        address[0] = HexUtil.intToByteArray(ControllerRobot.PAPER_TOWEL_MACHINE)[3];

        functionCode[0] = HexUtil.intToByteArray((int) TissueConfig.tissueConfig.get("function_code"))[3];

        byte[] data_end = HexUtil.intToByteArray(ControllerRobot.DATA_END);
        end[0] = data_end[2];
        end[1] = data_end[3];


        byte[] data_byte = HexUtil.intToByteArray(Integer.parseInt(data));

        length[0] = HexUtil.intToByteArray(CrcOperateUtil.concatAll(head, address, functionCode, length, data_byte, end).length)[3];


        // 最终提交至服务器的byte数据，未添加crc校验
        byte[] submit = CrcOperateUtil.concatAll(head, address, functionCode, length, data_byte, end);

//        long value=Crc.Calc_CRC16(submit,length[0]);

        // 给submit数组添加两位CRC校验
        submit = CrcOperateUtil.setParamCRC(submit);
        LogUtils.w("发送数据有符号指令:" + Arrays.toString(submit));
        submit = unsignedByte(submit);
        LogUtils.w("发送数据有无符号指令:" + Arrays.toString(submit));
        Controller.getInstance().writeMessage(submit);
    }

    private static byte[] unsignedByte(byte[] submit) {
        DataInputStream stream = new DataInputStream(new ByteArrayInputStream(submit));
        for (int i = 0; i < submit.length; i++) {
            try {
                int int_value = stream.readUnsignedByte();
                if (int_value > UNSIGNED_MAX_VALUE) {
                    submit[i] = (byte) (~int_value + COVERING_POSITION);
                } else {
                    submit[i] = (byte) int_value;
                }
            } catch (IOException e) {
                LogUtils.w("转无符号失败:", e);
            }
        }
        return submit;
    }
}
