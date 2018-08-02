package com.example.root.ternaknesia;

/**
 * Created by reza on 02/10/17.
 */

public class DaftarPeternak {

    String userId;
    String userName;
    String userAdddress;
    String userEmail;
    String userPassword;
    String userFoto;

    public DaftarPeternak(){

    }

    public DaftarPeternak(String userId, String userName, String userAdddress, String userEmail, String userPassword, String userFoto) {
        this.userId = userId;
        this.userName = userName;
        this.userAdddress = userAdddress;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userFoto = userFoto;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserAdddress(String userAdddress) {
        this.userAdddress = userAdddress;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserFoto(String userFoto) {
        this.userFoto = userFoto;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserAdddress() {
        return userAdddress;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserFoto() {
        return userFoto;
    }
}
