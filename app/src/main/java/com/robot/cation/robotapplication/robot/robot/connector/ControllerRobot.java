package com.robot.cation.robotapplication.robot.robot.connector;

import com.robot.cation.robotapplication.robot.robot.coffee.CoffeeConfig;
import com.robot.cation.robotapplication.robot.robot.dressingmirror.DressingMirrorConfig;
import com.robot.cation.robotapplication.robot.robot.icecream.IceCreamConfig;
import com.robot.cation.robotapplication.robot.robot.led.LedConfig;
import com.robot.cation.robotapplication.robot.robot.lottery.LotteryConfig;
import com.robot.cation.robotapplication.robot.robot.photoprint.PhotoPrintConfig;
import com.robot.cation.robotapplication.robot.robot.sensor.SensorConfig;
import com.robot.cation.robotapplication.robot.robot.teawithmilk.TeaWithMilk;
import com.robot.cation.robotapplication.robot.robot.tissue.Tissue;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by THINK on 2017/12/21.
 */

public class ControllerRobot implements Robot {

    //包头
    public static final int DATA_HEAD = 0xFFFE;
    //包尾
    public static final int DATA_END = 0xFEFF;
    //奶茶机
    public static final int MILK_TEA_MACHINE = 0x01;
    //纸巾机
    public static final int PAPER_TOWEL_MACHINE = 0x02;
    //冰淇淋机
    public static final int ICE_CREAM_MACHINE = 0x03;
    //充电宝
    public static final int POWER_BANK = 0x04;
    //广告机
    public static final int ADVISEMENT_PLAYER = 0x05;
    //咖啡机
    public static final int COFFEE_MAKER = 0x06;

    public static final int UNSIGNED_MAX_VALUE = 127;
    public static final int COVERING_POSITION = 1;
    public static final int HEAD_SIZE = 2;
    public static final int ADDRESS_SIZE = 1;
    public static final int FUNCTION_CODE_SIZE = 1;
    public static final int DATA_LENGTH = 1;
    public static final int END_SIZE = 2;
    public static final int TISSUE_SIZE = 1;
    private static ControllerRobot instance;

    private ControllerRobot() {
        // Do nothing
    }

    public static ControllerRobot getInstance() {
        if (null == instance) {
            instance = new ControllerRobot();
        }
        return instance;
    }

    @Override
    public void iceCream(IceCreamConfig config) {

    }

    @Override
    public void coffee(CoffeeConfig config) {

    }

    @Override
    public void teaWithMilk(int data) {
        TeaWithMilk.TeaWithMilkStart(data);
    }

    @Override
    public void lottery(LotteryConfig config) {

    }

    @Override
    public void photoPrint(PhotoPrintConfig config) {

    }

    @Override
    public void sharedChargingPoint(String tissueNumber) {

    }

    @Override
    public void tissue(int tissueNumber) {
        Tissue.TissueStart(tissueNumber);
    }

    @Override
    public void vrDressingMirror(DressingMirrorConfig config) {

    }

    @Override
    public void led(LedConfig config) {

    }

    @Override
    public void sensor(SensorConfig config) {

    }


    /**
     * 转无符号byte
     *
     * @param submit
     * @return
     */
    public static byte[] unsignedByte(byte[] submit) {
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

            }
        }
        return submit;
    }

    public static String getName(int id) {
        String str = "";
        switch (id) {
            case MILK_TEA_MACHINE:
                str = "杯奶茶";
                break;
            case PAPER_TOWEL_MACHINE:
                str = "个纸巾";
                break;
            case ICE_CREAM_MACHINE:
                str = "个冰淇淋";
                break;
            case POWER_BANK:
                str = "个充电宝";
                break;
            case COFFEE_MAKER:
                str = "杯咖啡";
                break;
            case ADVISEMENT_PLAYER:
                str = "条广告";
                break;
        }
        return str;
    }
}
