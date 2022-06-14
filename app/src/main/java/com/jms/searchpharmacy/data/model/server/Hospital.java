package com.jms.searchpharmacy.data.model.server;

import com.google.gson.annotations.SerializedName;

public class Hospital {
    @SerializedName("loadaddress")
    private String loadaddress;
    @SerializedName("name")
    private String name;
    @SerializedName("type")
    private String type;
    @SerializedName("address")
    private String address;
    @SerializedName("startdate")
    private String startdate;
    @SerializedName("totaldoctor")
    private Integer totaldoctor;

    public Hospital(String loadaddress, String name, String type, String address, String startdate, Integer totaldoctor) {
        this.loadaddress = loadaddress;
        this.name = name;
        this.type = type;
        this.address = address;
        this.startdate = startdate;
        this.totaldoctor = totaldoctor;
    }

    public String getLoadAddress() {
        return loadaddress;
    }

    public void setLoadAddress(String loadaddress) {
        this.loadaddress = loadaddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStartDate() {
        return startdate;
    }

    public void setStartDate(String startdate) {
        this.startdate = startdate;
    }

    public Integer getTotalDoctor() {
        return totaldoctor;
    }

    public void setTotalDoctor(Integer totaldoctor) {
        this.totaldoctor = totaldoctor;
    }
}
