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
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mala.digital_joper_mala.R;

import java.util.HashMap;
import java.util.List;

public class Mantras extends RecyclerView.Adapter<Mantras.Holder> {

    private List<HashMap<String, String>> list;
    private Context context;

    public Mantras(Context context, List<HashMap<String, String>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.lay_mantra, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        HashMap<String, String> map = list.get(position);

        String title = map.get("title");
        String mantra = map.get("mantra");

        holder.tv_title.setText(title);
        holder.tv_mantra.setText(mantra);

        holder.tv_mantra.setOnClickListener(view -> {

            ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData data = ClipData.newPlainText("Text copied",mantra);
            manager.setPrimaryClip(data);

            new Handler(Looper.getMainLooper()).post(() -> {

                Toast.makeText(context, "কপি হয়েছে", Toast.LENGTH_SHORT).show();

            });

        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Holder extends RecyclerView.ViewHolder{

        AppCompatTextView tv_title, tv_mantra;

        public Holder(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_title);
            tv_mantra = itemView.findViewById(R.id.tv_mantra);

        }
    }

}
