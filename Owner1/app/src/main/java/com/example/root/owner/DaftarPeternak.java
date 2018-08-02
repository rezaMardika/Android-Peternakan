package com.example.root.owner;

/**
 * Created by root on 12/09/17.
 */

public class DaftarPeternak {

    String userId;
    String userName;
    String userAddress;
    String userEmail;
    String userPassword;
    String userFoto;
    String userJob;

    public DaftarPeternak(){

    }

    public DaftarPeternak(String userId, String userName, String userAddress, String userEmail, String userPassword, String userFoto, String userJob) {
        this.userId = userId;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userFoto = userFoto;
        this.userJob = userJob;
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

    public String getUserFoto() {
        return userFoto;
    }

    public void setUserFoto(String userFoto) {
        this.userFoto = userFoto;
    }

    public String getUserJob() {
        return userJob;
    }

    public void setUserJob(String userJob) {
        this.userJob = userJob;
    }
}
