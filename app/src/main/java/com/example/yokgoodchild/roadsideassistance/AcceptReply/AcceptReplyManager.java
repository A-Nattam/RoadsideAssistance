package com.example.yokgoodchild.roadsideassistance.AcceptReply;

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
 * Created by YokGoodChild on 6/5/2017.
 */

public class AcceptReplyManager {
    private AcceptReplyActivity activity;
    String url;

    public void setURL(String Url){
        url = "http://"+Url+":8086/Mini/AcceptReplyServlet";
    }

    public AcceptReplyManager(AcceptReplyActivity tvolley){ this.activity = tvolley; }

    public void getReplyDetail(int replyID, String Status, int requestID, String registrationID){
        final int replyid = replyID ;
        final String status = Status ;
        final int requestid = requestID;
        final String registrationid = registrationID;

        RequestQueue requestQueue = Volley.newRequestQueue(activity);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(activity, "Data "+response.toString(), Toast.LENGTH_SHORT).show();
                try {


                }catch (Exception e){
                    Toast.makeText(activity, "Error dataListRequest", Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // do something...
                Toast.makeText(activity, "Error List Request Volley", Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,  String> headers = new HashMap<>();

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("replyID", replyid);
                    jsonObject.put("status", status);
                    jsonObject.put("requestID",requestid);
                    jsonObject.put("registrationID",registrationid);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                headers.put("Content-Type", "application/json");
                headers.put("dataReply",jsonObject.toString());

                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

}
