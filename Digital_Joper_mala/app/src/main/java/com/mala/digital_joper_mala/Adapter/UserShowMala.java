package com.mala.digital_joper_mala.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mala.digital_joper_mala.View.Act_User_mala;
import com.mala.digital_joper_mala.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class UserShowMala extends RecyclerView.Adapter<UserShowMala.holder> {

    private List<HashMap<String, String>> list;
    private Context context;

    public UserShowMala(Context context, List<HashMap<String, String>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.lay_desgin_for_all_mala_home, parent, false);

        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

        HashMap<String, String> map = list.get(position);

        String name = map.get("mala_name");

        holder.tv_all_mala_name.setText(name);

        Picasso.get().load(R.drawable.img_splash).into(holder.iv_all_mala_image);

        holder.iv_all_mala_image.setOnClickListener(view -> {

            Intent intent = new Intent(context, Act_User_mala.class);
            intent.putExtra("show", "Fg_show_user_mala");
            intent.putExtra("name", name);
            context.startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class holder extends RecyclerView.ViewHolder{

        AppCompatImageView iv_all_mala_image;
        AppCompatTextView tv_all_mala_name;

        public holder(@NonNull View itemView) {
            super(itemView);

            iv_all_mala_image = itemView.findViewById(R.id.iv_all_mala_image);
            tv_all_mala_name = itemView.findViewById(R.id.tv_all_mala_name);

        }
    }

}
