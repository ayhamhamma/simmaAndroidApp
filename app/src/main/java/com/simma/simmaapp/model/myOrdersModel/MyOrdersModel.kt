package com.simma.simmaapp.model.myOrdersModel

import com.google.gson.annotations.SerializedName

data class MyOrdersModel(
    @SerializedName("results")val result: List<MyOrdersResultData>,
    val total : Int,
)