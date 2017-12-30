package com.robot.cation.robotapplication.robot.singlechip;


import java.util.HashMap;
import java.util.Map;

import static com.robot.cation.robotapplication.robot.singlechip.SingleChipType.ADVERTISING;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipType.COFFEE;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipType.ICE_CREAM;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipType.POWER_BANK;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipType.TEA_WITH_MILK;
import static com.robot.cation.robotapplication.robot.singlechip.SingleChipType.TISSUE;

/**
 * 每一个发送指令和其他发送用到的tips
 */
public class SingleChipTips {

    //奶茶配置
    public static Map teaWithMilkConfig = new HashMap();

    //奶茶提示配置
    public static Map teaWithMilkTips = new HashMap();

    //奶茶提示配置
    public static Map teaWithMilkFailedTips = new HashMap();

    //充电宝配置
    public static Map sharedChargingPointConfig = new HashMap();

    //充电宝tips配置
    public static Map sharedChargingPointTips = new HashMap();

    //充电宝tips配置
    public static Map sharedChargingPointFailedTips = new HashMap();

    //机械臂和冰淇淋区分
    public static Map<String, Integer> mainpulatorConfigType = new HashMap();

    //机械臂位置
    public static Map<String, Integer> mainpulatorConfig = new HashMap();

    //电动门和落杯器
    public static Map<String, Integer> electrically_operated_gate_fun = new HashMap();

    //电动门数据区分
    public static Map<String, Integer> electrically_operated_gate_data = new HashMap();

    //落杯器数据
    public static Map<String, Integer> folling_cup_machine_data = new HashMap();

    static {
        sharedChargingPointConfig.put(0x01, "个充电宝");
        sharedChargingPointConfig.put(0x02, "条TYPE-C数据线");
        sharedChargingPointConfig.put(0x03, "条安卓数据线");
        sharedChargingPointConfig.put(0x04, "条苹果数据线");
    }

    static {
        sharedChargingPointTips.put(0x01, "您的充电宝正在出货中请稍后!");
        sharedChargingPointTips.put(0x02, "您的TYPE-C数据线正在出货中请稍后!");
        sharedChargingPointTips.put(0x03, "您的安卓数据线正在出货中请稍后!");
        sharedChargingPointTips.put(0x04, "您的苹果数据线正在出货中请稍后!");
    }

    static {
        sharedChargingPointFailedTips.put(0x01, "您的充电宝出货失败!");
        sharedChargingPointFailedTips.put(0x02, "您的TYPE-C数据线出货失败");
        sharedChargingPointFailedTips.put(0x03, "您的安卓数据线出货失败");
        sharedChargingPointFailedTips.put(0x04, "您的苹果数据线出货失败");
    }

    static {
        teaWithMilkConfig.put(0x01, "果汁");
        teaWithMilkConfig.put(0x02, "豆浆");
        teaWithMilkConfig.put(0x03, "水");
    }

    static {
        teaWithMilkTips.put(0x01, "您的奶茶(" + teaWithMilkConfig.get(0x01) + ")正在制作中请稍后!");
        teaWithMilkTips.put(0x02, "您的奶茶(" + teaWithMilkConfig.get(0x02) + ")正在制作中请稍后!");
        teaWithMilkTips.put(0x03, "您的奶茶(" + teaWithMilkConfig.get(0x03) + ")正在制作中请稍后!");
    }

    static {
        teaWithMilkFailedTips.put(0x01, "您的奶茶(" + teaWithMilkConfig.get(0x01) + ")制作失败");
        teaWithMilkFailedTips.put(0x02, "您的奶茶(" + teaWithMilkConfig.get(0x02) + ")制作失败");
        teaWithMilkFailedTips.put(0x03, "您的奶茶(" + teaWithMilkConfig.get(0x03) + ")制作失败");
    }

    public static final String MECHANICAL_ARM = "机械臂";

    public static final String ICE_CREAM_ = "冰淇淋";

    static {
        mainpulatorConfigType.put(ICE_CREAM_, 0x01);
        mainpulatorConfigType.put(MECHANICAL_ARM, 0x02);
    }

    public static final String PICK_UP_CUP = "接杯子";

    public static final String MILK_TEA_POSITION = "奶茶位置";

    public static final String POSITION_FRUIT_JUICE = "果汁位置";

    public static final String WATER_LOCATION = "水位置";

    public static final String CHOCOLATE_POSITION = "巧克力位置";

    public static final String ICE_CREAM_POSITION = "冰淇淋位置";

    public static final String GATE_LOCATION = "门口";

    public static final String RESTORATION = "复位";

    static {
        mainpulatorConfig.put(PICK_UP_CUP, 0x01);
        mainpulatorConfig.put(MILK_TEA_POSITION, 0x02);
        mainpulatorConfig.put(POSITION_FRUIT_JUICE, 0x03);
        mainpulatorConfig.put(WATER_LOCATION, 0x04);
        mainpulatorConfig.put(CHOCOLATE_POSITION, 0x05);
        mainpulatorConfig.put(ICE_CREAM_POSITION, 0x06);
    }

    public static final String ELECTRICALLY_OPERATED_GATE_ = "电动门";

    public static final String FOLLING_CUP_MACHINE = "落杯器";

    static {
        electrically_operated_gate_fun.put(ELECTRICALLY_OPERATED_GATE_, 0x01);
        electrically_operated_gate_fun.put(FOLLING_CUP_MACHINE, 0x02);
    }

