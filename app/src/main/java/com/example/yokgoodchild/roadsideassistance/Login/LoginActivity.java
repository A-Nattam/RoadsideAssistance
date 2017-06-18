package com.example.yokgoodchild.roadsideassistance.Login;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yokgoodchild.roadsideassistance.ClassBean.ApplicantBean;
import com.example.yokgoodchild.roadsideassistance.ClassBean.LoginBean;
import com.example.yokgoodchild.roadsideassistance.ClassBean.RepairShopBean;
import com.example.yokgoodchild.roadsideassistance.Main_RepairShop;
import com.example.yokgoodchild.roadsideassistance.Main_User;
import com.example.yokgoodchild.roadsideassistance.R;
import com.example.yokgoodchild.roadsideassistance.SharedPreferences.SingleTon;
import com.example.yokgoodchild.roadsideassistance.SharedPreferences.StringPreferences;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements LoginManager.View{

    private LoginManager loginManager;
    public static final String DATA_LOGIN = "DATA_LOGIN";
    public static final String LOGIN_DATA = "LOGIN_DATA";
    public static final String LOGIN_STATUS = "LOGIN_STATUS";
    private SharedPreferences sp;
    private EditText e_username;
    private EditText e_password;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp = getSharedPreferences(DATA_LOGIN, Context.MODE_PRIVATE);
        StringPreferences session = new StringPreferences(sp,LOGIN_STATUS);
        String check = session.get();

        if(check.equals("2")){
            gotoMain_User();
        }else if(check.equals("3")){
            gotoMain_Repairshop();
        }else{
            Toast.makeText(this, "No Session", Toast.LENGTH_SHORT).show();
        }

        e_username = (EditText) findViewById(R.id.login_txt_username);
        e_password = (EditText) findViewById(R.id.login_txt_password);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loginManager = new LoginManager();
        loginManager.callRetrofit(this);
    }

    public void onClickSubmitLogin(View v){
        try{
            LoginBean login = new LoginBean(e_username.getText().toString(),e_password.getText().toString());
            progressDialog = new ProgressDialog(this);
            progressDialog.show();
            loginManager.getLoginData(login.getUsername(),login.getPassword());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setStringPrefrefernces(String Data_Gson,String status){
        sp = getSharedPreferences(DATA_LOGIN, Context.MODE_PRIVATE);
        StringPreferences session_data_login = new StringPreferences(sp,LOGIN_DATA);
        StringPreferences session_status_login = new StringPreferences(sp,LOGIN_STATUS);
        session_data_login.setValue(Data_Gson);
        session_status_login.setValue(status);
    }

    public void gotoMain_User(){
        Intent intent = new Intent(LoginActivity.this, Main_User.class);
        startActivity(intent);
        finish();
    }

    public void gotoMain_Repairshop(){
        Intent intent = new Intent(LoginActivity.this, Main_RepairShop.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onBackPressed() {

        Dialog dialog = new Dialog(LoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertdialog_exit);
        Button ok = (Button) dialog.findViewById(R.id.alert_Dialog_btn_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        dialog.show();

    }

    @Override
    public void updateView(GetLogin getLogin) {
        progressDialog.dismiss();
        Toast.makeText(this, "Data "+getLogin.getRepairshop().get(0).getApplicant().getCardID(), Toast.LENGTH_SHORT).show();
                    if(getLogin.getRepairshop().get(0).getApplicant().getStatus() != null){
                String status = getLogin.getRepairshop().get(0).getApplicant().getStatus();
                if(status.equals("2")){
                    ApplicantBean applicantBean = new ApplicantBean();
                    applicantBean = getLogin.getRepairshop().get(0).getApplicant();

                    Gson gson = new Gson();
                    String Data_Gson = gson.toJson(applicantBean);
                    setStringPrefrefernces(Data_Gson,status);

                    gotoMain_User();

                }else if(status.equals("3")){
                    RepairShopBean repairShopBean = new RepairShopBean();
                    repairShopBean = getLogin.getRepairshop().get(0);

                    Gson gson = new Gson();
                    String Data_Gson = gson.toJson(repairShopBean);
                    setStringPrefrefernces(Data_Gson,status);

                    gotoMain_Repairshop();

                }else{
                    Toast.makeText(LoginActivity.this, "กรุณา Login ใหม่อีกครั้ง", Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(LoginActivity.this, "ชื่อผู้ใช้ หรือ รหัสผ่านผิด ", Toast.LENGTH_SHORT).show();
            }
    }
}
