package com.example.yokgoodchild.roadsideassistance.ListHelpRequest;


import android.widget.Toast;

import com.example.yokgoodchild.roadsideassistance.App.AppRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by YokGoodChild on 6/10/2017.
 */

public class ListHelpRequestManager extends AppRetrofit {

    private CallGetListHelpRequest callGetListHelpRequest;
    private GetListHelpRequest getListHelpRequest;
    private View listener;

    public void callRetrofit(View listener){

        this.listener = listener;

        callGetListHelpRequest = getService().create(CallGetListHelpRequest.class);
    }

    public void getListHelpRequest(String cardID,String status){
        try{
            Call<GetListHelpRequest> call = callGetListHelpRequest.getAll_ListHelpRequest(cardID,status);
            call.enqueue(new Callback<GetListHelpRequest>() {
                @Override
                public void onResponse(Call<GetListHelpRequest> call, Response<GetListHelpRequest> response) {
                    getListHelpRequest = response.body();
                    listener.updateListHelpRequest(getListHelpRequest);
                }

                @Override
                public void onFailure(Call<GetListHelpRequest> call, Throwable t) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface View{
        void updateListHelpRequest(GetListHelpRequest list);
    }

}
