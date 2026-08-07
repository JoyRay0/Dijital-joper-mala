package com.mala.digital_joper_mala.Model

data class Mantra(
    val status : String = "",
    val message : String = "",
    val data : List<MantraItem> = emptyList()
)

data class MantraItem(
    val id : Int = 0,
    val title : String = "",
    val mantra : String ="",
)

class AllMantraModel {
}