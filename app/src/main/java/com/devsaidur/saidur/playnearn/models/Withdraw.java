package com.devsaidur.saidur.playnearn.models;

public class Withdraw {
    String userMobile;
    String userSec;
    String userId;
    String userName;
    String userEmail;
    String WithdrawAmount;
    String WithdrawType;
    String WithdrawNumber;
    String WithdrawStatus;
    String WithdrawDate;
    String WithdrawApproveDate;

    public Withdraw() {
    }

    public Withdraw(String userMobile, String userSec, String userId, String userName, String userEmail, String withdrawAmount, String withdrawType, String withdrawNumber, String withdrawStatus,String withdrawDate) {
        this.userMobile = userMobile;
        this.userSec = userSec;
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        WithdrawAmount = withdrawAmount;
        WithdrawType = withdrawType;
        WithdrawNumber = withdrawNumber;
        WithdrawStatus = withdrawStatus;
        WithdrawDate =   withdrawDate;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserSec() {
        return userSec;
    }

    public void setUserSec(String userSec) {
        this.userSec = userSec;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getWithdrawAmount() {
        return WithdrawAmount;
    }

    public void setWithdrawAmount(String withdrawAmount) {
        WithdrawAmount = withdrawAmount;
    }

    public String getWithdrawType() {
        return WithdrawType;
    }

    public void setWithdrawType(String withdrawType) {
        WithdrawType = withdrawType;
    }

    public String getWithdrawNumber() {
        return WithdrawNumber;
    }

    public void setWithdrawNumber(String withdrawNumber) {
        WithdrawNumber = withdrawNumber;
    }

    public String getWithdrawStatus() {
        return WithdrawStatus;
    }

    public void setWithdrawStatus(String withdrawStatus) {
        WithdrawStatus = withdrawStatus;
    }

    public String getWithdrawDate() {
        return WithdrawDate;
    }

    public void setWithdrawDate(String withdrawDate) {
        WithdrawDate = withdrawDate;
    }
}
