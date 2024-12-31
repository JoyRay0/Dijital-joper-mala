package com.mala.digital_joper_mala;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){

            other_page();

        }else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    other_page();
                }
            },2000);

        }


    }
    private void other_page(){

        startActivity(new Intent(SplashScreenActivity.this, HomeAllMalaActivity.class));
        finishAffinity();

    }
}