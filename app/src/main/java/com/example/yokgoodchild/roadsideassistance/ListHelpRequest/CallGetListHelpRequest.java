package com.example.yokgoodchild.roadsideassistance.ListHelpRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by YokGoodChild on 6/10/2017.
 */

public interface CallGetListHelpRequest {

    @GET("JSONListHelpRequestsServlet")
    Call<GetListHelpRequest> getAll_ListHelpRequest(@Query("repairID")String repairID, @Query("status")String status);

}
