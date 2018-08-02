package com.example.root.ternaknesia;

/**
 * Created by root on 21/08/17.
 */

public class DaftarPakan {

    String idPakan;
    String namaPakan;
    String kapasitasPakan;
    String datePakan;

    public DaftarPakan(){

    }

    public DaftarPakan(String idPakan, String namaPakan, String kapasitasPakan, String datePakan) {
        this.idPakan = idPakan;
        this.namaPakan = namaPakan;
        this.kapasitasPakan = kapasitasPakan;
        this.datePakan = datePakan;
    }

    public void setIdPakan(String idPakan) {
        this.idPakan = idPakan;
    }

    public void setNamaPakan(String namaPakan) {
        this.namaPakan = namaPakan;
    }

    public void setKapasitasPakan(String kapasitasPakan) {
        this.kapasitasPakan = kapasitasPakan;
    }

    public void setDatePakan(String datePakan) {
        this.datePakan = datePakan;
    }

    public String getIdPakan() {
        return idPakan;
    }

    public String getNamaPakan() {
        return namaPakan;
    }

    public String getKapasitasPakan() {
        return kapasitasPakan;
    }

    public String getDatePakan() {
        return datePakan;
    }
}
