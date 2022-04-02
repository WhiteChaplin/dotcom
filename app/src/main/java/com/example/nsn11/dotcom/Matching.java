package com.example.nsn11.dotcom;

public class Matching {
    public String shopName;
    public String estimateNumber;
    public String userUID;

    Matching(){

    }

    Matching(String shopName,String estimateNumber,String userUID){
        this.shopName = shopName;
        this.estimateNumber = estimateNumber;
        this.userUID = userUID;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopName() {
        return shopName;
    }

    public String getEstimateNumber() {
        return estimateNumber;
    }

    public void setEstimateNumber(String estimateNumber) {
        this.estimateNumber = estimateNumber;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }
}
