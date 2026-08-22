package com.mala.digital_joper_mala.Model

import android.content.Context
import com.mala.digital_joper_mala.Helper.CacheHelper_


data class BoishnobItem(
    val id : Int = 0,
    val title : String = "",
    val mantra : String = ""
)

class BoishnobMalaModel(
    private val context: Context
) {

    private val cache = CacheHelper_(context, "boishnob_mala")

    fun getBoishnobMala() : List<BoishnobItem>{

        val boishnobMalaList : MutableList<BoishnobItem> = mutableListOf()

        boishnobMalaList.add(BoishnobItem(
            id = 1,
            title = "জপ শুরুর মন্ত্র:",
            mantra = "‘‘ শ্রীকৃষ্ণ চৈতন্য প্রভু নিত্যানন্দ শ্রী অদ্বৈত গদাধর শ্রীবাসাদি গৌর ভক্তবৃন্দ। ’’"
        ))

        boishnobMalaList.add(BoishnobItem(
            id = 2,
            title = "অষ্টসখীর নাম:",
            mantra = "১। ললিতা ২। বিশাখা ৩। সুচিএা ৪। চম্পকলতা ৫। রঙ্গঁদেবী ৬। ইন্দুরেখা ৭। তুঙ্গঁবিদ্যা ৮। সুদেবী"
        ))

        boishnobMalaList.add(BoishnobItem(
            id = 3,
            title = "শ্রী হরিনাম মহামন্ত্র:",
            mantra = "‘‘ হরে কৃষ্ণ হরে কৃষ্ণ কৃষ্ণ কৃষ্ণ হরে হরে হরে রাম হরে রাম রাম রাম হরে হরে। ’’"
        ))

        boishnobMalaList.add(BoishnobItem(
            id = 4,
            title = "শ্রী শ্রী হরিনাম জপ সমাপনের মন্ত্র:",
            mantra = "‘‘ শ্রী হরিনাম মহাযজ্ঞ কলৌ পাপবিনাশনং শ্রীকৃষ্ণ চৈতন্য প্রীত্যার্থে শ্রী হরিনাম জপ সমাপন। ’’"
        ))

        return boishnobMalaList

    }

    fun setLastCountCache(value : String){

        if (value.isEmpty()) return

        cache.setCache("boishnob_mala_count", value)

    }

    fun getLastCountCache() : String{

        return cache.getCache("boishnob_mala_count", "")

    }

    fun setCountLimit(value: String){

        if (value.isEmpty()) return

        cache.setCache("boishnob_mala_count_limit", value)

    }

    fun getCountLimit() : String{

        return cache.getCache("boishnob_mala_count_limit", "0")

    }


}