package com.example.root.ternaknesia;

/**
 * Created by root on 14/05/17.
 */

public class DaftarKandang {
    String KandangId;
    String NamaKandang;
    String Emailus;

    public DaftarKandang(){

    }

    public DaftarKandang(String kandangId, String namaKandang, String emailus) {
        this.KandangId = kandangId;
        this.NamaKandang = namaKandang;
        this.Emailus = emailus;
    }


    public void setKandangId(String kandangId) {
        KandangId = kandangId;
    }

    public void setNamaKandang(String namaKandang) {
        NamaKandang = namaKandang;
    }

    public void setEmailus(String emailus) {
        Emailus = emailus;
    }

    public String getKandangId() {
        return KandangId;
    }

    public String getNamaKandang() {
        return NamaKandang;
    }

    public String getEmailus() {
        return Emailus;
    }
}
