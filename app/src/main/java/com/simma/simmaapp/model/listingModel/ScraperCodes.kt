package com.vs.simma.model.listingModel

import androidx.annotation.Keep

@Keep
data class ScraperCodes(
    val cartItem: String,
    val color: String,
    val itemCount: String,
    val itemImage: String,
    val itemLabel: String,
    val itemLink: String,
    val itemPrice: String,
    val itemSize: String,
    val linkTwo: String,
    val sizeTwo: String,
    val totalPrice: String
)