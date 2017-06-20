package com.example.yokgoodchild.roadsideassistance.RateServiceScore;

import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by YokGoodChild on 6/18/2017.
 */

public class RateServiceScoreManager {

    PointScoreFragment activity;
    String url;

    public void setURL(String Url){
        url = "http://"+Url+":8086/Mini/RateServiceScoreServlet";
    }

    public RateServiceScoreManager(PointScoreFragment tvolley){
        this.activity = tvolley;
    }

    public void isUpdateRateStatus(int replyID, Double scores, int requestID, String cardID){
        final int replyid = replyID ;
        final Double score = scores ;
        final int requestid = requestID;
        final String cardid = cardID;

        RequestQueue requestQueue = Volley.newRequestQueue(activity.getActivity());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(activity.getActivity(), "Data "+response.toString(), Toast.LENGTH_SHORT).show();
                try {


                }catch (Exception e){
                    Toast.makeText(activity.getActivity(), "Error dataListRequest", Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // do something...
                Toast.makeText(activity.getActivity(), "Error List Request Volley", Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,  String> headers = new HashMap<>();

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("replyID", replyid);
                    jsonObject.put("score", String.valueOf(score));
                    jsonObject.put("requestID",requestid);
                    jsonObject.put("cardID",cardid);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                headers.put("Content-Type", "application/json");
                headers.put("dataRateScore",jsonObject.toString());

                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

}
