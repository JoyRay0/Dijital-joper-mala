package com.mala.digital_joper_mala.Utils

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.mala.digital_joper_mala.R
import java.util.Locale

object BanglaHelper {

    fun readInt( number : Int) : String {

        return String.format(Locale.forLanguageTag("bn"), "%d", number)

    }//fun end

    fun readLong( number : Long) : String {

        return String.format(Locale.forLanguageTag("bn"), "%d", number)

    }//fun end

    fun banglaFont() : FontFamily{

        return FontFamily(Font(R.font.noto_serif_bengali))
    }

}