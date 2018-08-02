package com.example.root.owner;

/**
 * Created by root on 15/09/17.
 */

public class Data_Pakan {

    String idPakan;
    String namaPakan;
    String kapasitasPakan;
    String datePakan;

    public Data_Pakan(){

    }

    public Data_Pakan(String idPakan, String namaPakan, String kapasitasPakan, String datePakan) {
        this.idPakan = idPakan;
        this.namaPakan = namaPakan;
        this.kapasitasPakan = kapasitasPakan;
        this.datePakan = datePakan;
    }

    public String getIdPakan() {
        return idPakan;
    }

    public void setIdPakan(String idPakan) {
        this.idPakan = idPakan;
    }

    public String getNamaPakan() {
        return namaPakan;
    }

    public void setNamaPakan(String namaPakan) {
        this.namaPakan = namaPakan;
    }

    public String getKapasitasPakan() {
        return kapasitasPakan;
    }

    public void setKapasitasPakan(String kapasitasPakan) {
        this.kapasitasPakan = kapasitasPakan;
    }

    public String getDatePakan() {
        return datePakan;
    }

    public void setDatePakan(String datePakan) {
        this.datePakan = datePakan;
    }
}
