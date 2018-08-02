package com.example.root.ternaknesia;

/**
 * Created by root on 17/07/17.
 */

public class DaftarBobot {

    String codeB;
    String idB;
    String Uid;
    String genderB;
    String bobotB;
    String dateB;

    public DaftarBobot(){

    }

    public DaftarBobot(String codeB, String idB, String uid, String genderB, String bobotB, String dateB) {
        this.codeB = codeB;
        this.idB = idB;
        this.Uid = uid;
        this.genderB = genderB;
        this.bobotB = bobotB;
        this.dateB = dateB;
    }

    public void setCodeB(String codeB) {
        this.codeB = codeB;
    }

    public void setIdB(String idB) {
        this.idB = idB;
    }

    public void setGenderB(String genderB) {
        this.genderB = genderB;
    }

    public void setBobotB(String bobotB) {
        this.bobotB = bobotB;
    }

    public void setDateB(String dateB) {
        this.dateB = dateB;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getCodeB() {
        return codeB;
    }

    public String getIdB() {
        return idB;
    }

    public String getGenderB() {
        return genderB;
    }

    public String getBobotB() {
        return bobotB;
    }

    public String getDateB() {
        return dateB;
    }

    public String getUid() {
        return Uid;
    }
}
