package com.example.nsn11.dotcom;

public class MapPoint {
    private String Name;
    private String City;
    private double latitude;
    private double longitude;
    private String Shop_ID;

    public MapPoint()
    {
        super();
    }

    public MapPoint(String Name,String City,double latitude,double longitude,String shop_ID)
    {
        this.Name = Name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.City = City;
        this.Shop_ID = shop_ID;
    }

    public String getName()
    {
        return Name;
    }

    public void setName(String Name)
    {
        this.Name = Name;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCity() {
        return City;
    }

    public String getShop_ID() {
        return Shop_ID;
    }

    public void setShop_ID(String shop_ID) {
        Shop_ID = shop_ID;
    }

}
