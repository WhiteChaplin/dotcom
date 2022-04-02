package com.example.nsn11.dotcom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserInfo {
    public String userEmail;
    public String userPhone;
    public String userName;
    public String userNicName;
    private DatabaseReference mDatabase;
    //public String userEmail;
    //public String userBrith;

    public UserInfo()
    {

    }

    public UserInfo(String userEmail,String userPhone,String userName,String userNicName)
    {
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userName = userName;
        this.userNicName = userNicName;
    }


    public String getUserEmail() {
        return userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public void setUserNicName(String userNicName){
        this.userNicName = userNicName;
    }

    public String getUserNicName()
    {
        return userNicName;
    }
}
