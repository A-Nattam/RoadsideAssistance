package com.example.yokgoodchild.roadsideassistance.ViewScore;

import com.example.yokgoodchild.roadsideassistance.App.AppRetrofit;
import com.example.yokgoodchild.roadsideassistance.ClassBean.ViewScoreBean;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by YokGoodChild on 6/24/2017.
 */

public class ViewScoreManager extends AppRetrofit {

    private CallGetViewScore callGetViewScore;
    private GetViewScore getViewScore;
    private View listener;

    public void  callRetrofit(View listener){

        this.listener = listener;

        callGetViewScore = getService().create(CallGetViewScore.class);

    }

    public void getViewScore(String registrationID){
        try{
            Call<GetViewScore> call = callGetViewScore.getViewScoreRepair(registrationID);
            call.enqueue(new Callback<GetViewScore>() {
                @Override
                public void onResponse(Call<GetViewScore> call, Response<GetViewScore> response) {
                    getViewScore = response.body();
                    ViewScoreBean viewScoreBean = new ViewScoreBean();
                    viewScoreBean = getViewScore.getViewScoreBean();
                    listener.getViewScoreRepair(viewScoreBean);
                }

                @Override
                public void onFailure(Call<GetViewScore> call, Throwable t) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface View{
        void getViewScoreRepair(ViewScoreBean data);
    }

}
