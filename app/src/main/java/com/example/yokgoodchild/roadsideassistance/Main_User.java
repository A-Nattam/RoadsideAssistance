package com.example.yokgoodchild.roadsideassistance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.yokgoodchild.roadsideassistance.Login.LoginActivity;
import com.example.yokgoodchild.roadsideassistance.Map.MapsUserActivity;
import com.example.yokgoodchild.roadsideassistance.RateServiceScore.RateServiceScoreActivity;
import com.example.yokgoodchild.roadsideassistance.RequestForHelp.RequestForHelpActivity;
import com.example.yokgoodchild.roadsideassistance.SharedPreferences.StringPreferences;
import com.example.yokgoodchild.roadsideassistance.ViewReply.ViewReplyActivity;

public class Main_User extends AppCompatActivity {

    public static final String DATA_LOGIN = "DATA_LOGIN";
    public static final String LOGIN_DATA = "LOGIN_DATA";
    public static final String LOGIN_STATUS = "LOGIN_STATUS";

    private com.github.clans.fab.FloatingActionMenu MARTHER_MENU ;
    private int checkClick = 0;
    private Button list_request;
    private Button validator_score;
    private Button view_maps;
    private Button qr_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__user);

        list_request = (Button) findViewById(R.id.main_user_btn_request);
        validator_score = (Button) findViewById(R.id.main_user_btn_view_reply);
        view_maps = (Button) findViewById(R.id.main_user_btn_view_map);
        qr_code = (Button) findViewById(R.id.main_user_btn_reate_score);


        findViewById(R.id.fab_logout_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Main_User.this, "Log out", Toast.LENGTH_SHORT).show();
                SharedPreferences sp = getSharedPreferences(DATA_LOGIN, Context.MODE_PRIVATE);
                clearSessionLogin(sp,LOGIN_DATA);
                clearSessionLogin(sp,LOGIN_STATUS);

                Intent intent = new Intent(Main_User.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void clearSessionLogin(SharedPreferences s,String key){
        StringPreferences session = new StringPreferences(s,key);
        session.delete();
    }

    public void onClickMain_User_Request(View v){
        Intent intent = new Intent(this, RequestForHelpActivity.class);
        startActivity(intent);
    }

    public void onClickMain_User_ViewReply(View v){
        Intent intent = new Intent(Main_User.this,ViewReplyActivity.class);
        startActivity(intent);
    }

    public void onClickMain_User_RateServiceScore(View v){
        Intent intent = new Intent(Main_User.this,RateServiceScoreActivity.class);
        startActivity(intent);
    }

    public void onClickMain_User_Maps(View v){
        Intent intent = new Intent(Main_User.this,MapsUserActivity.class);
        startActivity(intent);
    }

    public void checkClickFloatingActionMenu(boolean chk){
            list_request.setEnabled(chk);
            validator_score.setEnabled(chk);
            view_maps.setEnabled(chk);
            qr_code.setEnabled(chk);

    }

}
