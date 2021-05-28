package com.example.orderfood.POJO;

public class OrderModel {

    private String ProductId;
    private String ProductName;
    private String Quantity;
    private String Price;

    public OrderModel() {
    }

    public OrderModel(String productId, String productName, String price,String quantity) {
        ProductId = productId;
        ProductName = productName;
        Price = price;
        Quantity = quantity;
    }



    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }
    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }



}
