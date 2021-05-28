package com.example.orderfood.POJO;

public class Model {
    String ItemName,ItemPrice,ItemImgUri,ItemDescription;

    public Model(){

    }
    public Model(String itemName, String itemPrice, String itemImgUri,String itemDescription) {
        ItemName = itemName;
        ItemPrice = itemPrice;
        ItemImgUri = itemImgUri;
        ItemDescription=itemDescription;
    }


    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(String itemPrice) {
        ItemPrice = itemPrice;
    }

    public String getItemImgUri() {
        return ItemImgUri;
    }

    public void setItemImgUri(String itemImgUri) {
        ItemImgUri = itemImgUri;
    }

    public String getItemDescription() {
        return ItemDescription;
    }

    public void setItemDescription(String itemDescription) {
        ItemDescription = itemDescription;
    }
}
