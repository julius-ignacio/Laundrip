package com.example.laundrip;


public class users {

    String userId;
    String name;
    String profile;
    String address;
    String password;
    String accountType;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }











    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    public users(String userId, String name, String profile, String address, String password, String accountType) {
        this.userId = userId;
        this.name = name;
        this.profile = profile;
        this.address = address;
        this.password = password;
        this.accountType = getAccountType();
    }

    public users(){}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
