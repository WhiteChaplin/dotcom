package com.example.nsn11.dotcom;

public class RepairShopProfile {
    public String title;
    public String choice;
    public String date;
    public String state;
    public String estimateNumber;

    public RepairShopProfile(String title,String choice,String date,String state,String estimateNumber){
        this.title = title;
        this.choice = choice;
        this.date = date;
        this.state = state;
        this.estimateNumber = estimateNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEstimateNumber() {
        return estimateNumber;
    }

    public void setEstimateNumber(String estimateNumber) {
        this.estimateNumber = estimateNumber;
    }
}
