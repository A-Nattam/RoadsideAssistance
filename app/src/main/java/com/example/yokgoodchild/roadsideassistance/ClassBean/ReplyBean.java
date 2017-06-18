package com.example.yokgoodchild.roadsideassistance.ClassBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by YokGoodChild on 6/1/2017.
 */

public class ReplyBean {

    @SerializedName("replyid")
    @Expose
    private Integer replyid;
    @SerializedName("titleReply")
    @Expose
    private String titleReply;
    @SerializedName("dateReply")
    @Expose
    private String dateReply;
    @SerializedName("statusReply")
    @Expose
    private String statusReply;
    @SerializedName("requestid")
    @Expose
    private Integer requestid;
    @SerializedName("repair")
    @Expose
    private RepairShopBean repair;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("photo")
    @Expose
    private String photo;

    public ReplyBean(Integer replyid, String titleReply, String dateReply, String statusReply, Integer requestid, RepairShopBean repair, String detail, Integer price, String photo) {
        this.replyid = replyid;
        this.titleReply = titleReply;
        this.dateReply = dateReply;
        this.statusReply = statusReply;
        this.requestid = requestid;
        this.repair = repair;
        this.detail = detail;
        this.price = price;
        this.photo = photo;
    }

    public ReplyBean() {
    }

    public Integer getReplyid() {
        return replyid;
    }

    public void setReplyid(Integer replyid) {
        this.replyid = replyid;
    }

    public String getTitleReply() {
        return titleReply;
    }

    public void setTitleReply(String titleReply) {
        this.titleReply = titleReply;
    }

    public String getDateReply() {
        return dateReply;
    }

    public void setDateReply(String dateReply) {
        this.dateReply = dateReply;
    }

    public String getStatusReply() {
        return statusReply;
    }

    public void setStatusReply(String statusReply) {
        this.statusReply = statusReply;
    }

    public Integer getRequestid() {
        return requestid;
    }

    public void setRequestid(Integer requestid) {
        this.requestid = requestid;
    }

    public RepairShopBean getRepair() {
        return repair;
    }

    public void setRepair(RepairShopBean repair) {
        this.repair = repair;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
