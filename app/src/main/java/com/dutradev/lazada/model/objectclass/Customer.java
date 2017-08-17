package com.dutradev.lazada.model.objectclass;

/**
 * Created by dutradev on 16/08/2017.
 */

public class Customer {
    public int getmNameId() {
        return mNameId;
    }

    public void setmNameId(int mNameId) {
        this.mNameId = mNameId;
    }

    public int getmCustomerType() {
        return mCustomerType;
    }

    public void setmCustomerType(int mCustomerType) {
        this.mCustomerType = mCustomerType;
    }

    public int getmGender() {
        return mGender;
    }

    public void setmGender(int mGender) {
        this.mGender = mGender;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmBirthDay() {
        return mBirthDay;
    }

    public void setmBirthDay(String mBirthDay) {
        this.mBirthDay = mBirthDay;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getmReciveNotification() {
        return mReciveNotification;
    }

    public void setmReciveNotification(String mReciveNotification) {
        this.mReciveNotification = mReciveNotification;
    }

    int mNameId, mCustomerType, mGender;
    String mUserName, mPassword, mName, mAddress, mBirthDay, mPhone, mReciveNotification;
}
