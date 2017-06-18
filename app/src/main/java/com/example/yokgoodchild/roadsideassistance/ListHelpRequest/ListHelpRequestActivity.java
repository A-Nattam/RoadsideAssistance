package com.example.yokgoodchild.roadsideassistance.ListHelpRequest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.yokgoodchild.roadsideassistance.ClassBean.RepairShopBean;
import com.example.yokgoodchild.roadsideassistance.ClassBean.RequestBean;
import com.example.yokgoodchild.roadsideassistance.ListHelpRequest.CustomListview_ListHelpRequest.CustomAdapter_ListHelpRequest;
import com.example.yokgoodchild.roadsideassistance.R;
import com.example.yokgoodchild.roadsideassistance.ReplyHelpRequest.ReplyHelpRequestActivity;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ListHelpRequestActivity extends AppCompatActivity implements ListHelpRequestManager.View {

    public static final String DATA_LOGIN = "DATA_LOGIN";
    public static final String LOGIN_DATA = "LOGIN_DATA";
    private static CustomAdapter_ListHelpRequest adapter;
    private RepairShopBean repairShopBean;
    private RadioButton radio_request;
    private RadioButton radio_accept_reply;
    private ListView listview;
    private ListHelpRequestManager listHelpRequestManager;
    private String status = "0";
    private ArrayList<RequestBean> dataModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_help_request);

        radio_request = (RadioButton) findViewById(R.id.list_help_request_radio_request);
        radio_accept_reply = (RadioButton) findViewById(R.id.list_help_request_radio_accept_reply);
        listview = (ListView) findViewById(R.id.list_help_request_list_view);

        SharedPreferences sp = getSharedPreferences(DATA_LOGIN, Context.MODE_PRIVATE);
        String data_login = sp.getString(LOGIN_DATA, "Fail");
        if(data_login != "Fail"){
            repairShopBean = new Gson().fromJson(data_login,RepairShopBean.class);
        }

        radio_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio_accept_reply.setChecked(false);
                status = "0" ;
                listHelpRequestManager.getListHelpRequest(repairShopBean.getRegistrationID(), status);
            }
        });

        radio_accept_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio_request.setChecked(false);
                status = "1" ;
                listHelpRequestManager.getListHelpRequest(repairShopBean.getRegistrationID(), status);
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Gson gson = new Gson();
                String obj = gson.toJson(dataModels.get(position));

                Intent intent = new Intent(ListHelpRequestActivity.this, ReplyHelpRequestActivity.class);
                intent.putExtra("request",obj);
                intent.putExtra("status",status);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        listHelpRequestManager = new ListHelpRequestManager();
        listHelpRequestManager.callRetrofit(this);
    }


    @Override
    public void updateListHelpRequest(GetListHelpRequest list) {
        dataModels = new ArrayList<>();
        for(int i = 0 ; i < list.getListRequestData().size() ; i++){
            dataModels.add(new RequestBean(
                    list.getListRequestData().get(i).getRequestID(),
                    list.getListRequestData().get(i).getTitleDamage(),
                    list.getListRequestData().get(i).getName(),
                    list.getListRequestData().get(i).getDamageDetail(),
                    list.getListRequestData().get(i).getCarType(),
                    list.getListRequestData().get(i).getPhoto(),
                    list.getListRequestData().get(i).getLatitude(),
                    list.getListRequestData().get(i).getLongitude(),
                    list.getListRequestData().get(i).getScore(),
                    list.getListRequestData().get(i).getStatusRequest(),
                    list.getListRequestData().get(i).getDateRequest(),
                    list.getListRequestData().get(i).getApplicant(),
                    list.getListRequestData().get(i).getReply()
            ));
        }

        adapter = new CustomAdapter_ListHelpRequest(dataModels,getApplicationContext(),status);

        listview.setAdapter(adapter);

    }


}
