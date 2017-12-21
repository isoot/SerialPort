package com.robot.cation.robotapplication.robot.robot.tissue;

import com.robot.cation.robotapplication.robot.robot.connector.ControllerRobot;
import com.robot.cation.robotapplication.robot.utils.HexUtil;

/**
 * 纸巾
 */

public class Tissue {

    public static void TissueStart(int tissueNumber) {
        //定义数据大小
        byte[] head = new byte[2];
        byte[] address = new byte[1];
        byte[] functionCode = new byte[1];
        byte[] length = new byte[1];
        byte[] end = new byte[2];
        byte[] CRC_height = new byte[1];
        byte[] CRC_low = new byte[1];

        //填充数据
        byte[] data_head = HexUtil.intToByteArray(ControllerRobot.DATA_HEAD);
        head[0] = data_head[2];
        head[1] = data_head[3];

        address[0] = HexUtil.intToByteArray(ControllerRobot.PAPER_TOWEL_MACHINE)[3];

        functionCode[0] = HexUtil.intToByteArray((int) TissueConfig.tissueConfig.get("function_code"))[3];

        byte[] data_end = HexUtil.intToByteArray(ControllerRobot.DATA_END);
        end[0] = data_end[2];
        end[1] = data_end[3];




    }
}
