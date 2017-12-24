package com.robot.cation.robotapplication.robot.robot.connector;

import com.robot.cation.robotapplication.robot.robot.coffee.CoffeeConfig;
import com.robot.cation.robotapplication.robot.robot.dressingmirror.DressingMirrorConfig;
import com.robot.cation.robotapplication.robot.robot.icecream.IceCreamConfig;
import com.robot.cation.robotapplication.robot.robot.led.LedConfig;
import com.robot.cation.robotapplication.robot.robot.lottery.LotteryConfig;
import com.robot.cation.robotapplication.robot.robot.photoprint.PhotoPrintConfig;
import com.robot.cation.robotapplication.robot.robot.sensor.SensorConfig;
import com.robot.cation.robotapplication.robot.robot.sharedchargingpoint.SharedChargingPointConfig;
import com.robot.cation.robotapplication.robot.robot.teawithmilk.TeaWithMilkConfig;
import com.robot.cation.robotapplication.robot.robot.tissue.Tissue;

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
    public void teaWithMilk(TeaWithMilkConfig config) {

    }

    @Override
    public void lottery(LotteryConfig config) {

    }

    @Override
    public void photoPrint(PhotoPrintConfig config) {

    }

    @Override
    public void sharedChargingPoint(SharedChargingPointConfig config) {

    }

    @Override
    public void tissue(String tissueNumber) {
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

}
