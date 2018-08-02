package com.example.root.ternaknesia;

/**
 * Created by root on 13/07/17.
 */

public class DaftarPenyakit {

    String IdP;
    String CodeH;
    String IdK;
    String NamaH;
    String NamaP;
    String BobotP;
    String DateP;

    public DaftarPenyakit(){

    }

    public DaftarPenyakit(String idP, String codeH, String IdK, String NamaH, String namaP, String bobotP, String dateP) {
        this.IdP = idP;
        this.CodeH = codeH;
        this.IdK = IdK;
        this.NamaH = NamaH;
        this.NamaP = namaP;
        this.BobotP = bobotP;
        this.DateP = dateP;
    }

    public void setIdP(String idP) {
        IdP = idP;
    }

    public void setCodeH(String codeH) {
        CodeH = codeH;
    }

    public void setIdK(String idK) {
        IdK = idK;
    }

    public void setNamaP(String namaP) {
        NamaP = namaP;
    }

    public void setBobotP(String bobotP) {
        BobotP = bobotP;
    }

    public void setDateP(String dateP) {
        DateP = dateP;
    }

    public String getIdP() {
        return IdP;
    }

    public String getCodeH() {
        return CodeH;
    }

    public String getIdK() {
        return IdK;
    }

    public String getNamaP() {
        return NamaP;
    }

    public String getBobotP() {
        return BobotP;
    }

    public String getDateP() {
        return DateP;
    }

    public String getNamaH() {
        return NamaH;
    }

    public void setNamaH(String namaH) {
        NamaH = namaH;
    }
}
