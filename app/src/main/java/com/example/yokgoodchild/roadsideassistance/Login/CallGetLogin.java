package com.example.yokgoodchild.roadsideassistance.Login;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by YokGoodChild on 6/1/2017.
 */

public interface CallGetLogin {

    @GET("JSONsLoginServlet")
    Call<GetLogin> getDataLogin(@Query("username")String username, @Query("password")String password);

}