    public static final String OPEN_DOOR = "开门";

    public static final String CLOSE_DOOR = "关门";

    static {
        electrically_operated_gate_data.put(OPEN_DOOR, 0x01);
        electrically_operated_gate_data.put(CLOSE_DOOR, 0x02);
    }

    public static final String FLOW_ONE = "落杯一个";

    static {
        folling_cup_machine_data.put(FLOW_ONE, 0x01);
    }

    /**
     * 获得语音提示用的
     *
     * @param addressNumber  地址
     * @param functionNumber 功能
     * @return
     */
    public static String getStartTips(int addressNumber, int functionNumber) {
        String tips = "";
        if (addressNumber == TEA_WITH_MILK.getValue()) {
            tips = "您的奶茶正在制作中请稍后!";
        } else if (addressNumber == TISSUE.getValue()) {
            tips = "您的纸巾正在出货中请稍后!";
        } else if (addressNumber == ICE_CREAM.getValue()) {
            tips = "您的冰淇淋正在制作中请稍后!";
        } else if (addressNumber == POWER_BANK.getValue()) {
            tips = (String) sharedChargingPointTips.get(functionNumber);
        } else if (addressNumber == ADVERTISING.getValue()) {
            tips = "您的广告正在生效中请稍后!";
        } else if (addressNumber == COFFEE.getValue()) {
            tips = "您的咖啡正在制作中请稍后!";
        }
        return tips;
    }

    /**
     * 获得语音提示用的
     *
     * @param addressNumber  地址
     * @param functionNumber 功能
     * @return
     */
    public static String getEndTips(int addressNumber, int functionNumber) {
        String tips = "";
        if (addressNumber == TEA_WITH_MILK.getValue()) {
            tips = "您的奶茶制作完成请取走，谢谢惠顾";
        } else if (addressNumber == TISSUE.getValue()) {
            tips = "您的纸巾出货完成请取走，谢谢惠顾";
        } else if (addressNumber == ICE_CREAM.getValue()) {
            tips = "您的冰淇淋制作完成请取走，谢谢惠顾";
        } else if (addressNumber == POWER_BANK.getValue()) {
            tips = (String) sharedChargingPointTips.get(functionNumber);
        } else if (addressNumber == ADVERTISING.getValue()) {
            tips = "您的广告生效完成!";
        } else if (addressNumber == COFFEE.getValue()) {
            tips = "您的咖啡制作完成请取走，谢谢惠顾";
        }
        return tips;
    }

    /**
     * 获得界面显示提示用的
     *
     * @param addressNumber  地址
     * @param functionNumber 功能
     * @return
     */
    public static String getInterfaceStartTips(int addressNumber, int functionNumber) {
        String tips = "";
        if (addressNumber == TEA_WITH_MILK.getValue()) {
            tips = (String) teaWithMilkTips.get(functionNumber);
        } else if (addressNumber == TISSUE.getValue()) {
            tips = "您的纸巾正在出货中请稍后!";
        } else if (addressNumber == ICE_CREAM.getValue()) {
            tips = "您的冰淇淋正在制作中请稍后!";
        } else if (addressNumber == POWER_BANK.getValue()) {
            tips = (String) sharedChargingPointTips.get(functionNumber);
        } else if (addressNumber == ADVERTISING.getValue()) {
            tips = "您的广告正在生效中请稍后!";
        } else if (addressNumber == COFFEE.getValue()) {
            tips = "您的咖啡正在制作中请稍后!";
        }
        return tips;
    }

    /**
     * 获得下单等待提示内容
     *
     * @param addressNumber  地址
     * @param functionNumber 功能
     * @param count          个数
     * @return
     */
    public static String getBuyInfo(int addressNumber, int functionNumber, int count) {
        String tips = "";
        if (addressNumber == TEA_WITH_MILK.getValue()) {
            tips = count + "杯奶茶(" + teaWithMilkConfig.get(functionNumber) + ")";
        } else if (addressNumber == TISSUE.getValue()) {
            tips = count + "个纸巾";
        } else if (addressNumber == ICE_CREAM.getValue()) {
            tips = count + "杯冰淇淋";
        } else if (addressNumber == POWER_BANK.getValue()) {
            tips = count + (String) sharedChargingPointConfig.get(functionNumber);
        } else if (addressNumber == ADVERTISING.getValue()) {
            tips = count + "条广告";
        } else if (addressNumber == COFFEE.getValue()) {
            tips = count + "杯咖啡";
        }
        return tips;
    }

    public static String getFailedTips(int addressNumber, int functionNumber) {
        String tips = "";
        if (addressNumber == TEA_WITH_MILK.getValue()) {
            tips = (String) teaWithMilkFailedTips.get(functionNumber);
        } else if (addressNumber == TISSUE.getValue()) {
            tips = "您的纸巾出货失败";
        } else if (addressNumber == ICE_CREAM.getValue()) {
            tips = "您的冰淇淋制作失败";
        } else if (addressNumber == POWER_BANK.getValue()) {
            tips = (String) sharedChargingPointFailedTips.get(functionNumber);
        } else if (addressNumber == ADVERTISING.getValue()) {
            tips = "您的广告生效失败";
        } else if (addressNumber == COFFEE.getValue()) {
            tips = "您的咖啡制作失败";
        }
        return tips;
    }
}
