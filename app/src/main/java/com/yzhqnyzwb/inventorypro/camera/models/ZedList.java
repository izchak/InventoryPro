package com.yzhqnyzwb.inventorypro.camera.models;

public class ZedList {
    private String uid;
    private   String dateOfZed;
    private String resultForZed;


    public ZedList(String uid, String dateOfZed, String resultForZed) {
        this.uid = uid;
        this.dateOfZed = dateOfZed;
        this.resultForZed = resultForZed;
    }
    public ZedList() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDateOfZed() {
        return dateOfZed;
    }

    public void setDateOfZed(String dateOfZed) {
        this.dateOfZed = dateOfZed;
    }

    public String getResultForZed() {
        return resultForZed;
    }

    public void setResultForZed(String resultForZed) {
        this.resultForZed = resultForZed;
    }

    @Override
    public String toString() {
        return "ZedList{" +
                "uid='" + uid + '\'' +
                ", dateOfZed='" + dateOfZed + '\'' +
                ", resultForZed='" + resultForZed + '\'' +
                '}';
    }
}
