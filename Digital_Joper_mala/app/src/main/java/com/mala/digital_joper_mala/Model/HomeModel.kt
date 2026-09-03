package com.mala.digital_joper_mala.Model

import android.content.Context
import com.google.gson.annotations.SerializedName
import com.mala.digital_joper_mala.Database.AllMantraDatabase
import com.mala.digital_joper_mala.Helper.ApiLinkHelper
import com.mala.digital_joper_mala.Helper.OkHttpWrapper
import com.mala.digital_joper_mala.Helper.header
import java.util.Collections.emptyList

data class Home(

    val status : String = "",
    val message : String = "",
    val version : String = "",
    @SerializedName("data")
    val data : List<HomeData> = emptyList()

)

data class HomeData(

    val id : Int = 0,
    val question : String = "",
    val image : String = "",
    val answer : String = "",
    val title : String = "",
    val mantra : String = ""

)

class HomeModel(
    private val context : Context
) {

    private val allMantraDB = AllMantraDatabase(context)

    fun getRules() : List<HomeData>{

        val list : MutableList<HomeData> = mutableListOf()

        list.add(HomeData(
            id = 1,
            question = "১। জপের পূর্বে ইষ্টদেবতার স্মরণ কেন গুরুত্বপূর্ণ বলে বিবেচিত হয়?",
            answer = "মালা জপ শুরু করার পূর্বে, ভক্তিভরে ইষ্টদেবতার নাম স্মরণ করলে জপ আরও ফলদায়ী হয়।"
        ))

        list.add(HomeData(
            id = 2,
            question = "২। জপ করার ক্ষেত্রে স্নানের পর সময়টিকে কেন সর্বোত্তম বলে বিবেচনা করা হয়?",
            answer = "জপমালা ব্যবহারের আদর্শ সময় হলো স্নানের পর, যখন শরীর ও মন পবিত্র থাকে। এই পবিত্রতা আমাদের ভক্তিভাব জাগিয়ে তোলে এবং জপে গভীর মনোযোগ বজায় রাখতে সহায়তা করে।"
        ))

        list.add(HomeData(
            id = 3,
            question = "৩। অপবিত্র দেহে জপ করলে তার প্রভাব কী ধরনের হতে পারে?",
            answer = "অপবিত্র শরীরে, বিশেষত শৌচকর্মের পর স্নান না করে জপ করলে—তা পূর্ণভাবে কার্যকর হয় না। জপের সফলতার জন্য দেহ ও মন উভয়ের পবিত্রতা অপরিহার্য।"
        ))

        list.add(HomeData(
            id = 4,
            question = "৪। জপের প্রথম তিন ধাপ কীভাবে সম্পন্ন করতে হয়?",
            answer = "শুরুতে ১০৮ বার জপ শেষ করুন। তারপর দ্বিতীয় ধাপে, ১ বার করে মোট ১৬ বার গননা করবেন। শেষে, ৩য় ধাপে আবার ১ বার করে মোট ৪ বার গননা করুন।"
        ))

        list.add(HomeData(
            id = 5,
            question = "৫। কোন জায়গায় বসে জপ করলে সবচেয়ে ভালো ফল পাওয়া যায়?",
            answer = "জপের সময় এমন স্থানে বসা উচিত যা পরিচ্ছন্ন, পবিত্র এবং শান্ত। শব্দ থেকে মুক্ত এমন পরিবেশ মনকে স্থির করে এবং ভগবানের স্মৃতিতে গভীর নিমগ্ন হতে সহায়তা করে। জপের জন্য নির্দিষ্ট স্থান থাকা, নিয়মিত সেখানে বসে জপ করা শরীর ও মনের অভ্যাস গড়তে সাহায্য করে।"
        ))

        list.add(HomeData(
            id = 6,
            question = "৬। নবীনদের জন্য কম বার জপ শুরু করার পরামর্শ কী?",
            answer = "১০৮ বার জপ করার বিশেষ তাৎপর্য আছে। শাস্ত্রে ১০৮ সংখ্যাটি পবিত্র ও পূর্ণতা নির্দেশ করে। যখন আপনি ১০৮ বার জপ করেন, তখন আপনার সাধনা পরিপূর্ণ হয় এবং মন্ত্রের শক্তি পূর্ণরূপে প্রবাহিত হয়। তাই সাধারনত ১০৮ বার থেকে কম জপ করা উচিত নয়। তবে, নবীন বা সময়ের অভাবে কেউ কম বার জপ শুরু করলেও নিয়মিত ও ভক্তিপূর্ণ জপ করাই সবচেয়ে গুরুত্বপূর্ণ। সময় সুযোগ মতো ধীরে ধীরে ১০৮ পূর্ণ করা উত্তম।"
        ))

        return list


    }

    fun dataFromServer(
        onSuccess : (List<HomeData>) -> Unit = {},
        onFailed : (Boolean) -> Unit = {},
        onError : (String) -> Unit = {}
    ){

        OkHttpWrapper()
            .url(ApiLinkHelper.info())
            .execute(Home::class.java, onSuccess = {

                if (it.status == "Success"){

                    onSuccess(it.data)

                }else{

                    onFailed(true)

                }


            }, onFailed = {

                onFailed(it)

            }, onError = {

                onError(it)

            })

    }

    fun pagerDataFromServer(
        onSuccess : (List<HomeData>) -> Unit = {},
        onFailed : (Boolean) -> Unit = {},
        onError : (String) -> Unit = {}
    ){

        OkHttpWrapper()
            .url(ApiLinkHelper.pager())
            .execute(Home::class.java, onSuccess = {

                if (it.status == "Success"){

                    onSuccess(it.data)

                }else{

                    onFailed(true)

                }


            }, onFailed = {

                onFailed(it)

            }, onError = {

                onError(it)

            })


    }

    fun appUpdate(
        onSuccess : (String) -> Unit = {},
        onFailed : (Boolean) -> Unit = {}
    ){

        OkHttpWrapper()
            .url(ApiLinkHelper.appUpdate())
            .execute(Home::class.java, onSuccess = {

                if (it.status == "Success"){

                    onSuccess(it.version)

                }else{

                    onFailed(true)

                }


            }, onFailed = {

                onFailed(it)

            })

    }

    fun getAllMantra() : List<HomeData>{

        val list : MutableList<HomeData> = mutableListOf()

        val data = allMantraDB.getAllMantra(1)

        data.forEach { result ->

            list.add(HomeData(
                title = result.title,
                mantra = result.mantra
            ))

        }

        return list
    }

}