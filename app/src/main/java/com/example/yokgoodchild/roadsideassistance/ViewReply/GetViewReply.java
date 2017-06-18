package com.example.yokgoodchild.roadsideassistance.ViewReply;

import com.example.yokgoodchild.roadsideassistance.ClassBean.ReplyBean;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by YokGoodChild on 6/2/2017.
 */

public class GetViewReply {

    @SerializedName("listReplyData")
    @Expose
    private List<ReplyBean> listReplyData = null;

    public List<ReplyBean> getListReplyData() {
        return listReplyData;
    }

    public void setListReplyData(List<ReplyBean> listReplyData) {
        this.listReplyData = listReplyData;
    }
}
