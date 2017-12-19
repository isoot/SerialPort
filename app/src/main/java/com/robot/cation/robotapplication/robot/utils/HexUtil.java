package com.robot.cation.robotapplication.robot.utils;

/**
 * ClassName: HexUtil <br/>
 * Function: 16进制字节数组与10进制字节数组转换工具类 <br/>
 * date: 2017年1月23日 下午10:58:17 <br/>
 *
 * @author JohnFNash
 * @since JDK 1.6
 */
public class HexUtil {

    public static String hexToString(byte[] soure) {
        String content = "";
        try {
            byte[] temp = new String(soure, 0, soure.length, "GBK").getBytes("UTF-8");
            content = new String(temp);
        } catch (Exception e) {
            LogUtils.w(e);
        }
        return content;
    }

    public static String stringToHex(byte[] soure) {
        String content = "";
        try {
            byte[] temp = new String(soure, 0, soure.length, "UTF-8").getBytes("GBK");
            content = new String(temp);
        } catch (Exception e) {
            LogUtils.w(e);
        }
        return content;
    }
}  