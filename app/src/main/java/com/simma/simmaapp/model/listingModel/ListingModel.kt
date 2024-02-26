package com.vs.simma.model.listingModel

import androidx.annotation.Keep

@Keep
data class ListingModel(
    val results: List<Result>,
    val total: Int
)