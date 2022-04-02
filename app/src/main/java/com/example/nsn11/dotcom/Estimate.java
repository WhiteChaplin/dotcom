package com.example.nsn11.dotcom;

import java.util.Date;

public class Estimate {
    public String userUID;
    public String pictureFilePath;
    public String explainEditText;
    public String editTitle;
    public String separateText;
    public String selfRepairText;
    public String locationText;
    public String moreOption;
    public String nowState="수리점 확인중";
    public String date;

    public Estimate()
    {

    }

    public Estimate(String userUID,String pictureFilePath,String explainEditText,String editTitle, String separateText,String selfRepairText,String locationText, String moreOption,String date)
    {
        this.userUID = userUID;
        this.pictureFilePath = pictureFilePath;
        this.explainEditText = explainEditText;
        this.editTitle = editTitle;
        this.separateText = separateText;
        this.selfRepairText = selfRepairText;
        this.locationText = locationText;
        this.moreOption = moreOption;
        nowState ="수리점 확인중";
        this.date = date;
    }

    public void setExplainEditText(String explainEditText) {
        this.explainEditText = explainEditText;
    }

    public void setLocationText(String locationText) {
        this.locationText = locationText;
    }

    public void setMoreOption(String moreOption) {
        this.moreOption = moreOption;
    }

    public void setPictureFilePath(String pictureFilePath) {
        this.pictureFilePath = pictureFilePath;
    }

    public void setSelfRepairText(String selfRepairText) {
        this.selfRepairText = selfRepairText;
    }

    public void setSeparateText(String separateText) {
        this.separateText = separateText;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getExplainEditText() {
        return explainEditText;
    }

    public String getLocationText() {
        return locationText;
    }

    public String getMoreOption() {
        return moreOption;
    }

    public String getPictureFilePath() {
        return pictureFilePath;
    }

    public String getSelfRepairText() {
        return selfRepairText;
    }

    public String getSeparateText() {
        return separateText;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setNowState(String nowState) {
        this.nowState = nowState;
    }

    public String getNowState() {
        return nowState;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEditTitle() {
        return editTitle;
    }

    public void setEditTitle(String editTitle) {
        this.editTitle = editTitle;
    }
}
