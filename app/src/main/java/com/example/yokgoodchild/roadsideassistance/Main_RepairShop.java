package com.example.yokgoodchild.roadsideassistance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.yokgoodchild.roadsideassistance.GenerateQRCode.GenerateQRCodeActivity;
import com.example.yokgoodchild.roadsideassistance.ListHelpRequest.ListHelpRequestActivity;
import com.example.yokgoodchild.roadsideassistance.Login.LoginActivity;
import com.example.yokgoodchild.roadsideassistance.SharedPreferences.StringPreferences;

public class Main_RepairShop extends AppCompatActivity {

    public static final String DATA_LOGIN = "DATA_LOGIN";
    public static final String LOGIN_DATA = "LOGIN_DATA";
    public static final String LOGIN_STATUS = "LOGIN_STATUS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__repair_shop);

        com.github.clans.fab.FloatingActionButton logout = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_logout_repair_shop);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Main_RepairShop.this, "Log out", Toast.LENGTH_SHORT).show();
                SharedPreferences sp = getSharedPreferences(DATA_LOGIN, Context.MODE_PRIVATE);
                clearSessionLogin(sp,LOGIN_DATA);
                clearSessionLogin(sp,LOGIN_STATUS);

                Intent intent = new Intent(Main_RepairShop.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void onClickListHelpRequest(View v){
        Intent intent = new Intent(Main_RepairShop.this, ListHelpRequestActivity.class);
        startActivity(intent);
    }

    public void clearSessionLogin(SharedPreferences s, String key){
        StringPreferences session = new StringPreferences(s,key);
        session.delete();
    }

    public void onClickGenerateQRCode(View v){
        Intent intent = new Intent(Main_RepairShop.this, GenerateQRCodeActivity.class);
        startActivity(intent);
    }
}
