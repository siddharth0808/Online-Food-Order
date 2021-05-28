package com.example.orderfood.POJO;

public class CommonModel {

    public static OwnerModel currentUser;

    public static final String UPDATE="update";
    public static final String DELETE="delete";

    public static String convertCodeToStatus(String status) {
        if(status.equals("0")){
            return "Placed";
        }else if(status.equals("1")){
            return "Preparing";
        }else{
            return "Shipped";
        }

    }
}
