package com.jms.searchpharmacy.data.model.server;

import com.google.gson.annotations.SerializedName;

public class Pharmacy {
    @SerializedName("loadaddress")
    private String loadaddress;
    @SerializedName("name")
    private String name;
    @SerializedName("address")
    private String address;
    @SerializedName("startdate")
    private String startdate;

    public Pharmacy(String loadaddress, String name, String address, String startdate) {
        this.loadaddress = loadaddress;
        this.name = name;
        this.address = address;
        this.startdate = startdate;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStartDate() {
        return startdate;
    }

    public void setStartDate(String startDate) {
        this.startdate = startdate;
    }
}
