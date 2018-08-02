package com.example.root.owner;

/**
 * Created by root on 14/09/17.
 */

public class Data_Kandang {

    String KandangId;
    String NamaKandang;
    String KapasitasKandang;
    String Emailus;

    public Data_Kandang(){

    }

    public Data_Kandang(String kandangId, String namaKandang, String kapasitasKandang, String emailus) {
        this.KandangId = kandangId;
        this.NamaKandang = namaKandang;
        this.KapasitasKandang = kapasitasKandang;
        this.Emailus = emailus;
    }

    public String getKandangId() {
        return KandangId;
    }

    public void setKandangId(String kandangId) {
        KandangId = kandangId;
    }

    public String getNamaKandang() {
        return NamaKandang;
    }

    public void setNamaKandang(String namaKandang) {
        NamaKandang = namaKandang;
    }

    public String getKapasitasKandang() {
        return KapasitasKandang;
    }

    public void setKapasitasKandang(String kapasitasKandang) {
        KapasitasKandang = kapasitasKandang;
    }

    public String getEmailus() {
        return Emailus;
    }

    public void setEmailus(String emailus) {
        Emailus = emailus;
    }
}
