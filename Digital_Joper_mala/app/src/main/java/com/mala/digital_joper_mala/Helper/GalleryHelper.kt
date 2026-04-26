package com.mala.digital_joper_mala.Helper

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri

class GalleryHelper(

    fragment: androidx.fragment.app.Fragment,
    val onImagePicked : (Uri) -> Unit = {}

) {
    private val galleryLauncher = fragment.registerForActivityResult(

        ActivityResultContracts.GetContent()
    ){ it ->

        it?.let {

            try {
                fragment.requireActivity().contentResolver.takePersistableUriPermission(
                    it,
                    android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (e: SecurityException) {
                e.printStackTrace()
            }

            onImagePicked(it) }
            //imageUri = it

    }

    private val permissionLauncher = fragment.registerForActivityResult(

        ActivityResultContracts.RequestPermission()

    ){ granted ->

        if (granted) openGallery()

    }

    val context = fragment.requireContext()

    fun pickImage(){

        val permission = getPermission()

        if (ContextCompat.checkSelfPermission(context, permission) ==

            PackageManager.PERMISSION_GRANTED)
        {

            openGallery()

        }else{

            permissionLauncher.launch(permission)
        }

    }

    private fun openGallery(){

        galleryLauncher.launch("image/*")
    }

    private fun getPermission() : String{

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){

            Manifest.permission.READ_MEDIA_IMAGES

        }else {

            Manifest.permission.READ_EXTERNAL_STORAGE

        }

    }

    fun getImageUri(imageUri : String) : Uri {

        return try {
            val uri = imageUri.toUri()

            val id = DocumentsContract.getDocumentId(uri)
                .split(":")[1]

            val actualUri = ContentUris.withAppendedId(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                id.toLong()
            )

            actualUri

        } catch (e: Exception) {
            e.printStackTrace()
            // Fallback - সরাসরি original URI return করুন
            imageUri.toUri()
        }

    }

}