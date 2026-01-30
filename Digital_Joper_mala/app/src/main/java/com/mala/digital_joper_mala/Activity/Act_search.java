package com.mala.digital_joper_mala.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mala.digital_joper_mala.Adapter.Search;
import com.mala.digital_joper_mala.Database.Mantra;
import com.mala.digital_joper_mala.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Act_search extends AppCompatActivity {

    //XML id's-----------------------------------------------------

    //toolbar
    private ImageButton back;

    //in
    private RecyclerView rv_item;
    private AppCompatEditText ed_search;
    private LinearLayout ll_no_item;

    private List<HashMap<String, String>> s_list = new ArrayList<>();
    private HashMap<String, String> s_map;

    //other
    private Mantra mantra;
    private Search searchAdapter;

    //XML id's-----------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_search);

        init();

        search();

        back.setOnClickListener(view -> {

            finish();

        });


    }// on create==============================================================

    private void init(){

        back = findViewById(R.id.back);
        rv_item = findViewById(R.id.rv_item);
        ed_search = findViewById(R.id.ed_search);
        ll_no_item = findViewById(R.id.ll_no_item);


        mantra = new Mantra(this);

        searchAdapter = new Search(this, s_list);
        rv_item.setAdapter(searchAdapter);

    }

    //search from database------------------------------------------------------------
    private void search(){

        ed_search.requestFocus();

        ed_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (i == EditorInfo.IME_ACTION_SEARCH){

                    InputMethodManager manager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                    rv_item.setVisibility(View.VISIBLE);
                    ll_no_item.setVisibility(View.GONE);

                    if (manager != null){
                        manager.hideSoftInputFromWindow(textView.getWindowToken(),0 );
                    }

                    String search = ed_search.getText().toString().trim();

                    if (search == null || search.isEmpty()){

                        ed_search.setError("কোন নাম নেই!");

                    }else {

                        s_list.clear();
                        s_list.addAll(mantra.getSearchItem(search));
                        searchAdapter.notifyDataSetChanged();

                        if (s_list.isEmpty()){

                            rv_item.setVisibility(View.GONE);
                            ll_no_item.setVisibility(View.VISIBLE);
                            Toast.makeText(Act_search.this, "পাওয়া যায়নি।", Toast.LENGTH_SHORT).show();

                        }

                    }

                    return true;
                }

                return false;
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mantra.closeDB();
    }
}//public class===============================================================