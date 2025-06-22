package com.mala.digital_joper_mala.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
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

    //XML id's------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_all_mantra);

        //identity period-----------------------------------
        lv_mantra = findViewById(R.id.lv_mantra);
        back = findViewById(R.id.back);
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



    }//on create===================================

    //all mantras----------------------------
    private void mantras(){

        map = new HashMap<>();
        map.put("দেবতার নাম","১। শ্রী গণেশ মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ গাঁ গনপতয়ে নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","২। মা দুর্গার মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ দুঁ দুর্গায়ৈ নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","৩। শিব মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ নমঃ শিবায়");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","৪। শ্রী বিষ্ণু মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ নমো ভগবতে বাসুদেবায়");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","৫। শ্রী রাম মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ শ্রী রামায় নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","৬। হনুমান মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ হনুমতে নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","৭। শ্রী নারায়ণ মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ নমো নারায়ণায়");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","৮। মা লক্ষ্মী মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ শ্রীং মহালক্ষ্ম্যৈ নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","৯। মা সরস্বতী মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ আইং সরস্বত্যৈ নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","১০। শ্রী সন্তোষী মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ সন্তোষী মায়ৈ নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","১১। শ্রী কৃষ্ণ মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ শ্রী কৃষ্ণায় নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","১২। কালভৈরব মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ ভৈরবায় নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","১৩। নৃসিংহ মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ ক্ষ্রাঁ নৃসিংহায় নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","১৪। শ্রী সিদ্ধকালী মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ কালী কালী মহাকালী কালিকে স্বাহা");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","১৫। মা কালী মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ ক্রীং কালিকায়ৈ নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","১৬।  ত্রিপুরসুন্দরী মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ হ্রীং শ্রিং ক্লীং কামরূপিণ্যৈ নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","১৭। শ্রী রাধা মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ রাধায়ৈ নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","১৮। তুলসী দেবীর মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ তুলসৈ নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","১৯। সূর্যদেবের মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ ঘৃণিঃ সূর্যায় নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","২০। শনি দেবের মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ শং শনৈশ্চরায় নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","২১। তারা মা মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ তারা মায়ৈ নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","২২। অন্নপূর্ণা দেবীর মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ অন্নপূর্ণায়ৈ নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","২৩। ধনদা দেবী (কুবেরের সহচরী) মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ ধনদায়ৈ নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","২৪। ভুবনেশ্বরী মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ হ্রীং ভুবনেশ্বর্যৈ নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","২৫। শ্রী শীতলা মা মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ শীতলায়ৈ নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","২৬। নাগদেবতা মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ নাগায় নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","২৭। শ্রী গজানন (গজলক্ষ্মী) মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ গজলক্ষ্ম্যৈ নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","২৮। শ্রী রামকৃষ্ণ মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ রামকৃষ্ণায় নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","২৯। জগন্নাথ মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ জগন্নাথায় নমঃ");
        mapList.add(map);

        map = new HashMap<>();
        map.put("দেবতার নাম","৩০। অষ্টলক্ষ্মীর মন্ত্র");
        map.put("জপ মন্ত্র","ওঁ শ্রিং হ্রীং ক্রীং অষ্টলক্ষ্ম্যৈ নমঃ");
        mapList.add(map);



    }

}//public class=====================================