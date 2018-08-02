package com.example.root.ternaknesia;

/**
 * Created by reza on 10/04/18.
 */

public class DaftarRecordPenyakit {

    String idHewan, idPenyakit, namaPenyakit, tanggalSakit, TanggalSembuh;

    public DaftarRecordPenyakit(String idHewan, String idPenyakit, String namaPenyakit, String tanggalSakit, String tanggalSembuh) {
        this.idHewan = idHewan;
        this.idPenyakit = idPenyakit;
        this.namaPenyakit = namaPenyakit;
        this.tanggalSakit = tanggalSakit;
        TanggalSembuh = tanggalSembuh;
    }

    public String getIdHewan() {
        return idHewan;
    }

    public void setIdHewan(String idHewan) {
        this.idHewan = idHewan;
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

    public String getTanggalSakit() {
        return tanggalSakit;
    }

    public void setTanggalSakit(String tanggalSakit) {
        this.tanggalSakit = tanggalSakit;
    }

    public String getTanggalSembuh() {
        return TanggalSembuh;
    }

    public void setTanggalSembuh(String tanggalSembuh) {
        TanggalSembuh = tanggalSembuh;
    }
}
