package com.example.root.ternaknesia;

/**
 * Created by reza on 11/04/18.
 */

public class DaftarRHewan {

    String idHewan, namaPenyakit, tanggalSakit, tangggalSembuh;

    public DaftarRHewan(String idHewan, String namaPenyakit, String tanggalSakit, String tangggalSembuh) {
        this.idHewan = idHewan;
        this.namaPenyakit = namaPenyakit;
        this.tanggalSakit = tanggalSakit;
        this.tangggalSembuh = tangggalSembuh;
    }

    public String getIdHewan() {
        return idHewan;
    }

    public void setIdHewan(String idHewan) {
        this.idHewan = idHewan;
    }

    public String getNamaPenyakit() {
        return namaPenyakit;
    }

    public void setNamaPenyakit(String namaPenyakit) {
        this.namaPenyakit = namaPenyakit;
    }

    public String getTanggalSakit() {
        return tanggalSakit;
    }

    public void setTanggalSakit(String tanggalSakit) {
        this.tanggalSakit = tanggalSakit;
    }

    public String getTangggalSembuh() {
        return tangggalSembuh;
    }

    public void setTangggalSembuh(String tangggalSembuh) {
        this.tangggalSembuh = tangggalSembuh;
    }
}
