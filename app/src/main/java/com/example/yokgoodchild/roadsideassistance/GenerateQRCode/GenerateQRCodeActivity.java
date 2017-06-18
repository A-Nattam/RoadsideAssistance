package com.example.yokgoodchild.roadsideassistance.GenerateQRCode;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.yokgoodchild.roadsideassistance.ClassBean.RepairShopBean;
import com.example.yokgoodchild.roadsideassistance.R;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class GenerateQRCodeActivity extends AppCompatActivity {

    public static final String DATA_LOGIN = "DATA_LOGIN";
    public static final String LOGIN_DATA = "LOGIN_DATA";
    private RepairShopBean repairShopBean;
    private ImageView image;
    private String text2Qr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qrcode);

        SharedPreferences sp = getSharedPreferences(DATA_LOGIN, Context.MODE_PRIVATE);
        String data_login = sp.getString(LOGIN_DATA, "Fail");
        if(data_login != "Fail"){
            repairShopBean = new Gson().fromJson(data_login,RepairShopBean.class);
        }

        image = (ImageView) findViewById(R.id.generation_qr_code_img_qr_code);
        text2Qr = repairShopBean.getRegistrationID();
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            image.setImageBitmap(bitmap);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
