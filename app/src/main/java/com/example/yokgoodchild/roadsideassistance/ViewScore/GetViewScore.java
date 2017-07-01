package com.example.yokgoodchild.roadsideassistance.ViewScore;

import com.example.yokgoodchild.roadsideassistance.ClassBean.ViewScoreBean;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by YokGoodChild on 6/24/2017.
 */

public class GetViewScore {

    @SerializedName("ViewScore")
    @Expose
    private ViewScoreBean viewScoreBean = null;

    public ViewScoreBean getViewScoreBean() {
        return viewScoreBean;
    }

}
