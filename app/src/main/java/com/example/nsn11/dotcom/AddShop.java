package com.example.nsn11.dotcom;

public class AddShop {
    public String shopName;
    public String shopCity;
    public String shopLatitude;
    public String shopLongitude;
    public String userID;
    public AddShop()
    {

    }

    public AddShop(String shopName,String shopCity,String shopLatitude,String shopLongitude,String userID)
    {
        this.shopName = shopName;
        this.shopCity = shopCity;
        this.shopLatitude = shopLatitude;
        this.shopLongitude = shopLongitude;
        this.userID = userID;
    }

    public String getShopName() {
        return shopName;
    }

    public String getShopLatitude() {
        return shopLatitude;
    }

    public String getShopLongitude() {
        return shopLongitude;
    }

    public String getShopCity() {
        return shopCity;
    }

    public void setShopCity(String shopCity) {
        this.shopCity = shopCity;
    }

    public void setShopLatitude(String shopLatitude) {
        this.shopLatitude = shopLatitude;
    }

    public void setShopLongitude(String shopLongitude) {
        this.shopLongitude = shopLongitude;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }


    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

}
