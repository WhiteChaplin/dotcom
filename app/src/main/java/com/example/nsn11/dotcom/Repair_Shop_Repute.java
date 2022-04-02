package com.example.nsn11.dotcom;

public class Repair_Shop_Repute {
    String str_Repute;
    String repute_Rating;
    String estimateNumber;
    String uID;
    Repair_Shop_Repute(){

    }

    Repair_Shop_Repute(String str_Repute,String repute_Rating,String estimateNumber,String uID){
        this.str_Repute = str_Repute;
        this.repute_Rating = repute_Rating;
        this.estimateNumber = estimateNumber;
        this.uID = uID;
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

    public void setEstimateNumber(String estimateNumber) {
        this.estimateNumber = estimateNumber;
    }

    public String getEstimateNumber() {
        return estimateNumber;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }
}
