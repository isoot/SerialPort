package com.robot.cation.robotapplication.robot.push.bean;

/**
 * Created by THINK on 2017/12/23.
 */

public class PushBean {

    /**
     * status : 1
     * msg : 发送订单信息
     * data :
     * code : 1
     */

    private int status;
    private String msg;
    private TissueBean data;
    private int code;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public TissueBean getData() {
        return data;
    }

    public void setData(TissueBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
