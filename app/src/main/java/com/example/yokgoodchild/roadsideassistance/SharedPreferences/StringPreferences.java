package com.example.yokgoodchild.roadsideassistance.SharedPreferences;

import android.content.SharedPreferences;

/**
 * Created by YokGoodChild on 6/1/2017.
 */

public class StringPreferences {
    private final SharedPreferences sharedPreferences;
    private final String key;

    public StringPreferences(SharedPreferences preferences, String key) {
        sharedPreferences = preferences;
        this.key = key;
    }

    public final boolean isSet() {
        return sharedPreferences.contains(key);
    }

    public final String get() {
        return sharedPreferences.getString(key,"");
    }

    public final void setValue(String value) {
        sharedPreferences.edit().putString(key,value).apply();
    }

    public final void delete() {
        sharedPreferences.edit().remove(key).apply();
    }
}
