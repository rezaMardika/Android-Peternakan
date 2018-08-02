package com.example.root.owner;

/**
 * Created by root on 21/09/17.
 */

public class Data_Potong {

    String IdH;
    String CodeH;
    String BobotH;
    String GenderH;
    String TanggalH;
    String KandangH;
    String urlH;

    public Data_Potong(){

    }

    public Data_Potong(String idH, String codeH, String bobotH, String genderH, String tanggalH, String kandangH, String urlH) {
        IdH = idH;
        CodeH = codeH;
        BobotH = bobotH;
        GenderH = genderH;
        TanggalH = tanggalH;
        KandangH = kandangH;
        this.urlH = urlH;
    }

    public String getIdH() {
        return IdH;
    }

    public void setIdH(String idH) {
        IdH = idH;
    }

    public String getCodeH() {
        return CodeH;
    }

    public void setCodeH(String codeH) {
        CodeH = codeH;
    }

    public String getBobotH() {
        return BobotH;
    }

    public void setBobotH(String bobotH) {
        BobotH = bobotH;
    }

    public String getGenderH() {
        return GenderH;
    }

    public void setGenderH(String genderH) {
        GenderH = genderH;
    }

    public String getTanggalH() {
        return TanggalH;
    }

    public void setTanggalH(String tanggalH) {
        TanggalH = tanggalH;
    }

    public String getKandangH() {
        return KandangH;
    }

    public void setKandangH(String kandangH) {
        KandangH = kandangH;
    }

    public String getUrlH() {
        return urlH;
    }

    public void setUrlH(String urlH) {
        this.urlH = urlH;
    }
}
