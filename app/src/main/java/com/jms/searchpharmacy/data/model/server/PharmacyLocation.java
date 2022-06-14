package com.jms.searchpharmacy.data.model.server;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class PharmacyLocation {
    @SerializedName("index")
    private int index;
    //    load_address -> loadaddress
    @SerializedName("loadaddress")
    private String load_address;
    @SerializedName("hospitalcount")
    private int hospital_count;
    @SerializedName("pharmacycount")
    private int pharmacy_count;
    @SerializedName("conveniencecount")
    private int convenience_count;
    //    추가
    @SerializedName("doctorcount")
    private int doctorcount;
    @SerializedName("dong")
    private String dong;
    @SerializedName("hospitalperpharmacy")
    private float hospital_per_pharmacy;
    @SerializedName("doctorperpharmacy")
    private float doctor_per_pharmacy;
    @SerializedName("convenienceperpharmacy")
    private float convenience_per_pharmacy;
    @SerializedName("viewcount")
    private int viewcount;

    public PharmacyLocation(int index, String load_address, int hospital_count, int pharmacy_count, int convenience_count, int doctorcount, String dong, float hospital_per_pharmacy, float doctor_per_pharmacy, float convenience_per_pharmacy, int viewcount) {
        this.index = index;
        this.load_address = load_address;
        this.hospital_count = hospital_count;
        this.pharmacy_count = pharmacy_count;
        this.convenience_count = convenience_count;
        this.doctorcount = doctorcount;
        this.dong = dong;
        this.hospital_per_pharmacy = hospital_per_pharmacy;
        this.doctor_per_pharmacy = doctor_per_pharmacy;
        this.convenience_per_pharmacy = convenience_per_pharmacy;
        this.viewcount = viewcount;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getLoad_address() {
        return load_address;
    }

    public void setLoad_address(String load_address) {
        this.load_address = load_address;
    }

    public int getHospital_count() {
        return hospital_count;
    }

    public void setHospital_count(int hospital_count) {
        this.hospital_count = hospital_count;
    }

    public int getPharmacy_count() {
        return pharmacy_count;
    }

    public void setPharmacy_count(int pharmacy_count) {
        this.pharmacy_count = pharmacy_count;
    }

    public int getConvenience_count() {
        return convenience_count;
    }

    public void setConvenience_count(int convenience_count) {
        this.convenience_count = convenience_count;
    }

    public int getDoctorcount() {
        return doctorcount;
    }

    public void setDoctorcount(int doctorcount) {
        this.doctorcount = doctorcount;
    }

    public String getDong() {
        return dong;
    }

    public void setDong(String dong) {
        this.dong = dong;
    }

    public float getHospital_per_pharmacy() {
        return hospital_per_pharmacy;
    }

    public void setHospital_per_pharmacy(float hospital_per_pharmacy) {
        this.hospital_per_pharmacy = hospital_per_pharmacy;
    }

    public float getDoctor_per_pharmacy() {
        return doctor_per_pharmacy;
    }

    public void setDoctor_per_pharmacy(float doctor_per_pharmacy) {
        this.doctor_per_pharmacy = doctor_per_pharmacy;
    }

    public float getConvenience_per_pharmacy() {
        return convenience_per_pharmacy;
    }

    public void setConvenience_per_pharmacy(float convenience_per_pharmacy) {
        this.convenience_per_pharmacy = convenience_per_pharmacy;
    }

    public int getViewcount() {
        return viewcount;
    }

    public void setViewcount(int viewcount) {
        this.viewcount = viewcount;
    }
}
