package com.example.yokgoodchild.roadsideassistance.SplashScreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.yokgoodchild.roadsideassistance.R;

public class SplashScreen extends AppCompatActivity {

    private Handler handler;
    private Runnable runnable;
    private long delay_time;
    private long time = 3000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        handler = new Handler();

        SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();
        final boolean chkRunApp = sp.getBoolean("RUN_FIRST_APP", false);

        runnable = new Runnable() {
            public void run() {
                if (chkRunApp == true) {
                    Toast.makeText(SplashScreen.this, "true", Toast.LENGTH_SHORT).show();

//                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                    Intent intent = new Intent(SplashScreen.this, OnBoard.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(SplashScreen.this, "false", Toast.LENGTH_SHORT).show();

                    editor.putBoolean("RUN_FIRST_APP", true);
                    editor.commit();

                    Intent intent = new Intent(SplashScreen.this, OnBoard.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }

    public void onResume() {
        super.onResume();

        delay_time = time;
        handler.postDelayed(runnable, delay_time);
        time = System.currentTimeMillis();
    }

    public void onPause() {
        super.onPause();

        handler.removeCallbacks(runnable);
        time = delay_time - (System.currentTimeMillis() - time);
    }
}
