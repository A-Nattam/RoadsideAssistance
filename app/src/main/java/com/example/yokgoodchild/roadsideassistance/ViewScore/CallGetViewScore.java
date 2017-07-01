package com.example.yokgoodchild.roadsideassistance.ViewScore;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by YokGoodChild on 6/24/2017.
 */

public interface CallGetViewScore {

    @POST("ViewScoreServlet")
    Call<GetViewScore> getViewScoreRepair(@Query("registrationID")String registrationid);

}
