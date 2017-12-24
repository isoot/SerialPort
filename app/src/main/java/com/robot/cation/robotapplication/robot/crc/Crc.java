package com.robot.cation.robotapplication.robot.crc;

import com.robot.cation.robotapplication.robot.utils.LogUtils;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by THINK on 2017/12/22.
 */

public class Crc {
    private static byte[] CRC_TABLE_16 = new byte[]
        {(byte) 0x0000, (byte) 0x1189, (byte) 0x2312, (byte) 0x329b, (byte) 0x4624, (byte) 0x57ad, (byte) 0x6536, (byte) 0x74bf,
            (byte) 0x8c48, (byte) 0x9dc1, (byte) 0xaf5a, (byte) (short) (byte) 0xbed3, (byte) 0xca6c, (byte) 0xdbe5, (byte) 0xe97e, (byte) 0xf8f7,
            (byte) 0x1081, (byte) 0x0108, (byte) 0x3393, (byte) 0x221a, (byte) 0x56a5, (byte) 0x472c, (byte) 0x75b7, (byte) 0x643e,
            (byte) 0x9cc9, (byte) 0x8d40, (byte) 0xbfdb, (byte) 0xae52, (byte) 0xdaed, (byte) 0xcb64, (byte) 0xf9ff, (byte) 0xe876,
            (byte) 0x2102, (byte) 0x308b, (byte) 0x0210, (byte) 0x1399, (byte) 0x6726, (byte) 0x76af, (byte) 0x4434, (byte) 0x55bd,
            (byte) 0xad4a, (byte) 0xbcc3, (byte) 0x8e58, (byte) 0x9fd1, (byte) 0xeb6e, (byte) 0xfae7, (byte) 0xc87c, (byte) 0xd9f5,
            (byte) 0x3183, (byte) 0x200a, (byte) 0x1291, (byte) 0x0318, (byte) 0x77a7, (byte) 0x662e, (byte) 0x54b5, (byte) 0x453c,
            (byte) 0xbdcb, (byte) 0xac42, (byte) 0x9ed9, (byte) 0x8f50, (byte) 0xfbef, (byte) 0xea66, (byte) 0xd8fd, (byte) 0xc974,
            (byte) 0x4204, (byte) 0x538d, (byte) 0x6116, (byte) 0x709f, (byte) 0x0420, (byte) 0x15a9, (byte) 0x2732, (byte) 0x36bb,
            (byte) 0xce4c, (byte) 0xdfc5, (byte) 0xed5e, (byte) 0xfcd7, (byte) 0x8868, (byte) 0x99e1, (byte) 0xab7a, (byte) 0xbaf3,
            (byte) 0x5285, (byte) 0x430c, (byte) 0x7197, (byte) 0x601e, (byte) 0x14a1, (byte) 0x0528, (byte) 0x37b3, (byte) 0x263a,
            (byte) 0xdecd, (byte) 0xcf44, (byte) 0xfddf, (byte) 0xec56, (byte) 0x98e9, (byte) 0x8960, (byte) 0xbbfb, (byte) 0xaa72,
            (byte) 0x6306, (byte) 0x728f, (byte) 0x4014, (byte) 0x519d, (byte) 0x2522, (byte) 0x34ab, (byte) 0x0630, (byte) 0x17b9,
            (byte) 0xef4e, (byte) 0xfec7, (byte) 0xcc5c, (byte) 0xddd5, (byte) 0xa96a, (byte) 0xb8e3, (byte) 0x8a78, (byte) 0x9bf1,
            (byte) 0x7387, (byte) 0x620e, (byte) 0x5095, (byte) 0x411c, (byte) 0x35a3, (byte) 0x242a, (byte) 0x16b1, (byte) 0x0738,
            (byte) 0xffcf, (byte) 0xee46, (byte) 0xdcdd, (byte) 0xcd54, (byte) 0xb9eb, (byte) 0xa862, (byte) 0x9af9, (byte) 0x8b70,
            (byte) 0x8408, (byte) 0x9581, (byte) 0xa71a, (byte) 0xb693, (byte) 0xc22c, (byte) 0xd3a5, (byte) 0xe13e, (byte) 0xf0b7,
            (byte) 0x0840, (byte) 0x19c9, (byte) 0x2b52, (byte) 0x3adb, (byte) 0x4e64, (byte) 0x5fed, (byte) 0x6d76, (byte) 0x7cff,
            (byte) 0x9489, (byte) 0x8500, (byte) 0xb79b, (byte) 0xa612, (byte) 0xd2ad, (byte) 0xc324, (byte) 0xf1bf, (byte) 0xe036,
            (byte) 0x18c1, (byte) 0x0948, (byte) 0x3bd3, (byte) 0x2a5a, (byte) 0x5ee5, (byte) 0x4f6c, (byte) 0x7df7, (byte) 0x6c7e,
            (byte) 0xa50a, (byte) 0xb483, (byte) 0x8618, (byte) 0x9791, (byte) 0xe32e, (byte) 0xf2a7, (byte) 0xc03c, (byte) 0xd1b5,
            (byte) 0x2942, (byte) 0x38cb, (byte) 0x0a50, (byte) 0x1bd9, (byte) 0x6f66, (byte) 0x7eef, (byte) 0x4c74, (byte) 0x5dfd,
            (byte) 0xb58b, (byte) 0xa402, (byte) 0x9699, (byte) 0x8710, (byte) 0xf3af, (byte) 0xe226, (byte) 0xd0bd, (byte) 0xc134,
            (byte) 0x39c3, (byte) 0x284a, (byte) 0x1ad1, (byte) 0x0b58, (byte) 0x7fe7, (byte) 0x6e6e, (byte) 0x5cf5, (byte) 0x4d7c,
            (byte) 0xc60c, (byte) 0xd785, (byte) 0xe51e, (byte) 0xf497, (byte) 0x8028, (byte) 0x91a1, (byte) 0xa33a, (byte) 0xb2b3,
            (byte) 0x4a44, (byte) 0x5bcd, (byte) 0x6956, (byte) 0x78df, (byte) 0x0c60, (byte) 0x1de9, (byte) 0x2f72, (byte) 0x3efb,
            (byte) 0xd68d, (byte) 0xc704, (byte) 0xf59f, (byte) 0xe416, (byte) 0x90a9, (byte) 0x8120, (byte) 0xb3bb, (byte) 0xa232,
            (byte) 0x5ac5, (byte) 0x4b4c, (byte) 0x79d7, (byte) 0x685e, (byte) 0x1ce1, (byte) 0x0d68, (byte) 0x3ff3, (byte) 0x2e7a,
            (byte) 0xe70e, (byte) 0xf687, (byte) 0xc41c, (byte) 0xd595, (byte) 0xa12a, (byte) 0xb0a3, (byte) 0x8238, (byte) 0x93b1,
            (byte) 0x6b46, (byte) 0x7acf, (byte) 0x4854, (byte) 0x59dd, (byte) 0x2d62, (byte) 0x3ce};

    public static long Calc_CRC16(long pData, long nLength) {
        long FCS;    // FCS: Frame Check Sequence

        FCS = 0xFFFF;

        while (nLength > 0) {
            BigDecimal decimal = new BigDecimal(pData);
            FCS = (FCS >> 8) ^ CRC_TABLE_16[(int) ((FCS ^ pData) & 0xFF)];
            nLength--;
            pData++;
        }

        return ~FCS;
    }

    public static String Calc_CRC16(byte[] submit, long nLength) {
        byte[] temp = new byte[submit.length];
        DataInputStream stream = new DataInputStream(new ByteArrayInputStream(submit));
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < submit.length; i++) {
            try {
                int int_value = stream.readUnsignedByte();
                if (int_value <= 9) {
                    buffer.append("0" + int_value);
                } else {
                    buffer.append(int_value);
                }
            } catch (IOException e) {
                LogUtils.w("转无符号失败:", e);
            }
        }
        Calc_CRC16(Integer.parseInt(buffer.toString()), nLength);
        return "";
    }
}
