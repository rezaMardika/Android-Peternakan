package com.example.root.owner;

/**
 * Created by root on 16/09/17.
 */

public class Data_Jadwal {

    String idP;
    String namaP;
    String banyakP;
    String jamp;

    public Data_Jadwal(){

    }

    public Data_Jadwal(String idP, String namaP, String banyakP, String jamp) {
        this.idP = idP;
        this.namaP = namaP;
        this.banyakP = banyakP;
        this.jamp = jamp;
    }

    public String getIdP() {
        return idP;
    }

    public void setIdP(String idP) {
        this.idP = idP;
    }

    public String getNamaP() {
        return namaP;
    }

    public void setNamaP(String namaP) {
        this.namaP = namaP;
    }

    public String getBanyakP() {
        return banyakP;
    }

    public void setBanyakP(String banyakP) {
        this.banyakP = banyakP;
    }

    public String getJamp() {
        return jamp;
    }

    public void setJamp(String jamp) {
        this.jamp = jamp;
    }
}
