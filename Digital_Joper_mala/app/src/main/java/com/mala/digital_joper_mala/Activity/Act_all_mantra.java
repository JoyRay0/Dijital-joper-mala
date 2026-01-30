package com.mala.digital_joper_mala.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.mala.digital_joper_mala.Adapter.Mantras;
import com.mala.digital_joper_mala.Api.Request_link;
import com.mala.digital_joper_mala.Database.Mantra;
import com.mala.digital_joper_mala.Model.Api_links;
import com.mala.digital_joper_mala.Model.Data;
import com.mala.digital_joper_mala.Model.Main_response;
import com.mala.digital_joper_mala.R;
import com.mala.digital_joper_mala.Utils.ApiResponseListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Act_all_mantra extends AppCompatActivity {

    //XML id's-----------2-------------------------------------------

   private LinearLayout ll_no_mantra;

    private Mantras mantrasAdapter;

    private RecyclerView rv_mantra;

    private ImageButton back;

    private List<HashMap<String, String>> mapList = new ArrayList<>();

    private HashMap<String, String> map;

    private Mantra my_mantraDB;


    private AppCompatImageView iv_alert_info, iv_refresh, iv_search;

    //XML id's------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_all_mantra);

        init();

        List<HashMap<String, String>> allData = my_mantraDB.get_All_data();
        mapList.clear();

        if (allData == null || allData.isEmpty()){

            ll_no_mantra.setVisibility(View.VISIBLE);
            rv_mantra.setVisibility(View.GONE);

        }else {

            mapList.addAll(allData);

            rv_mantra.setVisibility(View.VISIBLE);
            ll_no_mantra.setVisibility(View.GONE);

            mantrasAdapter.notifyDataSetChanged();

        }

        refresh();

        back.setOnClickListener(view -> {

            finish();

        });

        iv_alert_info.setOnClickListener(view -> {

            Dialog dialog = new Dialog(Act_all_mantra.this);
            dialog.setContentView(R.layout.lay_info_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

            TextView tv_title = dialog.findViewById(R.id.tv_title);
            TextView tv_message = dialog.findViewById(R.id.tv_message);

            tv_title.setText("⚠\uFE0F সতর্কবার্তা");
            tv_message.setText(getText(R.string.jap_alert));

            dialog.show();

        });


        iv_search.setOnClickListener(view -> {

            startActivity(new Intent(this, Act_search.class));

        });


    }//on create====================================================================

    private void init(){

        back = findViewById(R.id.back);
        rv_mantra = findViewById(R.id.rv_mantra);
        iv_alert_info = findViewById(R.id.iv_alert_info);
        iv_refresh = findViewById(R.id.iv_refresh);
        ll_no_mantra = findViewById(R.id.ll_no_mantra);
        iv_search = findViewById(R.id.iv_search);

        mantrasAdapter = new Mantras(this, mapList);
        rv_mantra.setAdapter(mantrasAdapter);


        my_mantraDB = new Mantra(this);

    }

    //link --------------------------------------------------------------------------
    private void link(){

        try {

            Request_link link = new Request_link(new ApiResponseListener() {
                @Override
                public void onApiResponse(Api_links apiLinks) {

                    String url = apiLinks.getMantra();

                    mantra_from_server(url);

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

    //refresh---------------------------------------------------------------------------
    private void refresh(){

        iv_refresh.setOnClickListener(view -> {

            link();

        });

    }

    //data from sever------------------------------------------------------------------
    private void mantra_from_server(String url){

        Gson gson = new Gson();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {



            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.isSuccessful() && response.body() != null){

                    String mantra = response.body().string();

                    try {

                        Main_response mainResponse = gson.fromJson(mantra, Main_response.class);

                        List<Data> data = mainResponse.getData();

                        my_mantraDB.DeleteAll();

                        for ( Data data1 : data) {

                            my_mantraDB.insert(data1.getTitle(), data1.getMantra());

                        }

                        new Handler(Looper.getMainLooper()).post(() -> {

                            mapList.clear();
                            mapList.addAll(my_mantraDB.get_All_data());
                            mantrasAdapter.notifyDataSetChanged();

                            if (mapList.isEmpty()){

                                ll_no_mantra.setVisibility(View.VISIBLE);
                                rv_mantra.setVisibility(View.GONE);

                            }else {

                                rv_mantra.setVisibility(View.VISIBLE);
                                ll_no_mantra.setVisibility(View.GONE);

                            }

                        });


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        my_mantraDB.closeDB();
    }
}//public class=====================================