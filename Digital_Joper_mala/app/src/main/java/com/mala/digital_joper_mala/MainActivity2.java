package com.mala.digital_joper_mala;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.Intent;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.navigation.NavigationBarMenu;
import com.google.android.material.ripple.RippleUtils;

public class MainActivity2 extends AppCompatActivity {

    //XML id's

    ImageButton back;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //identity period

        back = findViewById(R.id.back);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                Intent myback = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(myback);

            }
        });

        
    }
}