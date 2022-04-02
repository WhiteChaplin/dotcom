package com.example.nsn11.dotcom;

public class EstimateNum {
    public String estimateNum;
    public String userID;
    EstimateNum()
    {

    }
    EstimateNum(String estimateNum,String userID)
    {
        this.estimateNum = estimateNum;
        this.userID = userID;
    }

    public String getEstimateNum() {
        return estimateNum;
    }

    public void setEstimateNum(String estimateNum) {
        this.estimateNum = estimateNum;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

}
