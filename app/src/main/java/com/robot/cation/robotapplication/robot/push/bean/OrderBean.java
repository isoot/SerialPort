package com.robot.cation.robotapplication.robot.push.bean;

/**
 * Created by THINK on 2017/12/23.
 */

public class OrderBean {
    //用户昵称
    private String nikeName;
    //订单id
    private int orderId;
    //用户头像
    private String headImg;
    //商品集合
    private String orderGoods;
    //订单类型 1 为普通商品类型订单；2 充电宝订单
    private String ordreType;

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getOrderGoods() {
        return orderGoods;
    }

    public void setOrderGoods(String orderGoods) {
        this.orderGoods = orderGoods;
    }

    public String getOrdreType() {
        return ordreType;
    }

    public void setOrdreType(String ordreType) {
        this.ordreType = ordreType;
    }
}
