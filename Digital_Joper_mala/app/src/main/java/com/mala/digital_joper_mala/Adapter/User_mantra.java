package com.mala.digital_joper_mala.Adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mala.digital_joper_mala.R;
import com.mala.digital_joper_mala.Database.Database_helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User_mantra extends RecyclerView.Adapter<User_mantra.mantra> {

    private Context context;
    private List<HashMap<String, String>> mapList = new ArrayList<>();
    private Database_helper helper;

    public User_mantra(Context context, List<HashMap<String, String>> mapList) {
        this.context = context;
        this.mapList = mapList;
    }

    @NonNull
    @Override
    public mantra onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.lay_user_input_mantra, parent, false);

        return new mantra(view);
    }

    @Override
    public void onBindViewHolder(@NonNull mantra holder, int position) {

        helper = new Database_helper(context);

        HashMap<String, String> map = mapList.get(position);

        String title = map.get("title");
        String mantra = map.get("mantra");

        holder.tv_title.setText(title);
        holder.tv_mantra.setText(mantra);

        holder.iv_copy.setOnClickListener(view -> {

            ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData data = ClipData.newPlainText("Text copied",mantra);
            manager.setPrimaryClip(data);

            new Handler(Looper.getMainLooper()).post(() -> {

                Toast.makeText(context, "কপি হয়েছে", Toast.LENGTH_SHORT).show();

            });

        });
        holder.iv_delete.setOnClickListener(view -> {

            Toast.makeText(context, "ডিলিট হয়েছে", Toast.LENGTH_SHORT).show();

            boolean deleted =  helper.delete(title);

            if (deleted){
                mapList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mapList.size());
            }





        });


    }

    @Override
    public int getItemCount() {
        return mapList.size();
    }

    public static class mantra extends RecyclerView.ViewHolder{

        private AppCompatTextView tv_title, tv_mantra;
        private AppCompatImageView iv_copy, iv_delete;

        public mantra(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_title);
            tv_mantra = itemView.findViewById(R.id.tv_mantra);
            iv_copy = itemView.findViewById(R.id.iv_copy);
            iv_delete = itemView.findViewById(R.id.iv_delete);

        }
    }

}
