package com.example.root.ternaknesia;

import android.widget.ImageView;

/**
 * Created by root on 03/09/17.
 */

public class ImageUpload {
    public String idI;
    public String nama;
    public String url;

    public ImageUpload(){

    }

    public ImageUpload(String idI, String nama, String url) {
        this.idI = idI;
        this.nama = nama;
        this.url = url;
    }

    public void setIdI(String idI) {
        this.idI = idI;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIdI() {
        return idI;
    }

    public String getNama() {
        return nama;
    }

    public String getUrl() {
        return url;
    }
}
