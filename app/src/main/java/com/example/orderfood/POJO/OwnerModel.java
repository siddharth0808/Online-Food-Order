package com.example.orderfood.POJO;

public class OwnerModel {

    private String Canteen_Name;
    private String Owner_Name;
    private String Email;
    private String Canteen_address;
    private String Passsword;
    private String Owner_Mobile;
    public OwnerModel() {
    }

    public OwnerModel(String canteen_Name, String owner_Name, String email, String canteen_address, String passsword) {
        Canteen_Name = canteen_Name;
        Owner_Name = owner_Name;
        Email = email;
        Canteen_address = canteen_address;
        Passsword = passsword;
    }

    public String getOwner_Mobile() {
        return Owner_Mobile;
    }

    public void setOwner_Mobile(String owner_Mobile) {
        Owner_Mobile = owner_Mobile;
    }

    public String getCanteen_Name() {
        return Canteen_Name;
    }

    public void setCanteen_Name(String canteen_Name) {
        Canteen_Name = canteen_Name;
    }

    public String getOwner_Name() {
        return Owner_Name;
    }

    public void setOwner_Name(String owner_Name) {
        Owner_Name = owner_Name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCanteen_address() {
        return Canteen_address;
    }

    public void setCanteen_address(String canteen_address) {
        Canteen_address = canteen_address;
    }

    public String getPasssword() {
        return Passsword;
    }

    public void setPasssword(String passsword) {
        Passsword = passsword;
    }
}
