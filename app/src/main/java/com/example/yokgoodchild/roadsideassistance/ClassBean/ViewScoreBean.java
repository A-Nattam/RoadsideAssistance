package com.example.yokgoodchild.roadside.View_Score.View_Score_Retrofit2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by YokGoodChild on 5/25/2017.
 */

public class ViewScoreBean {

    @SerializedName("number")
    @Expose
    private int number;
    @SerializedName("total")
    @Expose
    private int total;

    public ViewScoreBean(int number, int total) {
        super();
        this.number = number;
        this.total = total;
    }

    public ViewScoreBean(){

    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
