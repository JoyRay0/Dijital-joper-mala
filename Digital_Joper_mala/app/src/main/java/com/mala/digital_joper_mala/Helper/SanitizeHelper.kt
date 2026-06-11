package com.mala.digital_joper_mala.Helper

object SanitizeHelper {

    fun sanitizeNumber(number : String) : Long{

        val clean = number.replace(Regex("""\D+"""), "")

        return clean.toLongOrNull() ?: 0L

    }

    fun sanitizeText(text : String) : String{

        val _text = text.replace(Regex("""[^\p{L}\p{P}\p{M}\s]+"""), "")

        return _text

    }

}