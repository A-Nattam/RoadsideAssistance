package com.example.yokgoodchild.roadsideassistance.Login;

import com.example.yokgoodchild.roadsideassistance.ClassBean.RepairShopBean;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by YokGoodChild on 6/1/2017.
 */

public class GetLogin {

    @SerializedName("repairshop")
    @Expose
    private List<RepairShopBean> repairshop = null;

    public List<RepairShopBean> getRepairshop() {
        return repairshop;
    }

    public void setRepairshop(List<RepairShopBean> repairshop) {
        this.repairshop = repairshop;
    }
}
