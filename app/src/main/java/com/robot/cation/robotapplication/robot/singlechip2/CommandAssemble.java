package com.robot.cation.robotapplication.robot.singlechip2;


import com.robot.cation.robotapplication.robot.crc.CRC16X25Util;
import com.robot.cation.robotapplication.robot.utils.HexUtil;
import com.robot.cation.robotapplication.robot.utils.LogUtils;

import java.util.Arrays;

import static com.robot.cation.robotapplication.robot.constant.SingleChipConstant.ADDRESS_SIZE;
import static com.robot.cation.robotapplication.robot.constant.SingleChipConstant.DATA_END;
import static com.robot.cation.robotapplication.robot.constant.SingleChipConstant.DATA_HEAD;
import static com.robot.cation.robotapplication.robot.constant.SingleChipConstant.DATA_LENGTH;
import static com.robot.cation.robotapplication.robot.constant.SingleChipConstant.END_SIZE;
import static com.robot.cation.robotapplication.robot.constant.SingleChipConstant.FUNCTION_CODE_SIZE;
import static com.robot.cation.robotapplication.robot.constant.SingleChipConstant.HEAD_SIZE;


/**
 * 组装命令
 */
public class CommandAssemble {


    /**
     * 组装一个指令
     *
     * @param addressNumber  地址
     * @param functionNumber 功能
     * @param dataNumber     数据
     * @param dataByteSize   数据大小
     * @return
     */
    public static byte[] assembleCommand(int addressNumber, int functionNumber, int dataNumber, int dataByteSize) {
        //包头大小
        byte[] head = null;
        //地址大小
        byte[] address = null;
        //功能码大小
        byte[] functionCode = null;
        //包的长度 不加校验码
        byte[] length = new byte[DATA_LENGTH];
        //包尾
        byte[] end = null;
        //数据
        byte[] data = null;
        //赋值头部信息
        byte[] data_head = HexUtil.intToByteArray(DATA_HEAD);
        int head_length = data_head.length > HEAD_SIZE ? HEAD_SIZE : data_head.length;
        head = Arrays.copyOfRange(data_head, data_head.length - head_length, data_head.length);

        //地址信息赋值
        byte[] address_byte = HexUtil.intToByteArray(addressNumber);
        int address_length = address_byte.length > ADDRESS_SIZE ? ADDRESS_SIZE : address_byte.length;
        address = Arrays.copyOfRange(address_byte, address_byte.length - address_length, address_byte.length);

        //功能码
        byte[] function_byte = HexUtil.intToByteArray(functionNumber);
        int function_length = function_byte.length > FUNCTION_CODE_SIZE ? FUNCTION_CODE_SIZE : function_byte.length;
        functionCode = Arrays.copyOfRange(function_byte, function_byte.length - function_length, function_byte.length);

        //包尾
        byte[] end_byte = HexUtil.intToByteArray(DATA_END);
        int data_length = end_byte.length > dataByteSize ? dataByteSize : end_byte.length;
        end = Arrays.copyOfRange(end_byte, end_byte.length - data_length, end_byte.length);

        //数据
        byte[] data_byte = HexUtil.intToByteArray(dataNumber);
        int end_length = data_byte.length > END_SIZE ? END_SIZE : data_byte.length;
        data = Arrays.copyOfRange(end_byte, end_byte.length - end_length, end_byte.length);

        //长度
        byte[] length_byte = HexUtil.intToByteArray(CRC16X25Util.concatAll(head, address, functionCode, length, data, end).length);
        int length_length = length_byte.length > DATA_LENGTH ? DATA_LENGTH : length_byte.length;
        length = Arrays.copyOfRange(length_byte, length_byte.length - length_length, length_byte.length);

        //组装成一个数组
        byte[] submit = CRC16X25Util.concatAll(head, address, functionCode, length, data, end);

        LogUtils.w("CRC校验之前的原始数据:" + Arrays.toString(submit));

        //开始CRC校验 并且添加在最后两位
        submit = CRC16X25Util.setParamCRC(submit);

        LogUtils.w("CRC校验之后的有符号数据:" + Arrays.toString(submit));

        return submit;
    }
}
