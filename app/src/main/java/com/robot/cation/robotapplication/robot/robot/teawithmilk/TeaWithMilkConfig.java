package com.robot.cation.robotapplication.robot.robot.teawithmilk;

import java.util.HashMap;
import java.util.Map;

/**
 * 奶茶配置
 */

public class TeaWithMilkConfig {
    public static Map teaWithMilkConfig = new HashMap();

    static {
        //果汁
        teaWithMilkConfig.put("fruit_juice", 0x01);
        //豆浆
        teaWithMilkConfig.put("soybean_milk", 0x02);
    }
}
