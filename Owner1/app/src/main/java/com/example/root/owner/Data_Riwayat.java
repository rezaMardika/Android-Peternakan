package com.example.root.owner;

/**
 * Created by root on 16/09/17.
 */

public class Data_Riwayat {

    String codeB;
    String idB;
    String genderB;
    String bobotB;
    String dateB;

    public Data_Riwayat(){

    }

    public Data_Riwayat(String codeB, String idB, String genderB, String bobotB, String dateB) {
        this.codeB = codeB;
        this.idB = idB;
        this.genderB = genderB;
        this.bobotB = bobotB;
        this.dateB = dateB;
    }

    public String getCodeB() {
        return codeB;
    }

    public void setCodeB(String codeB) {
        this.codeB = codeB;
    }

    public String getIdB() {
        return idB;
    }

    public void setIdB(String idB) {
        this.idB = idB;
    }

    public String getGenderB() {
        return genderB;
    }

    public void setGenderB(String genderB) {
        this.genderB = genderB;
    }

    public String getBobotB() {
        return bobotB;
    }

    public void setBobotB(String bobotB) {
        this.bobotB = bobotB;
    }

    public String getDateB() {
        return dateB;
    }

    public void setDateB(String dateB) {
        this.dateB = dateB;
    }
}
