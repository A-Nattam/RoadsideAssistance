package com.example.yokgoodchild.roadsideassistance.RequestForHelp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yokgoodchild.roadsideassistance.ClassBean.ApplicantBean;
import com.example.yokgoodchild.roadsideassistance.ClassBean.RequestBean;
import com.example.yokgoodchild.roadsideassistance.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class RequestForHelpActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final String DATA_LOGIN = "DATA_LOGIN";
    public static final String LOGIN_DATA = "LOGIN_DATA";
    public static final int REQUEST_GALLERY = 1;

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;


    private ImageView img ;
    private EditText title;
    private EditText detail;
    private TextView name;
    private Spinner dropdown;

    private RequestBean requestBean;
    private ApplicantBean applicantBean;

    private Bitmap bmp;
    private Bitmap testBit ;
    private String latitude;
    private String longtitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_for_help);

        int baseColor = ContextCompat.getColor(this, R.color.colorToolbar_text);
        Toolbar toolbar = (Toolbar) findViewById(R.id.ac_request_toolbar);
        toolbar.setTitle("Request For Help");
        toolbar.setTitleTextColor(baseColor);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }

        SharedPreferences sp = getSharedPreferences(DATA_LOGIN, Context.MODE_PRIVATE);
        String data_login = sp.getString(LOGIN_DATA, "Fail");
        if(data_login != "Fail"){
            applicantBean = new Gson().fromJson(data_login,ApplicantBean.class);
        }

        title = (EditText) findViewById(R.id.fm_request_for_help_txt_title);
        detail = (EditText) findViewById(R.id.fm_request_for_help_txt_detail);
        name = (TextView) findViewById(R.id.fm_request_for_help_txt_full_name);
        img = (ImageView) findViewById(R.id.fm_request_for_help_imgViewShow);
        dropdown = (Spinner) findViewById(R.id.fm_request_for_help_spinner_type_car);

        name.setText(applicantBean.getTitle()+" "+applicantBean.getName()+" "+applicantBean.getLastname());

        String[] items = new String[]{"Car", "Motorcycle", "truck"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

    }

    public void isUploadImage(View v){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            Toast.makeText(this, "Latitude "+String.valueOf(mLastLocation.getLatitude())+" \n" +String.valueOf(mLastLocation.getLongitude()), Toast.LENGTH_SHORT).show();
            latitude = String.valueOf(mLastLocation.getLatitude());
            longtitude = String.valueOf(mLastLocation.getLongitude());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityResult(int reqCode, int resCode, Intent data) {
        if (reqCode == REQUEST_GALLERY && resCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                int sWidth = bmp.getWidth();
                int sHeight = bmp.getHeight();

                if(sWidth > 2000){
                    sWidth = 1200;
                    sHeight = 700;
                }

                if(sHeight > 2000){
                    sWidth = 1080;
                    sHeight = 1920;
                }

                testBit = Bitmap.createScaledBitmap(bmp, sWidth,sHeight,false); //set size image

                img.setImageBitmap(testBit);
                img.setTransitionName("pic_001");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void onClickSubmitRequest(View v){
        RequestForHelpManager request_manager = new RequestForHelpManager();
        boolean check_input = checkRequestDataScript();

        if(check_input){
            requestBean = new RequestBean();
            requestBean.setTitleDamage(title.getText().toString());
            requestBean.setName(name.getText().toString());
            requestBean.setDamageDetail(detail.getText().toString());
            requestBean.setCarType(String.valueOf(dropdown.getSelectedItemPosition()));
            requestBean.getApplicant().setCardID(applicantBean.getCardID());
            requestBean.setPhoto(getStringImage(testBit));
            requestBean.setLatitude(latitude);
            requestBean.setLongitude(longtitude);

//            Toast.makeText(this, "CardID "+requestBean.getApplicant().getCardID(), Toast.LENGTH_SHORT).show();

            String Data_Request = new Gson().toJson(requestBean);
            String url = getString(R.string.urlservice)+"JSONUploadImageServlet";
            request_manager.isAddRequestData(this,url,Data_Request);

            finish();
        }

    }

    public boolean checkRequestDataScript(){
        boolean check_input = false;
        if(title.getText().toString().matches("")){
            isFocus(title);
            Toast.makeText(this, "กรุณา กรอกหัวข้อ", Toast.LENGTH_SHORT).show();
        }else if(detail.getText().toString().matches("")){
            isFocus(detail);
            Toast.makeText(this, "กรุณา กรอกรายละเอียด", Toast.LENGTH_SHORT).show();
        }else if(img.getDrawable() == null){
            Toast.makeText(this, "กรุณา เลือกรูปภาพ", Toast.LENGTH_SHORT).show();
        }else{
            check_input = true;
        }
        return check_input;
    }

    public void isFocus(EditText txtFocus){
        txtFocus.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(txtFocus, InputMethodManager.SHOW_IMPLICIT);
    }

}
