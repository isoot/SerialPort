package com.robot.cation.robotapplication.robot.push.bean;

import java.util.List;

/**
 * Created by THINK on 2017/12/23.
 */

public class TissueBean {


    /**
     * nikeName : 321321
     * orderId : 4
     * headImg : http://
     * orderGoods : [{"orderId":4,"goodsId":5,"goodsNumber":"01","count":1,"functionNumber":"02"}]
     * ordreType : 1
     */

    private String nikeName;
    private int orderId;
    private String headImg;
    private int ordreType;
    private List<OrderGoodsBean> orderGoods;

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

    public int getOrdreType() {
        return ordreType;
    }

    public void setOrdreType(int ordreType) {
        this.ordreType = ordreType;
    }

    public List<OrderGoodsBean> getOrderGoods() {
        return orderGoods;
    }

    public void setOrderGoods(List<OrderGoodsBean> orderGoods) {
        this.orderGoods = orderGoods;
    }

}
