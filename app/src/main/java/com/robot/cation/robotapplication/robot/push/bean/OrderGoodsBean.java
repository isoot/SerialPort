package com.robot.cation.robotapplication.robot.push.bean;

/**
 * Created by THINK on 2017/12/23.
 */

public class OrderGoodsBean {
    /**
     * orderId : 4
     * goodsId : 5
     * goodsNumber : 01
     * count : 1
     * functionNumber : 02
     */

    private int orderId;
    private int goodsId;
    private String goodsNumber;
    private int count;
    private String functionNumber;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(String goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getFunctionNumber() {
        return functionNumber;
    }

    public void setFunctionNumber(String functionNumber) {
        this.functionNumber = functionNumber;
    }
}
