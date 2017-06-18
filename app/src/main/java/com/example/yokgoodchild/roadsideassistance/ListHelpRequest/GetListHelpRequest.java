package com.example.yokgoodchild.roadsideassistance.ListHelpRequest;

import com.example.yokgoodchild.roadsideassistance.ClassBean.RequestBean;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by YokGoodChild on 6/10/2017.
 */

public class GetListHelpRequest {

    @SerializedName("listRequestData")
    @Expose
    private List<RequestBean> listRequestData = null;

    public List<RequestBean> getListRequestData() {
        return listRequestData;
    }

    public void setListRequestData(List<RequestBean> listRequestData) {
        this.listRequestData = listRequestData;
    }

}
