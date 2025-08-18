package com.mala.digital_joper_mala.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mala.digital_joper_mala.Adapter.All_mantra;
import com.mala.digital_joper_mala.Database.Mantra;
import com.mala.digital_joper_mala.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Act_all_mantra extends AppCompatActivity {

    //XML id's------------------------------------------------------

    All_mantra mantra;

    private ListView lv_mantra;

    private ImageButton back;

    List<HashMap<String, String>> mapList = new ArrayList<>();

    HashMap<String, String> map;

    private Mantra my_mantra;

    private AppCompatImageView iv_alert_info;

    //XML id's------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_all_mantra);

        //identity period-----------------------------------
        lv_mantra = findViewById(R.id.lv_mantra);
        back = findViewById(R.id.back);
        iv_alert_info = findViewById(R.id.iv_alert_info);
        //identity period-----------------------------------

        mantra = new All_mantra(this, mapList);
        lv_mantra.setAdapter(mantra);
        mantras();

        /*
        my_mantra = new Mantra(this);

        my_mantra.insert("non", "Test");

        List<HashMap<String, String>> allData = my_mantra.get_All_data();

        if (allData == null || allData.isEmpty()){

            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
        }

        for (HashMap<String, String> hashMap: allData) {

            String title = hashMap.get("title");
            String mantra = hashMap.get("mantra");


            map = new HashMap<>();
            map.put("দেবতার নাম",title);
            map.put("জপ মন্ত্র",mantra);
            mapList.add(map);

        }

         */


        back.setOnClickListener(view -> {

            startActivity(new Intent(this, Act_Home_All_Mala.class));
            finishAffinity();

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



    }//on create===================================

    //all mantras----------------------------
    private void mantras(){

        map = new HashMap<>();
        map.put("দেবতার নাম","১। শিব মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ নমঃ শিবায়");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","২। কালভৈরব মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ ভৈরবায় নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","৩। হনুমান মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ হনুমতে নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","৪। মা দুর্গার মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ দুঁ দুর্গায়ৈ নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","৫। শ্রী সন্তোষী মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ সন্তোষী মায়ৈ নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","৬। অন্নপূর্ণা দেবীর মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ অন্নপূর্ণায়ৈ নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","৭। মা কালী মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ ক্রীং কালিকায়ৈ নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","৮। শ্রী সিদ্ধকালী মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ কালী কালী মহাকালী কালিকে স্বাহা");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","৯। তারা মা মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ তারা মায়ৈ নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","১০। শ্রী শীতলা মা মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ শীতলায়ৈ নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","১১।  ত্রিপুরসুন্দরী মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ হ্রীং শ্রিং ক্লীং কামরূপিণ্যৈ নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","১২। ভুবনেশ্বরী মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ হ্রীং ভুবনেশ্বর্যৈ নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","১৩। শ্রী গণেশ মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ গাঁ গনপতয়ে নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","১৪। শ্রী গজানন (গজলক্ষ্মী) মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ গজলক্ষ্ম্যৈ নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","১৫। শ্রী বিষ্ণু মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ নমো ভগবতে বাসুদেবায়");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","১৬। শ্রী নারায়ণ মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ নমো নারায়ণায়");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","১৭। শ্রী রাম মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ শ্রী রামায় নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","১৮। শ্রী কৃষ্ণ মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ শ্রী কৃষ্ণায় নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","১৯। শ্রী জগন্নাথ মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ জগন্নাথায় নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","২০। নৃসিংহ মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ ক্ষ্রাঁ নৃসিংহায় নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","২১। মা লক্ষ্মী মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ শ্রীং মহালক্ষ্ম্যৈ নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","২২। অষ্টলক্ষ্মীর মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ শ্রিং হ্রীং ক্রীং অষ্টলক্ষ্ম্যৈ নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","২৩। শ্রী রাধা মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ রাধায়ৈ নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","২৪। তুলসী দেবীর মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ তুলসৈ নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","২৫। মা সরস্বতী মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ আইং সরস্বত্যৈ নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","২৬। সূর্যদেবের মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ ঘৃণিঃ সূর্যায় নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","২৭। শনি দেবের মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ শং শনৈশ্চরায় নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","২৮। চন্দ্র দেবের মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ ঐং ক্লীং সোমায়ঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","২৯। মঙ্গল দেবের মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ হুং শ্রীং মঙ্গলায়ঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","৩০। বুধ দেবের মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ ঐং স্ত্রীং শ্রীং বুধায়ঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","৩১। বৃহস্পতি দেবের মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ হ্রীং ক্লীং হুং বৃহস্পতয়ে");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","৩২। শুক্র দেবের মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ হ্রীং শুক্রায়ঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","৩৩। রাহু মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ ঐং হ্রীং রাহবে");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","৩৪। কেতু মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ হ্রীং ঐং কেতবে");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","৩৫। কুবের দেবের মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ যক্ষায় কুবেরায় বৈশ্রবণায় ধনধান্যাদি পতয়ে কুবের দেবের নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","৩৬। ধনদা দেবী (কুবেরের সহচরী) মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ ধনদায়ৈ নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","৩৭। নাগদেবতা মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ নাগায় নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","৩৮। মা মনসার মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ হ্রীং শ্রীং ক্লিং মনসাদেব্যৈ স্বাহা");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","৩৯। শ্রী রামকৃষ্ণ মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ রামকৃষ্ণায় নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","৪০। শ্রী সারদা দেবীর মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ শ্রীমাতা শ্রী সারদা দেব্যৈ নমঃ");
        mapList.add(map);

    }

}//public class=====================================