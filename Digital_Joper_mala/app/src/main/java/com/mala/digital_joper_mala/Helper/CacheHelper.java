package com.mala.digital_joper_mala.Helper;

import android.content.Context;
import android.content.SharedPreferences;

public class CacheHelper {

    private Context context;
    private String preference;
    private SharedPreferences sp;

    public CacheHelper(Context context, String preference) {
        this.context = context;
        this.preference = preference;
    }

    public void setCache(String key, String value){

        sp = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();

    }

    public String getCache(String key){

        sp = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        String values = sp.getString(key, "");

        return values;
    }

    public void deleteCache(String key){

        sp = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        sp.edit().remove(key).apply();

    }

}
