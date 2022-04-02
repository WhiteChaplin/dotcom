package com.example.nsn11.dotcom;

public class ArrayListStoreReputeData {
    String estimateNumber;
    String repute_Rating;
    String str_Repute;
    String userID;

    public ArrayListStoreReputeData(String estimateNumber, String repute_Rating, String str_Repute, String userID){
        this.estimateNumber = estimateNumber;
        this.repute_Rating = repute_Rating;
        this.str_Repute = str_Repute;
        this.userID = userID;
    }

    public void setEstimateNumber(String estimateNumber) {
        this.estimateNumber = estimateNumber;
    }

    public String getEstimateNumber() {
        return estimateNumber;
    }

    public String getRepute_Rating() {
        return repute_Rating;
    }

    public String getStr_Repute() {
        return str_Repute;
    }

    public void setRepute_Rating(String repute_Rating) {
        this.repute_Rating = repute_Rating;
    }

    public void setStr_Repute(String str_Repute) {
        this.str_Repute = str_Repute;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
