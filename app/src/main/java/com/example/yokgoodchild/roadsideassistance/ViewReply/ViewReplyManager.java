package com.example.yokgoodchild.roadsideassistance.ViewReply;

import com.example.yokgoodchild.roadsideassistance.App.AppRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by YokGoodChild on 6/2/2017.
 */

public class ViewReplyManager extends AppRetrofit {

    private CallGetViewReply callGetViewReply;
    private GetViewReply getViewReply;
    private View listener;

    public void callRetrofit(View listener){

        this.listener = listener;

        callGetViewReply = getService().create(CallGetViewReply.class);
    }

    public void getReplyDetail(String cardID,String status){
        try{
            Call<GetViewReply> call = callGetViewReply.getAll_ViewReply(cardID,status);
            call.enqueue(new Callback<GetViewReply>() {
                @Override
                public void onResponse(Call<GetViewReply> call, Response<GetViewReply> response) {
                    getViewReply = response.body();
                    listener.updateViewReply(getViewReply);
                }

                @Override
                public void onFailure(Call<GetViewReply> call, Throwable t) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface View{
        void updateViewReply(GetViewReply data);
    }
}
