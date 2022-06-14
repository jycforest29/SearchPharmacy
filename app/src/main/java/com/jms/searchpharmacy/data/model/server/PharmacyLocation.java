package com.jms.searchpharmacy.data.model.server;

import com.google.gson.annotations.SerializedName;

public class PharmacyLocation {
    @SerializedName("index")
    private int index;
    @SerializedName("loadaddress")
    private String loadaddress;
    @SerializedName("hospitalcount")
    private int hospitalcount;
    @SerializedName("pharmacycount")
    private int pharmacycount;
    @SerializedName("startdate")
    private String startdate;
    @SerializedName("dong")
    private String dong;
    @SerializedName("hospitalperpharmacy")
    private float hospitalperpharmacy;
    @SerializedName("doctorperpharmacy")
    private float doctorperpharmacy;
    @SerializedName("viewcount")
    private int viewcount;

    public PharmacyLocation(int index, String loadaddress, int hospitalcount, int pharmacycount, String startdate, String dong, float hospitalperpharmacy, float doctorperpharmacy, int viewcount) {
        this.index = index;
        this.loadaddress = loadaddress;
        this.hospitalcount = hospitalcount;
        this.pharmacycount = pharmacycount;
        this.startdate = startdate;
        this.dong = dong;
        this.hospitalperpharmacy = hospitalperpharmacy;
        this.doctorperpharmacy = doctorperpharmacy;
        this.viewcount = viewcount;
    }


}
