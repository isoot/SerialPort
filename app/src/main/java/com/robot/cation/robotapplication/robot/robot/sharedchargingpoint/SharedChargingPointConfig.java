package com.robot.cation.robotapplication.robot.robot.sharedchargingpoint;

import java.util.HashMap;
import java.util.Map;

/**
 * 共享充电宝配置
 */

public class SharedChargingPointConfig {
    public static Map sharedChargingPointConfig = new HashMap();

    static {
        sharedChargingPointConfig.put(0x01, "苹果数据线");
        sharedChargingPointConfig.put(0x02, "TYPE-C数据线");
        sharedChargingPointConfig.put(0x03, "安卓数据线");
    }
}
