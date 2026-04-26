package com.mala.digital_joper_mala.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mala.digital_joper_mala.View.Act_Home_All_Mala;
import com.mala.digital_joper_mala.Database.UserMala;
import com.mala.digital_joper_mala.R;

import java.util.HashMap;
import java.util.List;

public class UserMalas extends RecyclerView.Adapter<UserMalas.holder> {

    private List<HashMap<String, String>> list;
    private Activity activity;
    private UserMala mala;

    public UserMalas(Activity activity, List<HashMap<String, String>> list) {
        this.activity = activity;
        this.list = list;
        this.mala = new UserMala(activity);
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity).inflate(R.layout.lay_user_mala, parent, false);

        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

        HashMap<String, String> map = list.get(position);

        holder.cb_check.setVisibility(View.GONE);
        holder.iv_delete.setVisibility(View.GONE);
        holder.cb_check.setChecked(false);

        holder.tv_title.setText(map.get("mala_name"));

        holder.cv_title.setOnLongClickListener(view -> {

            holder.cb_check.setVisibility(View.VISIBLE);
            holder.iv_delete.setVisibility(View.VISIBLE);
            holder.cb_check.setChecked(true);

            return true;
        });

        holder.cb_check.setOnClickListener(view -> {

            holder.cb_check.setVisibility(View.GONE);
            holder.iv_delete.setVisibility(View.GONE);
            holder.cb_check.setChecked(false);

        });

        holder.cv_title.setOnClickListener(view -> {

            activity.startActivity(new Intent(activity, Act_Home_All_Mala.class));
            activity.finishAffinity();

        });

        holder.iv_delete.setOnClickListener(view -> {

            boolean delete  =  mala.deleteOne(map.get("mala_name"));
            int currentPosition = holder.getAdapterPosition();

            if (currentPosition != RecyclerView.NO_POSITION && currentPosition < list.size()){

                list.remove(currentPosition);
                notifyItemRemoved(currentPosition);
                notifyDataSetChanged();

            }

            if (delete) Toast.makeText(activity, "ডিলিট হয়েছে।", Toast.LENGTH_SHORT).show();

        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class holder extends RecyclerView.ViewHolder{

        AppCompatImageView iv_delete;
        AppCompatCheckBox cb_check;
        CardView cv_title;
        AppCompatTextView tv_title;


        public holder(@NonNull View itemView) {
            super(itemView);

            iv_delete = itemView.findViewById(R.id.iv_delete);
            cb_check = itemView.findViewById(R.id.cb_check);
            cv_title = itemView.findViewById(R.id.cv_title);
            tv_title = itemView.findViewById(R.id.tv_title);

        }
    }

}
