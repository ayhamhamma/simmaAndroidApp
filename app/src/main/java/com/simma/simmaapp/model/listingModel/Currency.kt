package com.vs.simma.model.listingModel

import androidx.annotation.Keep

@Keep
data class Currency(
    val __v: Int,
    val _id: String,
    val autoUpdate: Boolean,
    val autoUpdatedAt: String,
    val code: String,
    val createdAt: String,
    val exchangeRate: Double,
    val exchangeRateUpdatedAt: String,
    val hasFailure: Boolean,
    val id: String,
    val margin: Int,
    val name: NameX,
    val updatedAt: String
)