package com.example.yokgoodchild.roadsideassistance.ViewReply;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.yokgoodchild.roadsideassistance.AcceptReply.AcceptReplyActivity;
import com.example.yokgoodchild.roadsideassistance.ClassBean.ApplicantBean;
import com.example.yokgoodchild.roadsideassistance.ClassBean.ReplyBean;
import com.example.yokgoodchild.roadsideassistance.R;
import com.example.yokgoodchild.roadsideassistance.ViewReply.CustomListview_ViewReply.CustomAdapter_ViewReply;
import com.google.gson.Gson;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

public class ViewReplyActivity extends AppCompatActivity implements ViewReplyManager.View{

    public static final String DATA_LOGIN = "DATA_LOGIN";
    public static final String LOGIN_DATA = "LOGIN_DATA";
    private static CustomAdapter_ViewReply adapter_viewReply;

    private ViewReplyManager viewReplyManager;
    private GetViewReply getViewReply;
    private ApplicantBean applicantBean;

    private ListView listview;
    private AlertDialog dialog;

    private String status = "0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reply);

        int baseColor = ContextCompat.getColor(this, R.color.colorToolbar_text);
        Toolbar toolbar = (Toolbar) findViewById(R.id.view_reply_toolbar);
        toolbar.setTitle("View Reply");
        toolbar.setTitleTextColor(baseColor);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sp = getSharedPreferences(DATA_LOGIN, Context.MODE_PRIVATE);
        String data_login = sp.getString(LOGIN_DATA, "Fail");
        if(data_login != "Fail"){
            applicantBean = new Gson().fromJson(data_login,ApplicantBean.class);
        }

        listview = (ListView) findViewById(R.id.view_reply_list);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dataIntent = new Gson().toJson(getViewReply.getListReplyData().get(position));
                Intent intent = new Intent(ViewReplyActivity.this, AcceptReplyActivity.class);
                intent.putExtra("reply",dataIntent);
                intent.putExtra("status",status);
                startActivity(intent);
            }
        });

        final RadioButton radio01 = (RadioButton) findViewById(R.id.view_reply_radio001);
        final RadioButton radio02 = (RadioButton) findViewById(R.id.view_reply_radio002);

        dialog = new SpotsDialog(this, R.style.Custom);
        radio01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio02.setChecked(false);
                status = "0";
                dialog.show();
                viewReplyManager.getReplyDetail(applicantBean.getCardID(),status);
            }
        });

        radio02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio01.setChecked(false);
                status = "2";
                dialog.show();
                viewReplyManager.getReplyDetail(applicantBean.getCardID(),status);
            }
        });

        listview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ViewReplyActivity.this, "Click", Toast.LENGTH_SHORT).show();
                String dataIntent = new Gson().toJson(getViewReply.getListReplyData().get(position));
                Intent intent = new Intent(ViewReplyActivity.this, AcceptReplyActivity.class);
                intent.putExtra("reply",dataIntent);
                intent.putExtra("status",status);
                startActivity(intent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        viewReplyManager = new ViewReplyManager();
        viewReplyManager.callRetrofit(this);
        getViewReply = new GetViewReply();
    }

    @Override
    public void updateViewReply(GetViewReply data) {
        dialog.dismiss();
        getViewReply = data;
        ArrayList<ReplyBean> dataModels = new ArrayList<>();
        for(int i = 0 ; i < data.getListReplyData().size() ; i++){
            dataModels.add(new ReplyBean(
                    data.getListReplyData().get(i).getReplyid(),
                    data.getListReplyData().get(i).getTitleReply(),
                    data.getListReplyData().get(i).getDateReply(),
                    data.getListReplyData().get(i).getStatusReply(),
                    data.getListReplyData().get(i).getRequestid(),
                    data.getListReplyData().get(i).getRepair(),
                    data.getListReplyData().get(i).getDetail(),
                    data.getListReplyData().get(i).getPrice(),
                    data.getListReplyData().get(i).getPhoto()
                    ));
        }
        adapter_viewReply = new CustomAdapter_ViewReply(dataModels,this,status);
        listview.setAdapter(adapter_viewReply);

    }


}
