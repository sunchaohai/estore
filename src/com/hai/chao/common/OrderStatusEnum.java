package com.hai.chao.common;

public enum OrderStatusEnum {

    ORDER_S1("待付款",1),
    ORDER_S2("已付款",2),
    ORDER_S3("已过期",3);

    private String message;
    private int code;
    OrderStatusEnum(String msg, int code) {
        this.message = msg;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
