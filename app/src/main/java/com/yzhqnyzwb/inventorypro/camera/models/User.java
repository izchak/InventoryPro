package com.yzhqnyzwb.inventorypro.camera.models;

public class User {
    private String uid;
   private String nameUser;
    private  String phoneUser;
    private  String emailUser;
    private String paswordUser;

    public User(String uid, String nameUser, String phoneUser, String emailUser, String paswordUser) {
        this.uid = uid;
        this.nameUser = nameUser;
        this.phoneUser = phoneUser;
        this.emailUser = emailUser;
        this.paswordUser = paswordUser;
    }

    public String getUid() {
        return uid;
    }

    public String getNameUser() {
        return nameUser;
    }

    public String getPhoneUser() {
        return phoneUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public String getPaswordUser() {
        return paswordUser;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public void setPhoneUser(String phoneUser) {
        this.phoneUser = phoneUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public void setPaswordUser(String paswordUser) {
        this.paswordUser = paswordUser;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", nameUser='" + nameUser + '\'' +
                ", phoneUser='" + phoneUser + '\'' +
                ", emailUser='" + emailUser + '\'' +
                ", paswordUser='" + paswordUser + '\'' +
                '}';
    }
}
