package com.mala.digital_joper_mala.Helper;

import android.app.Activity;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Vibrator;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.mala.digital_joper_mala.Database.JopaChartDB;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class CounterHelper {

    private Activity activity;
    private TextView tvDisplay1;
    private TextView tvDisplay2;
    private TextView tvDisplay3;

    private JopaChartDB jopaDB;

    String[] banglaNumber = {"১", "২", "৩", "৪", "৫", "৬", "৭", "৮", "৯", "১০",

            "১১", "১২", "১৩", "১৪", "১৫", "১৬", "১৭", "১৮", "১৯", "২০",
            "২১", "২২", "২৩", "২৪", "২৫", "২৬", "২৭", "২৮", "২৯", "৩০",
            "৩১", "৩২", "৩৩", "৩৪", "৩৫", "৩৬", "৩৭", "৩৮", "৩৯", "৪০",
            "৪১", "৪২", "৪৩", "৪৪", "৪৫", "৪৬", "৪৭", "৪৮", "৪৯", "৫০",
            "৫১", "৫২", "৫৩", "৫৪", "৫৫", "৫৬", "৫৭", "৫৮", "৫৯", "৬০",
            "৬১", "৬২", "৬৩", "৬৪", "৬৫", "৬৬", "৬৭", "৬৮", "৬৯", "৭০",
            "৭১", "৭২", "৭৩", "৭৪", "৭৫", "৭৬", "৭৭", "৭৮", "৭৯", "৮০",
            "৮১", "৮২", "৮৩", "৮৪", "৮৫", "৮৬", "৮৭", "৮৮", "৮৯", "৯০",
            "৯১", "৯২", "৯৩", "৯৪", "৯৫", "৯৬", "৯৭", "৯৮", "৯৯", "১০০",
            "১০১", "১০২", "১০৩", "১০৪", "১০৫", "১০৬", "১০৭", "১০৮"};

    String[] zero = {"০"};

    String fiCount = "";
    String seCount = "";
    String thCount = "";

    int firstCount = 0, secondCount = 0, thirdCount = 0;

    public CounterHelper(Activity activity, TextView tvDisplay1, TextView tvDisplay2, TextView tvDisplay3) {
        this.activity = activity;
        this.tvDisplay1 = tvDisplay1;
        this.tvDisplay2 = tvDisplay2;
        this.tvDisplay3 = tvDisplay3;
        this.jopaDB = new  JopaChartDB(activity);
    }

    public void setFirstCount(AppCompatButton add, AppCompatButton reset){

        add.setOnClickListener(view -> {

            firstCount++;


            if (firstCount > 0 && firstCount < 109){

                tvDisplay1.setText(banglaNumber[firstCount - 1]);

                fiCount = getCount(tvDisplay1);

            }
            vibrate();

        });

        reset.setOnClickListener(view -> {

            firstCount = 0;

            tvDisplay1.setText(zero[firstCount]);
            fiCount = getCount(tvDisplay1);

            vibrate();

        });

    }

    public void setSecondCount(AppCompatButton add, AppCompatButton reset){

        add.setOnClickListener(view -> {

            secondCount++;

            if (secondCount > 0 && secondCount < 17){

                tvDisplay2.setText(banglaNumber[secondCount - 1]);
                seCount = getCount(tvDisplay2);

                setDataInDB(1);

            }
            vibrate();

        });

        reset.setOnClickListener(view -> {

            secondCount = 0;

            tvDisplay2.setText(zero[secondCount]);
            seCount = getCount(tvDisplay2);

            vibrate();

        });


    }

    public void setThirdCount(AppCompatButton add, AppCompatButton reset){

        add.setOnClickListener(view -> {

            thirdCount++;

            if (thirdCount > 0 && thirdCount < 5){

                tvDisplay3.setText(banglaNumber[thirdCount - 1]);
                thCount = getCount(tvDisplay3);

            }

            vibrate();

        });

        reset.setOnClickListener(view -> {

            thirdCount = 0;

            tvDisplay3.setText(zero[thirdCount]);
            thCount = getCount(tvDisplay3);

            vibrate();

        });

    }

    private void vibrate(){

        Vibrator vibrator = (Vibrator) activity.getSystemService(Activity.VIBRATOR_SERVICE);

        vibrator.vibrate(50);

    }

    private String getCount(TextView tvDisplay){

        return tvDisplay.getText().toString();

    }

    public String getFirstCount(){

        return (fiCount == null || fiCount.isEmpty()) ? "" : fiCount;

    }

    public String getSecondCount(){

        return (seCount == null || seCount.isEmpty()) ? "" : seCount;

    }

    public String getThirdCount(){

        return (thCount == null || thCount.isEmpty()) ? "" : thCount;

    }

    private void setDataInDB(int count){

        int year = 0;
        String month = "";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            year = LocalDate.now().getYear();

            month = LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        }else {

            Calendar cal = Calendar.getInstance();
            year = cal.get(Calendar.YEAR);

            SimpleDateFormat sdf = new SimpleDateFormat("MMMM", Locale.ENGLISH);
            month = sdf.format(cal.getTime());

        }

        //String[] monthList = {"january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december"};

        if (year > 2025 && !month.isEmpty() && !month.equals("null")){

            jopaDB.insert(year, month.toLowerCase(Locale.ENGLISH), count);

            //Log.d("date", "year = "+year+"  "+"month = "+month.toLowerCase()+" "+"count = "+count);

        }

    }

}
