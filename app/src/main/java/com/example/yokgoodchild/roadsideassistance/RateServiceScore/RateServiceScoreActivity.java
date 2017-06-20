package com.example.yokgoodchild.roadsideassistance.RateServiceScore;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yokgoodchild.roadsideassistance.ClassBean.ApplicantBean;
import com.example.yokgoodchild.roadsideassistance.ClassBean.ReplyBean;
import com.example.yokgoodchild.roadsideassistance.R;
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
    private ArrayList<ReplyBean> dataModels;
    private ListView listview;
    private AlertDialog dialog;
    private int positioned ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_service_score);

        int baseColor = ContextCompat.getColor(this, R.color.colorToolbar_text);
        Toolbar toolbar = (Toolbar) findViewById(R.id.rate_service_score_toolbar);
        toolbar.setTitle("Accept Reply");
        toolbar.setTitleTextColor(baseColor);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sp = getSharedPreferences(DATA_LOGIN, Context.MODE_PRIVATE);
        String data_login = sp.getString(LOGIN_DATA, "Fail");
        if(data_login != "Fail"){
            applicantBean = new Gson().fromJson(data_login,ApplicantBean.class);
        }

        listview  = (ListView) findViewById(R.id.ac_rate_service_score_list);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                positioned = position;
                replyBean = dataModels.get(positioned) ;
                Toast.makeText(RateServiceScoreActivity.this, replyBean.getTitleReply(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RateServiceScoreActivity.this,ScanQRCodeRateServiceScoreActivity.class);
                startActivityForResult(intent,1);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
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
        dataModels = new ArrayList<>();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                String dataed = data.getStringExtra("RepairShopID");
                Toast.makeText(this, "Result Ok "+dataed+" psition " +positioned, Toast.LENGTH_SHORT).show();
                if(replyBean.getRepair().getRegistrationID().equals(dataed)){
                    Toast.makeText(this, "ถูกต้อง", Toast.LENGTH_SHORT).show();
//                    gotoRateScore(positioned);
                }else{
                    Toast.makeText(this, "ไม่ใช่ร้านที่รับซ่อม", Toast.LENGTH_SHORT).show();
                }
//                register = (Register) data.getSerializableExtra("result");
//                Toast.makeText(this, "Return "+register.getUsername(), Toast.LENGTH_SHORT).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    public void gotoRateScore(int position){
        Gson gson = new Gson();
        String obj = gson.toJson(replyBean);

        Bundle bundle = new Bundle();
        bundle.putString("reply", obj);

        PointScoreFragment one = new PointScoreFragment();
        one.setArguments(bundle);
        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
        transaction1.add(R.id.show_score_frragment, one);
        transaction1.addToBackStack(null);
        transaction1.commit();
    }

}
