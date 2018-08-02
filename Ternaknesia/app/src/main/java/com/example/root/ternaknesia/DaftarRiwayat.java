package com.example.root.ternaknesia;

/**
 * Created by root on 09/07/17.
 */

public class DaftarRiwayat {

    String IdH;
    String CodeH;
    String Uid;
    String BobotH;
    String GenderH;
    String TanggalH;
    String KandangH;
    String urlH;
    String periodeH;

    public DaftarRiwayat(){

    }

    public DaftarRiwayat(String idH, String codeH, String uid, String bobotH, String genderH, String tanggalH, String kandangH, String urlH, String periodeH) {
        this.IdH = idH;
        this.CodeH = codeH;
        this.Uid = uid;
        this.BobotH = bobotH;
        this.GenderH = genderH;
        this.TanggalH = tanggalH;
        this.KandangH = kandangH;
        this.urlH = urlH;
        this.periodeH = periodeH;
    }

    public void setIdH(String idH) {
        IdH = idH;
    }

    public void setCodeH(String codeH) {
        CodeH = codeH;
    }

    public void setBobotH(String bobotH) {
        BobotH = bobotH;
    }

    public void setGenderH(String genderH) {
        GenderH = genderH;
    }

    public void setTanggalH(String tanggalH) {
        TanggalH = tanggalH;
    }

    public void setKandangH(String kandangH) {
        KandangH = kandangH;
    }

    public void setUrlH(String urlH) {
        this.urlH = urlH;
    }

    public void setPeriodeH(String periodeH) {
        this.periodeH = periodeH;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getIdH() {
        return IdH;
    }

    public String getCodeH() {
        return CodeH;
    }

    public String getBobotH() {
        return BobotH;
    }

    public String getGenderH() {
        return GenderH;
    }

    public String getTanggalH() {
        return TanggalH;
    }

    public String getKandangH() {
        return KandangH;
    }

    public String getUrlH() {
        return urlH;
    }

    public String getPeriodeH() {
        return periodeH;
    }

    public String getUid() {
        return Uid;
    }
}
