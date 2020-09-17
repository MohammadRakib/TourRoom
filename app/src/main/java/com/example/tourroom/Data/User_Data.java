package com.example.tourroom.Data;

public class User_Data {
    String uid;
    String uEmail;
    String name;
    String image;

    public User_Data() {

    }

    public User_Data(String uid, String uEmail, String name, String image) {
        this.uid = uid;
        this.uEmail = uEmail;
        this.name = name;
        this.image = image;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }
}
