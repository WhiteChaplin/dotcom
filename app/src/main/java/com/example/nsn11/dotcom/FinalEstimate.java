package com.example.nsn11.dotcom;

public class FinalEstimate {
    public String forecastWrong;
    public String pay;
    public String repairDetail;
    public String repairTime;
    public String userID;
    public String estimateNumber;
    public String repairShopName;
    public String repairWay;
    public String title;

    public FinalEstimate()
    {

    }

    public FinalEstimate(String forecastWrong,String pay, String repairDetail,String repairTime,String userID,String estimateNumber,String repairShopName,String repairWay,String title)
    {
        this.forecastWrong = forecastWrong;
        this.pay = pay;
        this.repairDetail = repairDetail;
        this.repairTime = repairTime;
        this.userID = userID;
        this.estimateNumber = estimateNumber;
        this.repairShopName = repairShopName;
        this.repairWay = repairWay;
        this.title = title;
    }

    public String getForecastWrong() {
        return forecastWrong;
    }

    public void setForecastWrong(String forecastWrong) {
        this.forecastWrong = forecastWrong;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getRepairDetail() {
        return repairDetail;
    }

    public void setRepairDetail(String repairDetail) {
        this.repairDetail = repairDetail;
    }

    public String getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(String repairTime) {
        this.repairTime = repairTime;
    }


    public void setEstimateNumber(String estimateNumber) {
        this.estimateNumber = estimateNumber;
    }

    public String getEstimateNumber() {
        return estimateNumber;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getRepairShopName() {
        return repairShopName;
    }

    public void setRepairShopName(String repairShopName) {
        this.repairShopName = repairShopName;
    }

    public String getRepairWay() {
        return repairWay;
    }

    public void setRepairWay(String repairWay) {
        this.repairWay = repairWay;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
