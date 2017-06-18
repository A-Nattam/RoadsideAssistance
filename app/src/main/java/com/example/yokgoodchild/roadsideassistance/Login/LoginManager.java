package com.example.yokgoodchild.roadsideassistance.Login;

import android.util.Log;
import android.widget.Toast;

import com.example.yokgoodchild.roadsideassistance.App.AppRetrofit;
import com.example.yokgoodchild.roadsideassistance.ClassBean.LoginBean;
import com.example.yokgoodchild.roadsideassistance.ClassBean.RepairShopBean;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by YokGoodChild on 6/1/2017.
 */

public class LoginManager extends AppRetrofit {

    private CallGetLogin getLogin;
    private LoginBean login;
    private GetLogin get_login;
    private View listener;

    public void callRetrofit(View listener){

        this.listener = listener;

        getLogin = getService().create(CallGetLogin.class);
    }

    public void getLoginData(String username, String password){

        login = new LoginBean(username,password);

        try{
            Call<GetLogin> call = getLogin.getDataLogin(login.getUsername(),login.getPassword());
            call.enqueue(new Callback<GetLogin>() {
                @Override
                public void onResponse(Call<GetLogin> call, Response<GetLogin> response) {
                    get_login = new GetLogin();
                    get_login = response.body();
                    listener.updateView(get_login);
                }

                @Override
                public void onFailure(Call<GetLogin> call, Throwable t) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public interface View{
        void updateView(GetLogin g);
    }
}
