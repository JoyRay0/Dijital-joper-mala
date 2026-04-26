package com.mala.digital_joper_mala.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.mala.digital_joper_mala.R;

public class Act_boisnob_mala_helper extends AppCompatActivity {

    //XML id's------------------------------------------------------------

    ImageButton back;

    //XML id's------------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_boisnob_mala_rules);

        //identity period----------------------------------------------------------

        back = findViewById(R.id.back);

        //identity period----------------------------------------------------------


        //---------------------------------------------------------------------

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        //---------------------------------------------------------------------


        
    }//on create=========================

}//public class===========================