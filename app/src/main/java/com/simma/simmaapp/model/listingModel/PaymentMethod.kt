package com.vs.simma.model.listingModel

import androidx.annotation.Keep

@Keep
data class PaymentMethod(
    val _id: String,
    val active: Boolean,
    val freeDelivery: Boolean,
    val name: String
)