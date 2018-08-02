package com.example.root.ternaknesia;

/**
 * Created by root on 07/07/17.
 */

public class DaftarJadwal {
    String idP;
    String Uid;
    String namaP;
    String banyakP;
    String jamp;

    public DaftarJadwal(){

    }

    public DaftarJadwal(String idP, String uid, String namaP, String banyakP, String jamp) {
        this.idP = idP;
        this.Uid = uid;
        this.namaP = namaP;
        this.banyakP = banyakP;
        this.jamp = jamp;
    }

    public void setIdP(String idP) {
        this.idP = idP;
    }

    public void setNamaP(String namaP) {
        this.namaP = namaP;
    }

    public void setBanyakP(String banyakP) {
        this.banyakP = banyakP;
    }

    public void setJamp(String jamp) {
        this.jamp = jamp;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getIdP() {
        return idP;
    }

    public String getNamaP() {
        return namaP;
    }

    public String getBanyakP() {
        return banyakP;
    }

    public String getJamp() {
        return jamp;
    }

    public String getUid() {
        return Uid;
    }
}
