package com.vs.simma.model.listingModel

import androidx.annotation.Keep

@Keep
data class Image(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val originalUrl: String ? = "",
    val type: String,
    val updatedAt: String
)