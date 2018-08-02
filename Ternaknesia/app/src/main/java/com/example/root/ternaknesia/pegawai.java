package com.example.root.ternaknesia;

/**
 * Created by root on 12/05/17.
 */

public class pegawai {
    String userId;
    String userName;
    String userAddress;
    String userEmail;
    String userPassword;

    public pegawai(){

    }

    public pegawai(String userId, String userName, String userAddress, String userEmail, String userPassword) {
        this.userId = userId;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
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

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
