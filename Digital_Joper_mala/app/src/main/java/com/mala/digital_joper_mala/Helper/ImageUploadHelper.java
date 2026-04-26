package com.mala.digital_joper_mala.Helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;

import com.mala.digital_joper_mala.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ImageUploadHelper {

    private String imageName;
    private Activity activity;
    private AppCompatImageView ivUpload;
    private AppCompatImageView ivDelete;
    private AppCompatImageView ivUploadView;
    private static final int REQUEST_IMG_PICK = 1;
    private String img = "";

    public ImageUploadHelper(Activity activity, String imageName, AppCompatImageView ivUploadView ,AppCompatImageView ivUpload, AppCompatImageView ivDelete) {
        this.activity = activity;
        this.imageName = imageName;
        this.ivUpload = ivUpload;
        this.ivDelete = ivDelete;
        this.ivUploadView = ivUploadView;
    }

    public void imageButtons(){

        ivUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activity.startActivityForResult(intent, REQUEST_IMG_PICK);

            }
        });

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteImage();

            }
        });

    }

    public void loadImage(){

        try {

            FileInputStream fileInputStream = activity.openFileInput(imageName);

            Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
            ivUploadView.setImageBitmap(bitmap);
            fileInputStream.close();

        }catch (Exception e){

            e.printStackTrace();

        }

    }

    private void saveImageToStorge(Bitmap bitmap){

        try {

            File file = new File(activity.getFilesDir(),imageName);
            if (file.exists()){
                file.delete();

            }

            //file created-----
            FileOutputStream fos = activity.openFileOutput(imageName, Context.MODE_PRIVATE);

            //image compressed----
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

        }catch (Exception e){

            e.printStackTrace();
            Toast.makeText(activity, "Failed to save image", Toast.LENGTH_SHORT).show();

        }

    }

    private void deleteImage(){

        File file = new File(activity.getFilesDir(),imageName);

        if (file.exists()){

            if (file.delete()){

                ivUploadView.setImageDrawable(null);
                ivUploadView.setImageResource(R.drawable.img_gallery);

            }

        }
    }

    public void ActivityResult(int requestCode, int resultCode, Intent data){

        if (requestCode == REQUEST_IMG_PICK && resultCode == activity.RESULT_OK && data != null){

            try {

                Uri img_uri = data.getData();

                img = img_uri.toString();

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), img_uri);

                ivUploadView.setImageBitmap(bitmap);

                saveImageToStorge(bitmap);

            } catch (Exception e) {

                e.printStackTrace();
            }

        }

    }

    public String getImgUri(){

        return (img == null || img.isEmpty()) ? img = null : img;

    }

}
