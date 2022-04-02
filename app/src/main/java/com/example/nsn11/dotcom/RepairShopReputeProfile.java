package com.example.nsn11.dotcom;

public class RepairShopReputeProfile {
    String name;
    String title;
    String repute;
    String rating;

    RepairShopReputeProfile(String name,String title,String repute, String rating){
        this.name = name;
        this.title = title;
        this.repute = repute;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRepute() {
        return repute;
    }

    public void setRepute(String repute) {
        this.repute = repute;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}

