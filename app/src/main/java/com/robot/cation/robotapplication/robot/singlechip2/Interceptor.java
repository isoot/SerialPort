package com.robot.cation.robotapplication.robot.singlechip2;


import static com.robot.cation.robotapplication.robot.constant.SingleChipConstant.ADDRESS_SIZE;
import static com.robot.cation.robotapplication.robot.constant.SingleChipConstant.HEAD_SIZE;

/**
 * 数据拦截
 * 接收数据的方式是不停的去读取
 * 根据类型的不同做相应的操作
 * 但是由于流程的不同不能执行统一的任务
 * 需要任务细化，每一个任务实现独立流程，实现多模块配置化管理
 */
public class Interceptor {

    //地址 用来区分指令是属于哪个单机片
    private int address;

    //功能码
    private int functionCode;

    private DataCallBack callBack;

    public Interceptor() {

    }

    public Interceptor(int address, int functionCode, DataCallBack callBack) {
        this.address = address;
        this.functionCode = functionCode;
        this.callBack = callBack;
    }

    public boolean interceptor(byte[] data) {
        if (data[HEAD_SIZE] == address && data[HEAD_SIZE + ADDRESS_SIZE] == functionCode) {
            return true;
        }
        return false;
    }


    public int getAddress() {
        return address;
    }

    public void setAddress(byte address) {
        this.address = address;
    }

    public int getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(byte functionCode) {
        this.functionCode = functionCode;
    }

    public DataCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(DataCallBack callBack) {
        this.callBack = callBack;
    }

    public interface DataCallBack {
        void interceptor(byte[] command);

        void onFailedSendCommand(int code);
    }
}
