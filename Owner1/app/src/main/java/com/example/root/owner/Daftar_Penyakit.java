package com.example.root.owner;

/**
 * Created by reza on 21/12/17.
 */

public class Daftar_Penyakit {

    String idPenyakit, namaPenyakit;

    public Daftar_Penyakit(){

    }

    public Daftar_Penyakit(String idPenyakit, String namaPenyakit) {
        this.idPenyakit = idPenyakit;
        this.namaPenyakit = namaPenyakit;
    }

    public String getIdPenyakit() {
        return idPenyakit;
    }

    public void setIdPenyakit(String idPenyakit) {
        this.idPenyakit = idPenyakit;
    }

    public String getNamaPenyakit() {
        return namaPenyakit;
    }

    public void setNamaPenyakit(String namaPenyakit) {
        this.namaPenyakit = namaPenyakit;
    }
}
