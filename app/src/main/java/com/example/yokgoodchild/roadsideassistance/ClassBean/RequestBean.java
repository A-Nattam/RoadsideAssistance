package com.example.yokgoodchild.roadsideassistance.ClassBean;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Vector;

/**
 * Created by YokGoodChild on 6/1/2017.
 */

public class RequestBean {

    @SerializedName("requestID")
    @Expose
    private Integer requestID;
    @SerializedName("titleDamage")
    @Expose
    private String titleDamage;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("damageDetail")
    @Expose
    private String damageDetail;
    @SerializedName("carType")
    @Expose
    private String carType;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("statusRequest")
    @Expose
    private String statusRequest;
    @SerializedName("dateRequest")
    @Expose
    private String dateRequest;
    @SerializedName("applicant")
    @Expose
    private ApplicantBean applicant = new ApplicantBean();
    @SerializedName("reply")
    @Expose
    private Vector<ReplyBean> reply = new Vector<ReplyBean>();

    public RequestBean(Integer requestID, String titleDamage, String name, String damageDetail, String carType, String photo, String latitude, String longitude, Integer score, String statusRequest, String dateRequest, ApplicantBean applicant, Vector<ReplyBean> reply) {
        this.requestID = requestID;
        this.titleDamage = titleDamage;
        this.name = name;
        this.damageDetail = damageDetail;
        this.carType = carType;
        this.photo = photo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.score = score;
        this.statusRequest = statusRequest;
        this.dateRequest = dateRequest;
        this.applicant = applicant;
        this.reply = reply;
    }

    public RequestBean() {
    }

    public Integer getRequestID() {
        return requestID;
    }

    public void setRequestID(Integer requestID) {
        this.requestID = requestID;
    }

    public String getTitleDamage() {
        return titleDamage;
    }

    public void setTitleDamage(String titleDamage) {
        this.titleDamage = titleDamage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDamageDetail() {
        return damageDetail;
    }

    public void setDamageDetail(String damageDetail) {
        this.damageDetail = damageDetail;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getStatusRequest() {
        return statusRequest;
    }

    public void setStatusRequest(String statusRequest) {
        this.statusRequest = statusRequest;
    }

    public String getDateRequest() {
        return dateRequest;
    }

    public void setDateRequest(String dateRequest) {
        this.dateRequest = dateRequest;
    }

    public ApplicantBean getApplicant() {
        return applicant;
    }

    public void setApplicant(ApplicantBean applicant) {
        this.applicant = applicant;
    }

    public Vector<ReplyBean> getReply() {
        return reply;
    }

    public void setReply(Vector<ReplyBean> reply) {
        this.reply = reply;
    }
}
