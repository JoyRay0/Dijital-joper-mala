package com.mala.digital_joper_mala.Api;

import android.content.Context;

import okhttp3.OkHttpClient;

public class POST_api {

    private Context context;
    private String endPointLink;
    private OkHttpClient client;

    public POST_api(Context context, String endPointLink) {
        this.context = context;
        this.endPointLink = endPointLink;
        this.client = new OkHttpClient();
    }
}
