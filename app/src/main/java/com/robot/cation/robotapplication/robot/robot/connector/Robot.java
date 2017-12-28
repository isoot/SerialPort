package com.robot.cation.robotapplication.robot.robot.connector;

/**
 * 定义功能接口
 */
public interface Robot {

    /**
     * 冰淇淋
     *
     * @param config 冰淇淋配置
     */
    void iceCream(String config);

    /**
     * 咖啡
     *
     * @param config 咖啡配置
     */
    void coffee(String config);

    /**
     * 奶茶
     *  @param functionNumber
     * @param goodsNumber
     */
    void teaWithMilk(int functionNumber, int goodsNumber);

    /**
     * 彩票
     *
     * @param config 彩票配置
     */
    void lottery(String config);

    /**
     * 照片打印
     *
     * @param config 照片打印配置
     */
    void photoPrint(String config);

    /**
     * 共享充电宝
     *
     * @param tissueNumber
     */
    void sharedChargingPoint(String tissueNumber);

    /**
     * 纸巾
     *
     * @param functionNumber 纸巾数量
     * @param goodsNumber
     */
    void tissue(int functionNumber, int goodsNumber);

    /**
     * VR试衣服镜
     *
     * @param config VR试衣服镜配置
     */
    void vrDressingMirror(String config);

    /**
     * LED显示
     *
     * @param config LED显示配置
     */
    void led(String config);

    /**
     * 传感器
     *
     * @param config 传感器配置
     */
    void sensor(String config);
}
