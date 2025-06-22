package com.mala.digital_joper_mala.Adapter;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.mala.digital_joper_mala.R;

import java.util.HashMap;
import java.util.List;

public class All_mantra extends BaseAdapter {

    private List<HashMap<String, String>> mapList;
    private Context context;

    public All_mantra(Context context, List<HashMap<String, String>> mapList) {
        this.context = context;
        this.mapList = mapList;
    }

    @Override
    public int getCount() {
        return mapList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        @SuppressLint("ViewHolder")
        View view1 = LayoutInflater.from(context).inflate(R.layout.lay_mantra, viewGroup, false);

        AppCompatTextView tv_title = view1.findViewById(R.id.tv_title);
        AppCompatTextView tv_mantra = view1.findViewById(R.id.tv_mantra);
        AppCompatImageView iv_copy = view1.findViewById(R.id.iv_copy);

        HashMap<String, String> map = mapList.get(position);

        String dev = map.get("দেবতার নাম");
        String mantras = map.get("জপ মন্ত্র");

        tv_title.setText(dev);
        tv_mantra.setText(mantras);

        iv_copy.setOnClickListener(view2 -> {

            ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData data = ClipData.newPlainText("Text copied",mantras);
            manager.setPrimaryClip(data);

            new Handler(Looper.getMainLooper()).post(() -> {

                Toast.makeText(context, "কপি হয়েছে", Toast.LENGTH_SHORT).show();

            });

        });


        return view1;
    }
}
