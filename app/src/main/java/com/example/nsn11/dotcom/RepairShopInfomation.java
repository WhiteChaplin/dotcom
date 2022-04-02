package com.example.nsn11.dotcom;

public class RepairShopInfomation {
    public String repairShopExplain;
    public String repairShopService;
    public String repairShopWorkTimeWeekday;
    public String repairShopWorkTimeSaturday;
    public String repairShopWorkTimeSunday;
    public String shopTel;
    public String shopKakao;

    public RepairShopInfomation(){

    }

    public RepairShopInfomation(String repairShopExplain, String repairShopService, String repairShopWorkTimeWeekday, String repairShopWorkTimeSaturday, String repairShopWorkTimeSunday,String shopTel,String shopKakao){
        this.repairShopExplain = repairShopExplain;
        this.repairShopService = repairShopService;
        this.repairShopWorkTimeWeekday = repairShopWorkTimeWeekday;
        this.repairShopWorkTimeSaturday = repairShopWorkTimeSaturday;
        this.repairShopWorkTimeSunday = repairShopWorkTimeSunday;
        this.shopTel = shopTel;
        this.shopKakao = shopKakao;
    }

    public String getRepairShopExplain() {
        return repairShopExplain;
    }

    public void setRepairShopExplain(String repairShopExplain) {
        this.repairShopExplain = repairShopExplain;
    }

    public String getRepairShopService() {
        return repairShopService;
    }

    public void setRepairShopService(String repairShopService) {
        this.repairShopService = repairShopService;
    }

    public String getRepairShopWorkTimeWeekday() {
        return repairShopWorkTimeWeekday;
    }

    public void setRepairShopWorkTimeWeekday(String repairShopWorkTimeWeekday) {
        this.repairShopWorkTimeWeekday = repairShopWorkTimeWeekday;
    }

    public String getRepairShopWorkTimeSaturday() {
        return repairShopWorkTimeSaturday;
    }

    public void setRepairShopWorkTimeSaturday(String repairShopWorkTimeSaturday) {
        this.repairShopWorkTimeSaturday = repairShopWorkTimeSaturday;
    }

    public String getRepairShopWorkTimeSunday() {
        return repairShopWorkTimeSunday;
    }

    public void setRepairShopWorkTimeSunday(String repairShopWorkTimeSunday) {
        this.repairShopWorkTimeSunday = repairShopWorkTimeSunday;
    }

    public void setShopTel(String shopTel) {
        this.shopTel = shopTel;
    }

    public String getShopTel() {
        return shopTel;
    }

    public void setShopKakao(String shopKakao) {
        this.shopKakao = shopKakao;
    }

    public String getShopKakao() {
        return shopKakao;
    }
}
