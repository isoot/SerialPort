package com.robot.cation.robotapplication.robot.robot.tissue;

import java.util.HashMap;
import java.util.Map;

/**
 * 纸巾配置
 */

public class TissueConfig {

    public static Map tissueConfig = new HashMap();

    static {
        //功能码
        tissueConfig.put("function_code",0x01);
        //出纸巾
        tissueConfig.put("the_paper_towel",0x02);
    }
}
