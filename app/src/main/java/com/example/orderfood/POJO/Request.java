package com.example.orderfood.POJO;

import java.util.List;

public class Request {

    private String phone;
    private String name;
    private String total;
    private String pay_mode;
    private String status;
    private List<OrderModel> foods;

    public Request() {
    }

    public Request(String phone, String name, String total, String pay_mode, List<OrderModel> foods) {
        this.phone = phone;
        this.name = name;
        this.total = total;
        this.pay_mode = pay_mode;
        this.foods = foods;
        this.status="0";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPay_mode() {
        return pay_mode;
    }

    public void setPay_mode(String pay_mode) {
        this.pay_mode = pay_mode;
    }

    public List<OrderModel> getFoods() {
        return foods;
    }

    public void setFoods(List<OrderModel> foods) {
        this.foods = foods;
    }

}
