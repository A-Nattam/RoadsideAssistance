package com.example.yokgoodchild.roadsideassistance.ClassBean;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by YokGoodChild on 6/1/2017.
 */

public class RepairShopBean {

    @SerializedName("registrationID")
    @Expose
    private String registrationID;
    @SerializedName("nameShop")
    @Expose
    private String nameShop;
    @SerializedName("phoneShop")
    @Expose
    private String phoneShop;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("applicant")
    @Expose
    private ApplicantBean applicant;

    public String getRegistrationID() {
        return registrationID;
    }

    public void setRegistrationID(String registrationID) {
        this.registrationID = registrationID;
    }

    public String getNameShop() {
        return nameShop;
    }

    public void setNameShop(String nameShop) {
        this.nameShop = nameShop;
    }

    public String getPhoneShop() {
        return phoneShop;
    }

    public void setPhoneShop(String phoneShop) {
        this.phoneShop = phoneShop;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public ApplicantBean getApplicant() {
        return applicant;
    }

    public void setApplicant(ApplicantBean applicant) {
        this.applicant = applicant;
    }
}
