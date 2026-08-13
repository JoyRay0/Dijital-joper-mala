package com.mala.digital_joper_mala.Model


data class ShivItem(
    val id : Int = 0,
    val title : String = "",
    val mantra : String = ""
)

class ShivMalaModel {

    fun getShivMala() : List<ShivItem>{

        val shivMalaList : MutableList<ShivItem> = mutableListOf()

        shivMalaList.add(ShivItem(
            id = 1,
            title = "মালা জপের পূর্বের মন্ত্র",
            mantra = "“ধ্যায়েত পদ্মাসনস্থং প্রখট বিমল পদ্মাভিরামং মহান্তং। ধ্যানমূর্তিং ত্রিনেত্রং কমল গরলধরং নীলকণ্ঠং প্রসন্নম”"
        ))

        shivMalaList.add(ShivItem(
            id = 2,
            title = "মূল মন্ত্র",
            mantra = "“ ওঁ নমঃ শিবায় ”"
        ))

        shivMalaList.add(ShivItem(
            id = 3,
            title = "মালা জপের সমাপনী মন্ত্র",
            mantra = "“ কপূর-গৌরং, করুণাবতারং, সংসারসারং, ভুজগেন্দ্রহারং। সদা বসন্তং হৃদয়ারবিন্দে, ভবং ভবানীসহিতং নমামি”"
        ))

        return shivMalaList

    }

}