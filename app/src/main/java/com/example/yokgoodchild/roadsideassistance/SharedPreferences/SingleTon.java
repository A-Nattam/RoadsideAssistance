package com.example.yokgoodchild.roadsideassistance.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SingleTon {

    private SharedPreferences preferences;

    private static SingleTon singleTon;

    public static SingleTon getInstance() {

        if (singleTon == null) {
            singleTon = new SingleTon();
        }

        return singleTon;

    }

    public void setContext(Context context) {
        preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
    }


}