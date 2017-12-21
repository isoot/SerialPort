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

/**
 * 定义功能接口
 */
public interface Robot {

    /**
     * 冰淇淋
     *
     * @param config 冰淇淋配置
     */
    void iceCream(IceCreamConfig config);

    /**
     * 咖啡
     *
     * @param config 咖啡配置
     */
    void coffee(CoffeeConfig config);

    /**
     * 奶茶
     *
     * @param config 奶茶配置
     */
    void teaWithMilk(TeaWithMilkConfig config);

    /**
     * 彩票
     *
     * @param config 彩票配置
     */
    void lottery(LotteryConfig config);

    /**
     * 照片打印
     *
     * @param config 照片打印配置
     */
    void photoPrint(PhotoPrintConfig config);

    /**
     * 共享充电宝
     *
     * @param config 共享充电宝配置
     */
    void sharedChargingPoint(SharedChargingPointConfig config);

    /**
     * 纸巾
     *
     * @param tissueNumber 纸巾数量
     */
    void tissue(int tissueNumber);

    /**
     * VR试衣服镜
     *
     * @param config VR试衣服镜配置
     */
    void vrDressingMirror(DressingMirrorConfig config);

    /**
     * LED显示
     *
     * @param config LED显示配置
     */
    void led(LedConfig config);

    /**
     * 传感器
     *
     * @param config 传感器配置
     */
    void sensor(SensorConfig config);
}
