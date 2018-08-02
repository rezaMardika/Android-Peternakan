package com.example.root.owner;

/**
 * Created by root on 14/09/17.
 */

public class Data_Hewan {

    String code;
    String HewanId;
    String HewanGender;
    String HewanBobot;
    String nama;
    String url;
    String status;

    public Data_Hewan(){

    }

    public Data_Hewan(String code, String hewanId, String hewanGender, String hewanBobot, String nama, String url, String status) {
        this.code = code;
        this.HewanId = hewanId;
        this.HewanGender = hewanGender;
        this.HewanBobot = hewanBobot;
        this.nama = nama;
        this.url = url;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHewanId() {
        return HewanId;
    }

    public void setHewanId(String hewanId) {
        HewanId = hewanId;
    }

    public String getHewanGender() {
        return HewanGender;
    }

    public void setHewanGender(String hewanGender) {
        HewanGender = hewanGender;
    }

    public String getHewanBobot() {
        return HewanBobot;
    }

    public void setHewanBobot(String hewanBobot) {
        HewanBobot = hewanBobot;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
