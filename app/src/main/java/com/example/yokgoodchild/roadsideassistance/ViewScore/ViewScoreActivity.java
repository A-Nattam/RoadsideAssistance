package com.example.yokgoodchild.roadsideassistance.ViewScore;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yokgoodchild.roadsideassistance.ClassBean.RepairShopBean;
import com.example.yokgoodchild.roadsideassistance.ClassBean.ViewScoreBean;
import com.example.yokgoodchild.roadsideassistance.R;
import com.google.gson.Gson;

public class ViewScoreActivity extends AppCompatActivity implements ViewScoreManager.View {

    public static final String DATA_LOGIN = "DATA_LOGIN";
    public static final String LOGIN_DATA = "LOGIN_DATA";
    private RepairShopBean repairShopBean;
    private ViewScoreManager viewScoreManager;
    private ImageView img;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_score);

        int baseColor = ContextCompat.getColor(this, R.color.colorToolbar_text);
        Toolbar toolbar = (Toolbar) findViewById(R.id.ac_view_score_toolbar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(baseColor);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sp = getSharedPreferences(DATA_LOGIN, Context.MODE_PRIVATE);
        String data_login = sp.getString(LOGIN_DATA, "Fail");
        if (data_login != "Fail") {
            repairShopBean = new Gson().fromJson(data_login, RepairShopBean.class);
        }

        img = (ImageView) findViewById(R.id.ac_view_score_Image_View);
        textView = (TextView) findViewById(R.id.ac_view_score_txt_status);

        viewScoreManager = new ViewScoreManager();
        viewScoreManager.callRetrofit(this);
        viewScoreManager.getViewScore(repairShopBean.getRegistrationID());

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void getViewScoreRepair(ViewScoreBean data) {
        Toast.makeText(this, "Score " + data.getTotal(), Toast.LENGTH_SHORT).show();
        int total = (data.getTotal() / data.getNumber());
        Toast.makeText(this, "Total "+total, Toast.LENGTH_SHORT).show();
        if( data.getNumber() != 0){
            if(total >= 0.0 && total < 1.0){
                img.setImageResource(R.drawable.ic_emotion_bad);
                textView.setText("บริการ แย่มาก");
            }else if(total >= 1.0 && total < 2.0 ){
                img.setImageResource(R.drawable.ic_emotion_sad);
                textView.setText("บริการ แย่");
            }else if(total >= 2.00 && total < 3.0 ){
                img.setImageResource(R.drawable.ic_emotion_nomal);
                textView.setText("บริการ พอใช้");
            }else if(total >= 3.00 && total < 4.0 ){
                img.setImageResource(R.drawable.ic_emotion_good);
                textView.setText("บริการ ดี");
            }else if(total >= 4.00 && total <= 5.0 ){
                img.setImageResource(R.drawable.ic_emotion_happy);
                textView.setText("บริการ ดีมาก");
            }
        }else{
            img.setImageResource(R.drawable.ic_emotion_nomal);
            textView.setText("คุณยังไม่เคยให้บริการ");
        }
    }

    public void verifyScore(ViewScoreBean v_score){

    }

}
