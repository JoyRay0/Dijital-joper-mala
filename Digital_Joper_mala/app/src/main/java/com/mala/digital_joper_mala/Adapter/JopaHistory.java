package com.mala.digital_joper_mala.Adapter;

import android.app.Activity;
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

import com.mala.digital_joper_mala.Database.History;
import com.mala.digital_joper_mala.R;

import java.util.ArrayList;
import java.util.HashMap;

public class JopaHistory extends RecyclerView.Adapter<JopaHistory.Histotyholder> {

    private ArrayList<HashMap<String, String>> list;
    private Activity  activity;
    private History historyDB;

    public JopaHistory(Activity  activity, ArrayList<HashMap<String, String>> list) {
        this.activity = activity;
        this.list = list;
        this.historyDB = new History(activity);

    }

    @NonNull
    @Override
    public Histotyholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity).inflate(R.layout.lay_jopa_history, parent, false);

        return new Histotyholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Histotyholder holder, int position) {

        HashMap<String, String> map = list.get(position);


        holder.cb_check.setVisibility(View.GONE);
        holder.iv_delete.setVisibility(View.GONE);
        holder.cb_check.setChecked(false);

        holder.cv_jopa_list.setOnLongClickListener(view -> {

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

        holder.iv_delete.setOnClickListener(view -> {

            boolean delete  =  historyDB.DeleteOne(map.get("title"));
            int currentPosition = holder.getAdapterPosition();

            if (currentPosition != RecyclerView.NO_POSITION && currentPosition < list.size()){


                list.remove(currentPosition);
                notifyItemRemoved(currentPosition);
                notifyDataSetChanged();

                if (delete) Toast.makeText(activity, "ডিলিট হয়েছে।", Toast.LENGTH_SHORT).show();
            }

        });

        holder.tv_title.setText(map.get("title"));
        holder.tv_count.setText(map.get("counter"));

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public static class Histotyholder extends RecyclerView.ViewHolder{

        AppCompatCheckBox cb_check;
        AppCompatImageView iv_delete;
        CardView cv_jopa_list;
        AppCompatTextView tv_title, tv_count;

        public Histotyholder(@NonNull View itemView) {
            super(itemView);

            cb_check = itemView.findViewById(R.id.cb_check);
            iv_delete = itemView.findViewById(R.id.iv_delete);
            cv_jopa_list = itemView.findViewById(R.id.cv_jopa_list);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_count = itemView.findViewById(R.id.tv_count);

        }
    }

}
