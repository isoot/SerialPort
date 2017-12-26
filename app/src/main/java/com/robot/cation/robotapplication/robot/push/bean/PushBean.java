package com.robot.cation.robotapplication.robot.push.bean;


import java.util.List;

/**
 * 推送基础bean
 */
public class PushBean {



    private int status;
    private String msg;
    private DataBean data;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class DataBean {

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

        public static class OrderGoodsBean {
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
    }
}
