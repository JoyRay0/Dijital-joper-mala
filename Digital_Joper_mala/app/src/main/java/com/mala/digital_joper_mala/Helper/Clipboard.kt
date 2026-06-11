package com.mala.digital_joper_mala.Helper

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

object Clipboard {

    fun clipData(context: Context, text : String){

        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("text", text)
        clipboard.setPrimaryClip(clip)

    }

}