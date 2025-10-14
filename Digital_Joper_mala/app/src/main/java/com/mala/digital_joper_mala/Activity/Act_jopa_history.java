package com.mala.digital_joper_mala.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mala.digital_joper_mala.Adapter.JopaHistory;
import com.mala.digital_joper_mala.Database.History;
import com.mala.digital_joper_mala.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Act_jopa_history extends AppCompatActivity {

    //XML id's--------------------------------------------------------

    //toolbar
    private ImageButton back;

    private RecyclerView rv_history;

    private AppCompatTextView tv_text;

    private AppCompatImageView iv_delete;

    private ArrayList<HashMap<String, String>> h_list = new ArrayList<>();
    private HashMap<String, String> h_map;

    //other
    private JopaHistory history;
    
    private History historyDB;

    //XML id's--------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_jopa_history);

        //identity period--------------------------------------------------

        back = findViewById(R.id.back);
        rv_history = findViewById(R.id.rv_history);
        tv_text = findViewById(R.id.tv_text);
        iv_delete = findViewById(R.id.iv_delete);

        //identity period--------------------------------------------------

        history = new JopaHistory(this, h_list);
        rv_history.setAdapter(history);
        
        historyDB = new History(this);


        back.setOnClickListener(view -> {

            startActivity(new Intent(Act_jopa_history.this, Act_Home_All_Mala.class));
            finishAffinity();

        });

        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                startActivity(new Intent(Act_jopa_history.this, Act_Home_All_Mala.class));
                finishAffinity();

            }
        });

         //test();
         database();


    }// on create============================================================

    //test map-------------------------------------------------------------
    private void test(){

        h_map = new HashMap<>();
        h_map.put("title", "joy");
        h_map.put("count", "10");
        h_list.add(h_map);

        h_map = new HashMap<>();
        h_map.put("title", "roy");
        h_map.put("count", "15");
        h_list.add(h_map);

        h_map = new HashMap<>();
        h_map.put("title", "mridul");
        h_map.put("count", "30");
        h_list.add(h_map);

    }

    //database insert and delete all--------------------------------------------------
    private void database(){

        List<HashMap<String, String>> list = historyDB.getAll();
        h_list.clear();
        
        if (list == null || list.isEmpty()){

            tv_text.setVisibility(View.VISIBLE);
            rv_history.setVisibility(View.GONE);
            
            
        }else {

            h_list.addAll(list);
            
            history.notifyDataSetChanged();
            
            tv_text.setVisibility(View.GONE);
            rv_history.setVisibility(View.VISIBLE);
            
        }

        iv_delete.setOnClickListener(view -> {

            historyDB.DeleteAll();
            h_list.clear();
            history.notifyDataSetChanged();

            Toast.makeText(this, "সব ডিলিট হয়েছে।", Toast.LENGTH_SHORT).show();

        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        historyDB.CloseDB();
    }
}// public class==============================================================