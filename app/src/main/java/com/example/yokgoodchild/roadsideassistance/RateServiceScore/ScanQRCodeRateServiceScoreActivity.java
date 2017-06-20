package com.example.yokgoodchild.roadsideassistance.RateServiceScore;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yokgoodchild.roadsideassistance.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class ScanQRCodeRateServiceScoreActivity extends AppCompatActivity {

    private SurfaceView cameraPreview;
    private TextView txtResult;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private final int RequestCameraPermissionID = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrcode_rate_service_score);

        int baseColor = ContextCompat.getColor(this, R.color.colorToolbar_text);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_rate_service_score_toolbar);
        toolbar.setTitle("Accept Reply");
        toolbar.setTitleTextColor(baseColor);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cameraPreview = (SurfaceView) findViewById(R.id.main_rate_service_score_cameraPreview);
        txtResult = (TextView) findViewById(R.id.main_rate_service_score_txt_Result);
        Button cancel = (Button) findViewById(R.id.main_rate_service_score_btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        barcodeDetector = new BarcodeDetector.Builder(ScanQRCodeRateServiceScoreActivity.this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        cameraSource = new CameraSource
                .Builder(ScanQRCodeRateServiceScoreActivity.this, barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build();

        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                if (ActivityCompat.checkSelfPermission(ScanQRCodeRateServiceScoreActivity.this.getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //Request permission
                    ActivityCompat.requestPermissions(ScanQRCodeRateServiceScoreActivity.this,
                            new String[]{Manifest.permission.CAMERA},RequestCameraPermissionID);
                    return;
                }
                try {
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcodes = detections.getDetectedItems();
                if(qrcodes.size() != 0){
                    txtResult.post(new Runnable() {
                        @Override
                        public void run() {
                            Vibrator vibrator = (Vibrator)ScanQRCodeRateServiceScoreActivity.this.getApplication().getSystemService(Context.VIBRATOR_SERVICE);
                            txtResult.setText(qrcodes.valueAt(0).displayValue);
                            if(txtResult.getText().toString()!= null){
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("RepairShopID",txtResult.getText().toString());
                                setResult(Activity.RESULT_OK,returnIntent);
                                finish();
                            }
                        }
                    });
                }
            }
        });

    }
}
