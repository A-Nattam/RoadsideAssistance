package com.example.yokgoodchild.roadsideassistance.RateServiceScore;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.yokgoodchild.roadsideassistance.ClassBean.ApplicantBean;
import com.example.yokgoodchild.roadsideassistance.ClassBean.ReplyBean;
import com.example.yokgoodchild.roadsideassistance.R;
import com.example.yokgoodchild.roadsideassistance.ViewReply.CallGetViewReply;
import com.example.yokgoodchild.roadsideassistance.ViewReply.CustomListview_ViewReply.CustomAdapter_ViewReply;
import com.example.yokgoodchild.roadsideassistance.ViewReply.GetViewReply;
import com.example.yokgoodchild.roadsideassistance.ViewReply.ViewReplyManager;
import com.google.gson.Gson;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

public class RateServiceScoreActivity extends AppCompatActivity implements ViewReplyManager.View{

    public static final String DATA_LOGIN = "DATA_LOGIN";
    public static final String LOGIN_DATA = "LOGIN_DATA";

    private static CustomAdapter_ViewReply adapter_viewReply;

    private ViewReplyManager viewReplyManager;

    private GetViewReply getViewReply;
    private RateServiceScoreManager rateServiceScoreManager;
    private ReplyBean replyBean;
    private ApplicantBean applicantBean;
    private String status = "1";

    private ListView listview;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_service_score);

        SharedPreferences sp = getSharedPreferences(DATA_LOGIN, Context.MODE_PRIVATE);
        String data_login = sp.getString(LOGIN_DATA, "Fail");
        if(data_login != "Fail"){
            applicantBean = new Gson().fromJson(data_login,ApplicantBean.class);
        }

        listview  = (ListView) findViewById(R.id.ac_rate_service_score_list);

    }

    @Override
    protected void onResume() {
        super.onResume();
        rateServiceScoreManager = new RateServiceScoreManager();
        viewReplyManager = new ViewReplyManager();
        viewReplyManager.callRetrofit(this);
        getViewReply = new GetViewReply();
        viewReplyManager.getReplyDetail(applicantBean.getCardID(),status);
        dialog = new SpotsDialog(this, R.style.Custom);
        dialog.show();
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
