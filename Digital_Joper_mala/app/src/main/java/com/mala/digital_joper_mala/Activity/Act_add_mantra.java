package com.mala.digital_joper_mala.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mala.digital_joper_mala.Adapter.User_mantra;
import com.mala.digital_joper_mala.Database.Database_helper;
import com.mala.digital_joper_mala.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Act_add_mantra extends AppCompatActivity {

    //XML id's------------------------------------------------

    private ImageButton back;
    private RecyclerView rv_item;
    private User_mantra mantra;
    private AppCompatTextView tv_text;
    private AppCompatImageView iv_add;

    private List<HashMap<String, String>> list_show = new ArrayList<>();
    private HashMap<String, String> map_show;
    private Database_helper helper;



    //XML id's------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_mantra);

        //identity period----------------------------------------

        back = findViewById(R.id.back);
        rv_item = findViewById(R.id.rv_item);
        tv_text = findViewById(R.id.tv_text);
        iv_add = findViewById(R.id.iv_add);

        //identity period----------------------------------------

        helper = new Database_helper(this);

        back.setOnClickListener(view -> {

            startActivity(new Intent(this, Act_Home_All_Mala.class));
            finishAffinity();

        });

        mantra = new User_mantra(this, list_show);
        rv_item.setAdapter(mantra);

        showData();

        if (list_show.isEmpty() || list_show == null){

            tv_text.setVisibility(View.VISIBLE);

        }else {


            tv_text.setVisibility(View.GONE);


        }

        iv_add.setOnClickListener(view -> {

            userInput();

        });



    }//on create====================

    //user input-------------------------------------------
    private void userInput(){
        
        Dialog dialog = new Dialog(Act_add_mantra.this);
        dialog.setContentView(R.layout.lay_user_input_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        AppCompatAutoCompleteTextView atv_name = dialog.findViewById(R.id.atv_name);
        AppCompatAutoCompleteTextView atv_mantra = dialog.findViewById(R.id.atv_mantra);
        AppCompatImageView iv_clear_name = dialog.findViewById(R.id.iv_clear_name);
        AppCompatImageView iv_clear_mantra = dialog.findViewById(R.id.iv_clear_mantra);
        AppCompatTextView tv_none = dialog.findViewById(R.id.tv_none);
        AppCompatTextView tv_add = dialog.findViewById(R.id.tv_add);

        tv_none.setOnClickListener(view -> {

            dialog.dismiss();

        });

        iv_clear_name.setOnClickListener(view -> {

            atv_name.setText("");

        });

        iv_clear_mantra.setOnClickListener(view -> {

            atv_mantra.setText("");

        });

        tv_add.setOnClickListener(view -> {


            String ed_name = atv_name.getText().toString().trim();
            String ed_mantra = atv_mantra.getText().toString().trim();
            
            if (ed_name == null || ed_name.isEmpty()){

                Toast.makeText(this, "অনুগ্রহ করে নাম প্রদান করুন", Toast.LENGTH_SHORT).show();
                
            } else if (ed_mantra == null || ed_mantra.isEmpty()) {

                Toast.makeText(this, "অনুগ্রহ করে মন্ত্র প্রদান করুন", Toast.LENGTH_SHORT).show();

            }else {


                helper = new Database_helper(this);

                helper.insert(ed_name, ed_mantra);

                list_show.clear();
                list_show.addAll(helper.getData());
                mantra.notifyDataSetChanged();

                tv_text.setVisibility(View.GONE);

                dialog.dismiss();
            }

        });

        dialog.show();

    }

    //show inserted data----------------------------------------------------
    private void showData(){

        helper = new Database_helper(this);

        List<HashMap<String, String>> list = helper.getData();

        for (HashMap<String, String> map : list) {

            String title = map.get("title");
            String mantra = map.get("mantra");

            map_show = new HashMap<>();
            map_show.put("title", title);
            map_show.put("mantra", mantra);
            list_show.add(map_show);

        }

    }

    //back--------------------------------------
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(Act_add_mantra.this, Act_Home_All_Mala.class));
        finishAffinity();

    }
}//public class========================