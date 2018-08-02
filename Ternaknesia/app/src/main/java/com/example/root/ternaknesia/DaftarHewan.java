package com.example.root.ternaknesia;

import java.util.HashMap;
import java.util.Map;


public class DaftarHewan {
    String code;
    String HewanId;
    String HewanGender;
    String HewanBobot;
    String tanggal;
    String url;
    String status;
    String ver;
    String api;

    public Map<String, Boolean> Hewan = new HashMap<>();

    public DaftarHewan(){

    }

    public DaftarHewan(String code, String hewanId, String hewanGender, String hewanBobot, String tanggal, String url, String status) {
        super();
        this.code = code;
        this.HewanId = hewanId;
        this.HewanGender = hewanGender;
        this.HewanBobot = hewanBobot;
        this.tanggal = tanggal;
        this.url = url;
        this.status = status;
    }

    public Map<String, Object> toMap(){

        HashMap<String, Object> result = new HashMap<>();
        result.put("code", code);
        result.put("HewanId", HewanId);
        result.put("HewanGender", HewanGender);
        result.put("HewanBobot", HewanBobot);
        result.put("tanggal", tanggal);
        result.put("url", url);
        result.put("statsu", status);

        return result;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setHewanId(String hewanId) {
        HewanId = hewanId;
    }

    public void setHewanGender(String hewanGender) {
        HewanGender = hewanGender;
    }

    public void setHewanBobot(String hewanBobot) {
        HewanBobot = hewanBobot;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public String getHewanId() {
        return HewanId;
    }

    public String getHewanGender() {
        return HewanGender;
    }

    public String getHewanBobot() {
        return HewanBobot;
    }

    public String getUrl() {
        return url;
    }

    public String getStatus() {
        return status;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }
}
