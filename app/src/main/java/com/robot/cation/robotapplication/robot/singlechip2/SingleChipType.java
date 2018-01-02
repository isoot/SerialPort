package com.robot.cation.robotapplication.robot.singlechip2;


/**
 * 设备地址和名称
 */
public enum SingleChipType {

    TEA_WITH_MILK(0x01, "奶茶"),
    TISSUE(0x02, "纸巾"),
    ICE_CREAM(0x03, "冰淇淋和机械臂"),
    POWER_BANK(0x04, "充电宝和数据线"),//数据线和充电宝
    ADVERTISING(0x05, "广告"),
    COFFEE(0x06, "咖啡"),
    ELECTRICALLY_OPERATED_GATE(0x07, "电动门和落杯器");

    private int value;

    private String name;

    SingleChipType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
