package com.robot.cation.robotapplication.robot.utils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * byte[]与16进制字符串相互转换
 */
public class BytesHexStrTranslate {

    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5',
        '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 方法一：
     * byte[] to hex string
     *
     * @param bytes
     * @return
     */
    public static String bytesToHexFun1(byte[] bytes) {
        // 一个byte为8位，可用两个十六进制位标识
        char[] buf = new char[bytes.length * 2];
        int a = 0;
        int index = 0;
        for (byte b : bytes) { // 使用除与取余进行转换
            if (b < 0) {
                a = 256 + b;
            } else {
                a = b;
            }

            buf[index++] = HEX_CHAR[a / 16];
            buf[index++] = HEX_CHAR[a % 16];
        }

        return new String(buf);
    }

    /**
     * 方法二：
     * byte[] to hex string
     *
     * @param bytes
     * @return
     */
    public static String bytesToHexFun2(byte[] bytes) {
        char[] buf = new char[bytes.length * 2];
        int index = 0;
        for (byte b : bytes) { // 利用位运算进行转换，可以看作方法一的变种
            buf[index++] = HEX_CHAR[b >>> 4 & 0xf];
            buf[index++] = HEX_CHAR[b & 0xf];
        }

        return new String(buf);
    }

    /**
     * 方法三：
     * byte[] to hex string
     *
     * @param bytes
     * @return
     */
    public static String bytesToHexFun3(byte[] bytes) {
        StringBuilder buf = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) { // 使用String的format方法进行转换
            buf.append(String.format("%02x", new Integer(b & 0xff)));
        }

        return buf.toString();
    }

    /**
     * 将16进制字符串转换为byte[]
     *
     * @param str
     * @return
     */
    public static byte[] toBytes(String str) {
        if (str == null || str.trim().equals("")) {
            return new byte[0];
        }

        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }

        return bytes;
    }


    public static byte[] UTF8_GBK(byte[] source) {
        byte[] temp = new byte[0];
        if (source != null) {
            try {
                temp = new String(source, 0, source.length).getBytes("GBK");
            } catch (UnsupportedEncodingException e) {
                LogUtils.w(e);
            }
        }
        return temp;
    }

    public static byte[] GBK_UTF8(byte[] source) {
        byte[] temp = new byte[0];
        try {
            temp = new String(source, 0, source.length, "GBK").getBytes("UTF-8");
        } catch (Exception e) {
            LogUtils.w(e);
        }
        return temp;
    }

    public static void main(String[] args) throws Exception {
        byte[] bytes = "请输入数据,以回车键结束\r\n".getBytes("utf-8");
        System.out.println("字节数组为：" + Arrays.toString(bytes));
        System.out.println("方法一：" + bytesToHexFun1(bytes));
        System.out.println("方法二：" + bytesToHexFun2(bytes));
        System.out.println("方法三：" + bytesToHexFun3(bytes));

        System.out.println("==================================");

        String str = "e8afb7e8be93e585a5e695b0e68dae2ce4bba5e59b9ee8bda6e994aee7bb93e69d9f0d0a";
        System.out.println("转换后的字节数组：" + Arrays.toString(toBytes(str)));
        System.out.println(new String(toBytes(str), "utf-8"));
    }

}