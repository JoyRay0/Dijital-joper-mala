package com.mala.digital_joper_mala;



import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.jar.JarException;


public class Advantage_of_jopa extends Fragment {

    //XML id's--------------------------------------------

    ArrayList<HashMap<String ,String>> arrayList = new ArrayList<>();
    HashMap<String, String> hashMap;

    private ListView ad_jopa;
    private Myadapter myadapter;

    private BroadcastReceiver broadcastReceiver;



    private boolean isDataloaded = false;



    //XML id's--------------------------------------------


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advantage_of_jopa, container, false);

        //identity period--------------------------------------------

        ad_jopa = view.findViewById(R.id.ad_jopa);




        //identity period--------------------------------------------

        setnetworkReceiver();

        myadapter = new Myadapter();
        ad_jopa.setAdapter(myadapter);




        return view;
    }//on create===============================

    //data from server----------------------------------------------------
    private void data_from_server(String url){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try {

                            for (int x = 0; x <response.length(); x++){

                                JSONObject jsonObject = response.optJSONObject(x);

                                String question = jsonObject.getString("question");
                                String answer = jsonObject.getString("answer");

                                hashMap = new HashMap<>();
                                hashMap.put("ques",question);
                                hashMap.put("ans",answer);
                                arrayList.add(hashMap);

                            }

                            myadapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                });

        // Access the RequestQueue through your singleton class.
        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
        requestQueue.add(jsonArrayRequest);

    }
    //data from server----------------------------------------------------

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
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

    private void loadData(Context context){

        if (isInternetAvaiable(context)){

            if (!isDataloaded){

                data_from_server("https://rksoftwares.xyz/jopa_mala/Jopa_info.php");
                isDataloaded = true;
            }

        }else {


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
            @SuppressLint("ViewHolder") View view1 = layoutInflater.inflate(R.layout.design_of_listview, viewGroup, false);

            HashMap<String, String> hashMap1 = arrayList.get(position);

            AppCompatImageView imageview = view1.findViewById(R.id.imageview);
            CardView ans_cardview = view1.findViewById(R.id.ans_cardview);
            CardView clicked = view1.findViewById(R.id.clicked);
            AppCompatTextView ques_text = view1.findViewById(R.id.ques_text);
            AppCompatTextView tvDisplay = view1.findViewById(R.id.tvDisplay);


            String ques1 = hashMap1.get("ques");
            ques_text.setText(ques1);



            clicked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //code here-----

                    String ans1 = hashMap1.get("ans");

                    if (ans_cardview.getVisibility() == View.GONE){


                        tvDisplay.setText(ans1);
                        //TransitionManager.beginDelayedTransition(mother_layout, new AutoTransition());
                        ans_cardview.setVisibility(View.VISIBLE);
                        imageview.setImageResource(R.drawable.baseline_keyboard_arrow_up_24);

                    }else {


                        //TransitionManager.beginDelayedTransition(mother_layout, new AutoTransition());
                        ans_cardview.setVisibility(View.GONE);
                        imageview.setImageResource(R.drawable.baseline_keyboard_arrow_down_24);

                    }

                }
            });

            return view1;
        }
    }
    //custom adapter-----------------------------------


}//public class===============================