package com.mala.digital_joper_mala.Activity;



import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;


import com.google.gson.Gson;
import com.mala.digital_joper_mala.Api.Request_link;
import com.mala.digital_joper_mala.Model.Api_links;
import com.mala.digital_joper_mala.Model.Data;
import com.mala.digital_joper_mala.Model.Main_response;
import com.mala.digital_joper_mala.R;
import com.mala.digital_joper_mala.Utils.ApiResponseListener;
import com.mala.digital_joper_mala.Utils.Request_limit;


import java.io.File;
import java.io.IOException;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;


public class Fg_Advantage_of_jopa extends Fragment {

    //XML id's--------------------------------------------

    ArrayList<HashMap<String ,String>> arrayList = new ArrayList<>();
    HashMap<String, String> hashMap;

    private ListView ad_jopa;
    private Myadapter myadapter;

    private BroadcastReceiver broadcastReceiver;

    private ProgressBar progressBar;

    private boolean isDataloaded = false;

    private Request_limit limit;


    //XML id's--------------------------------------------


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_advantage_of_jopa, container, false);

        //identity period--------------------------------------------

        ad_jopa = view.findViewById(R.id.ad_jopa);
        progressBar = view.findViewById(R.id.progressBar);

        //identity period--------------------------------------------

        setnetworkReceiver();

        myadapter = new Myadapter();
        ad_jopa.setAdapter(myadapter);


        limit = new Request_limit(getActivity());


        return view;
    }//on create===============================

    //checking internet -----------------------------------------------------

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (broadcastReceiver != null){
            requireContext().unregisterReceiver(broadcastReceiver);
        }
    }

    private boolean isInternetAvaiable(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (connectivityManager != null) {
            Network network = connectivityManager.getActiveNetwork();
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
            return capabilities != null &&
                    (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
        }

        return false;
    }

    private void loadData(Context context){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("ইন্টারনেট সংযোগ নেই!");
        builder.setMessage("ইন্টারনেট চালু করে আবার চেষ্টা করুন।");
        builder.setPositiveButton("বন্ধ করুন", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

            }
        });


        if (isInternetAvaiable(context)){


            if (!isDataloaded){

               // arrayList.clear();

                if (limit.canMakeRequest(getActivity())){

                    try {

                        Request_link link = new Request_link(new ApiResponseListener() {
                            @Override
                            public void onApiResponse(Api_links apiLinks) {

                                String url = apiLinks.getJopa_info();

                                data_form_server(url);

                            }

                            @Override
                            public void onApiFailed(String error) {

                            }
                        });
                        link.Apis();

                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }

                isDataloaded = true;

            }

        }else {

            progressBar.setVisibility(View.GONE);
            builder.show();
            isDataloaded = false;

        }

    }
    private void setnetworkReceiver(){

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                loadData(context);

            }
        };

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        requireContext().registerReceiver(broadcastReceiver,filter);

    }

    //checking internet -----------------------------------------------------

    //custom adapter-----------------------------------
    public class Myadapter extends BaseAdapter{


        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {

            LayoutInflater layoutInflater = getLayoutInflater();
            @SuppressLint("ViewHolder") View view1 = layoutInflater.inflate(R.layout.lay_design_of_listview, viewGroup, false);

            HashMap<String, String> hashMap1 = arrayList.get(position);

            AppCompatImageView imageview = view1.findViewById(R.id.imageview);
            LinearLayout ans_layout = view1.findViewById(R.id.ans_layout);
            LinearLayout clicked = view1.findViewById(R.id.clicked);
            AppCompatTextView ques_text = view1.findViewById(R.id.ques_text);
            AppCompatTextView tvDisplay = view1.findViewById(R.id.tvDisplay);


            String ques1 = hashMap1.get("ques");
            ques_text.setText(ques1);



            clicked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //code here-----

                    String ans1 = hashMap1.get("ans");

                    if (ans_layout.getVisibility() == View.GONE){


                        tvDisplay.setText(ans1);
                        //TransitionManager.beginDelayedTransition(mother_layout, new AutoTransition());
                        ans_layout.setVisibility(View.VISIBLE);
                        imageview.setImageResource(R.drawable.ic_up);

                    }else {


                        //TransitionManager.beginDelayedTransition(mother_layout, new AutoTransition());
                        ans_layout.setVisibility(View.GONE);
                        imageview.setImageResource(R.drawable.ic_down);

                    }

                }
            });

            return view1;
        }
    }
    //custom adapter-----------------------------------

    private void data_form_server(String url){

        //cache data--------------------------------------
        File cacheDr = new File(getContext().getCacheDir(),"data");
        int cacheSize = 10 * 1024 * 1024; //10MB
        Cache cache = new Cache(cacheDr, cacheSize);

        //cache data--------------------------------------

        //unique device id
        String device_id = UUID.randomUUID().toString();

        Gson gson = new Gson();

        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    okhttp3.Response response = chain.proceed(chain.request());
                    response = response.newBuilder()
                            .header("Cache-Control", "public, max-age=600") // 10 মিনিট ক্যাশ রাখবে
                            .build();
                    return response;
                })
                .build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .header("X-UUID", device_id)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

               new Handler(Looper.getMainLooper()).post(() -> {

                   progressBar.setVisibility(View.VISIBLE);

               });
                e.printStackTrace();

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull okhttp3.Response response) throws IOException {

                if (response.isSuccessful() && response.body() != null){
                    
                    String jopa_info = response.body().string();

                    try {

                        Main_response mainResponse = gson.fromJson(jopa_info, Main_response.class);

                        List<Data> data = mainResponse.getData();

                        arrayList.clear();

                        for (int i = 0; i<data.size(); i++){

                           Data data1 = data.get(i);

                            hashMap = new HashMap<>();
                            hashMap.put("ques",data1.getQuestion());
                            hashMap.put("ans",data1.getAnswer());
                            arrayList.add(hashMap);

                        }
                       new Handler(Looper.getMainLooper()).post(() -> {

                           progressBar.setVisibility(View.GONE);
                           myadapter.notifyDataSetChanged();
                       });



                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }


            }
        });


    }

}//public class===============================