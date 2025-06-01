package com.mala.digital_joper_mala.Api;

import android.content.Context;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class GET_api {

    private String link;
    private OkHttpClient client;


    public GET_api( String link) {
        this.link = link;
        this.client = new OkHttpClient();
    }

   public void call_Api(Callback callback){

       Request request = new Request.Builder().url(link).build();

       client.newCall(request).enqueue(callback);

   }
}
