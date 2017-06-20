package com.example.yokgoodchild.roadsideassistance.ReplyHelpRequest;

import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.yokgoodchild.roadsideassistance.ClassBean.ReplyBean;
import com.example.yokgoodchild.roadsideassistance.ClassBean.RequestBean;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by YokGoodChild on 6/13/2017.
 */

public class ReplyHelpRequestManager {

    private ReplyHelpRequestActivity activity;

    private String url;

    public void setURL(String Url){
        url = "http://"+Url+":8086/Mini/ReplyHelpRequestServlet";
    }

    public ReplyHelpRequestManager(ReplyHelpRequestActivity tvolley){
        this.activity = tvolley;
    }

    public void isInsertReply(ReplyBean replyBean){

        final ReplyBean reply = replyBean;

        RequestQueue requestQueue = Volley.newRequestQueue(activity);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override //Set Methods SerVlet get or Post form URL
            public void onResponse(JSONObject response) { //เมธอท อ่านค่าจากเว็บ

                Toast.makeText(activity, "Hello "+response.toString(), Toast.LENGTH_SHORT).show();
                try {   //เรียกใช้เมธอด อ่าน JSON
                    /*dataApplicant(response.toString());
                    getDataLogin(response.toString());*/

                    //callService.onGetLogin(response.toString());
                }catch (Exception e){
                    Toast.makeText(activity, "Error data", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                /*callService.onGetLogin(response.toString());*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // do something...

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError { //เมธอท ส่งค่าไปเว็บ service
                Map<String,  String> headers = new HashMap<>();

                Gson gson = new Gson();
                String json = gson.toJson(reply);

                headers.put("Content-Type", "application/json");
                headers.put("data",json );

                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);

    }

}
