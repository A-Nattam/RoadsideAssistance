package com.example.yokgoodchild.roadsideassistance.RateServiceScore;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.yokgoodchild.roadsideassistance.ClassBean.ApplicantBean;
import com.example.yokgoodchild.roadsideassistance.ClassBean.ReplyBean;
import com.example.yokgoodchild.roadsideassistance.R;
import com.google.gson.Gson;

import java.security.acl.Group;

/**
 * A simple {@link Fragment} subclass.
 */
public class PointScoreFragment extends Fragment {

    public static final String DATA_LOGIN = "DATA_LOGIN";
    public static final String LOGIN_DATA = "LOGIN_DATA";
    private ApplicantBean applicantBean;
    private RateServiceScoreManager rateServiceScoreManager;
    private Activity activity;
    private ViewGroup root;
    private ReplyBean replyBean;

    public PointScoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = getActivity();
        root = (ViewGroup)inflater.inflate(R.layout.fragment_point_score, container, false);

        SharedPreferences sp = activity.getSharedPreferences(DATA_LOGIN, Context.MODE_PRIVATE);
        String data_login = sp.getString(LOGIN_DATA, "Fail");
        if(data_login != "Fail"){
            applicantBean = new Gson().fromJson(data_login,ApplicantBean.class);
        }

        final String reply = getArguments().getString("reply");
        replyBean = new Gson().fromJson(reply,ReplyBean.class);

        RatingBar ratingBar = (RatingBar) root.findViewById(R.id.rate_score);

        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#ff9900"), PorterDuff.Mode.SRC_ATOP);

        ratingBar.setIsIndicator(false);
        ratingBar.setRating(2);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(activity, String.valueOf(rating), Toast.LENGTH_SHORT).show();
                Toast.makeText(activity, replyBean.getTitleReply(), Toast.LENGTH_SHORT).show();
                isupdateRateScoreService(replyBean,Double.parseDouble(String.valueOf(rating)));
                getActivity().getFragmentManager().popBackStack();
            }
        });

        return root;
    }

    public void isupdateRateScoreService(ReplyBean data,Double point){
        rateServiceScoreManager = new RateServiceScoreManager(PointScoreFragment.this);
        rateServiceScoreManager.setURL(getString(R.string.url));
        rateServiceScoreManager.isUpdateRateStatus(data.getReplyid(),point,data.getRequestid(),applicantBean.getCardID());
    }

}
