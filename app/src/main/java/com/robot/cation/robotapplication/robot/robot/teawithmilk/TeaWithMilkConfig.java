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
        teaWithMilkConfig.put(0x01, "果汁");
        //
        teaWithMilkConfig.put(0x02, "豆浆");
        teaWithMilkConfig.put(0x03, "水");
    }
}
