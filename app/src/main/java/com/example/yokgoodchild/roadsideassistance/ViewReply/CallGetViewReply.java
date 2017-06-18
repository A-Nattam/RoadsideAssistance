package com.example.yokgoodchild.roadsideassistance.ViewReply;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by YokGoodChild on 6/2/2017.
 */

public interface CallGetViewReply {

    @POST("ViewReplyServlet")
    Call<GetViewReply> getAll_ViewReply(@Query("cardID")String cardID, @Query("status")String status);

}
