package com.example.root.owner;

/**
 * Created by reza on 01/10/17.
 */

public class Data_Penyakit {

    String idPenyakit;
    String idK;
    String codeH;
    String namaPenyakit;
    String namaH;
    String bobotP;
    String dateP;

    public Data_Penyakit(){

    }

    public Data_Penyakit(String idPenyakit, String idK, String codeH, String namaPenyakit, String namaH, String bobotP, String dateP) {
        this.idPenyakit = idPenyakit;
        this.idK = idK;
        this.codeH = codeH;
        this.namaPenyakit = namaPenyakit;
        this.namaH = namaH;
        this.bobotP = bobotP;
        this.dateP = dateP;
    }

    public void setIdP(String idPenyakit) {
        this.idPenyakit = idPenyakit;
    }

    public void setIdK(String idK) {
        this.idK = idK;
    }

    public void setCodeH(String codeH) {
        this.codeH = codeH;
    }

    public void setNamaP(String namaPenyakit) {
        this.namaPenyakit = namaPenyakit;
    }

    public void setNamaH(String namaH) {
        this.namaH = namaH;
    }

    public void setBobotP(String bobotP) {
        this.bobotP = bobotP;
    }

    public void setDateP(String dateP) {
        this.dateP = dateP;
    }

    public String getIdP() {
        return idPenyakit;
    }

    public String getIdK() {
        return idK;
    }

    public String getCodeH() {
        return codeH;
    }

    public String getNamaP() {
        return namaPenyakit;
    }

    public String getNamaH() {
        return namaH;
    }

    public String getBobotP() {
        return bobotP;
    }

    public String getDateP() {
        return dateP;
    }
}
