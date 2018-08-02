package com.example.root.ternaknesia;

/**
 * Created by reza on 15/10/17.
 */

public class DaftarSakit {

    String idPenyakit;
    String idK;
    String codeH;
    String namaPenyakit;
    String namaH;
    String bobotP;
    String dateP;

    public DaftarSakit(){

    }

    public DaftarSakit(String idPenyakit, String idK, String codeH, String namaPenyakit, String namaH, String bobotP, String dateP) {
        this.idPenyakit = idPenyakit;
        this.idK = idK;
        this.codeH = codeH;
        this.namaPenyakit = namaPenyakit;
        this.namaH = namaH;
        this.bobotP = bobotP;
        this.dateP = dateP;
    }

    public String getIdPenyakit() {
        return idPenyakit;
    }

    public void setIdPenyakit(String idPenyakit) {
        this.idPenyakit = idPenyakit;
    }

    public String getIdK() {
        return idK;
    }

    public void setIdK(String idK) {
        this.idK = idK;
    }

    public String getCodeH() {
        return codeH;
    }

    public void setCodeH(String codeH) {
        this.codeH = codeH;
    }

    public String getNamaPenyakit() {
        return namaPenyakit;
    }

    public void setNamaPenyakit(String namaPenyakit) {
        this.namaPenyakit = namaPenyakit;
    }

    public String getNamaH() {
        return namaH;
    }

    public void setNamaH(String namaH) {
        this.namaH = namaH;
    }

    public String getBobotP() {
        return bobotP;
    }

    public void setBobotP(String bobotP) {
        this.bobotP = bobotP;
    }

    public String getDateP() {
        return dateP;
    }

    public void setDateP(String dateP) {
        this.dateP = dateP;
    }
}
