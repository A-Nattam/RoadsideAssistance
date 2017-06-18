package com.example.yokgoodchild.roadsideassistance.App;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.yokgoodchild.roadsideassistance.BuildConfig;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by YokGoodChild on 6/2/2017.
 */

public class AppRetrofit  {

    public static Retrofit getService(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BuildConfig.RSA_API_BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                build();

        return retrofit ;
    }


}
