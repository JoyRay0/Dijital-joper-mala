package com.mala.digital_joper_mala.View;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.mala.digital_joper_mala.Adapter.UserMalas;
import com.mala.digital_joper_mala.Database.UserMala;
import com.mala.digital_joper_mala.R;
import com.mala.digital_joper_mala.Helper.GalleryHelper;
import com.mala.digital_joper_mala.Helper.onDataDeleteListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class Fg_create_mala extends Fragment implements onDataDeleteListener {

    //XML id's-------------------------------------------------------------------------

    private AppCompatTextView tv_no_item;
    private RecyclerView rv_mala;

    //fab
    private AppCompatImageView iv_create;

    //list
    private List<HashMap<String, String>> m_list  = new ArrayList<>();
    

    //initialize variable
    String fi_mantra = "";
    String se_mantra = "";
    String th_mantra = "";
    String fo_mantra = "";
    String img_uri = "";



    //other
    private UserMalas userMalaAdapter;
    private UserMala mala;

    private GalleryHelper permissionHelper;

    //XML id's-------------------------------------------------------------------------

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fg_create_mala, container, false);

        //identity period--------------------------------------------------------------

        tv_no_item = view.findViewById(R.id.tv_no_item);
        rv_mala = view.findViewById(R.id.rv_mala);
        iv_create = view.findViewById(R.id.iv_create);

        //identity period--------------------------------------------------------------

        userMalaAdapter = new UserMalas(requireActivity(), m_list);
        rv_mala.setAdapter(userMalaAdapter);

        mala = new UserMala(requireActivity());

        permissionHelper = new GalleryHelper(Fg_create_mala.this, new Function1<Uri, Unit>() {
            @Override
            public Unit invoke(Uri uri) {

                img_uri = uri.toString();

                return null;
            }
        });

        List<HashMap<String, String>> allList = mala.getAll();
        m_list.clear();

        if (allList == null || allList.isEmpty()){

            tv_no_item.setVisibility(View.VISIBLE);
            rv_mala.setVisibility(View.GONE);

        }else {

            m_list.addAll(mala.getAll());
            tv_no_item.setVisibility(View.GONE);
            rv_mala.setVisibility(View.VISIBLE);

            userMalaAdapter.notifyDataSetChanged();
        }



        iv_create.setOnClickListener(view1 -> {

            create_dialog();

        });

        return view;
    }// on create========================================================================

    //create dialog----------------------------------------------------------------
    private void create_dialog(){

        Dialog dialog = new Dialog(requireActivity());
        dialog.setContentView(R.layout.lay_create_mala);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        AppCompatAutoCompleteTextView ed_mala_title = dialog.findViewById(R.id.ed_mala_title);
        AppCompatAutoCompleteTextView ed_mala1 = dialog.findViewById(R.id.ed_mala1);
        AppCompatAutoCompleteTextView ed_mala2 = dialog.findViewById(R.id.ed_mala2);
        AppCompatAutoCompleteTextView ed_mala3 = dialog.findViewById(R.id.ed_mala3);
        AppCompatAutoCompleteTextView ed_mala4 = dialog.findViewById(R.id.ed_mala4);
        AppCompatTextView tv_image = dialog.findViewById(R.id.tv_image);
        CardView cd_cancel = dialog.findViewById(R.id.cd_cancel);
        CardView cd_ok = dialog.findViewById(R.id.cd_ok);



        tv_image.setOnClickListener(view -> {

            //Toast.makeText(requireActivity(), "পরবর্তী আপডেটে এটি যুক্ত করা হবে।", Toast.LENGTH_SHORT).show();

            permissionHelper.pickImage();

        });

        cd_cancel.setOnClickListener(view -> {

            dialog.dismiss();
            img_uri = "";

        });

        cd_ok.setOnClickListener(view -> {

            String mala_title = ed_mala_title.getText().toString().trim();
            String mantra1 = ed_mala1.getText().toString().trim();
            String mantra2 = ed_mala2.getText().toString().trim();
            String mantra3 = ed_mala3.getText().toString().trim();
            String mantra4 = ed_mala4.getText().toString().trim();

            String image = permissionHelper.getImageUri(img_uri).toString();

            if (mala_title == null || mala_title.isEmpty()){

                ed_mala_title.setError("দিতে হবে");

            }else {

                if (!mantra1.isEmpty()){

                    fi_mantra = mantra1;

                }else {

                    fi_mantra = null;

                }

                if (!mantra2.isEmpty()) {

                    se_mantra = mantra2;

                }else {

                    se_mantra = null;

                }

                if (!mantra3.isEmpty()) {

                    th_mantra = mantra3;

                }else {

                    th_mantra = null;

                }

                if (!mantra4.isEmpty()) {

                    fo_mantra = mantra4;

                }else {

                    fo_mantra = null;

                }

                Log.d("dbInsert", image);

                m_list.clear();
                mala.insert(mala_title, image, fi_mantra, se_mantra, th_mantra, fo_mantra);
                m_list.addAll(mala.getAll());

                if (m_list == null || m_list.isEmpty()){

                    tv_no_item.setVisibility(View.VISIBLE);
                    rv_mala.setVisibility(View.GONE);

                }else {

                    tv_no_item.setVisibility(View.GONE);
                    rv_mala.setVisibility(View.VISIBLE);
                    userMalaAdapter.notifyDataSetChanged();

                }

                Toast.makeText(requireActivity(), "সেভ হয়েছে।", Toast.LENGTH_SHORT).show();

                img_uri = "";
                dialog.dismiss();

            }

        });

        dialog.show();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mala.closeDB();
    }

    @Override
    public void onDataDeleted() {

        mala = new UserMala(requireActivity());
        m_list.clear();
        m_list.addAll(mala.getAll());
        userMalaAdapter.notifyDataSetChanged();

    }


}// public class=========================================================================