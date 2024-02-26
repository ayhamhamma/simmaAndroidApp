package com.simma.simmaapp.model.myOrdersModel

data class PaymentMethod(
    val _id: String,
    val active: Boolean,
    val freeDelivery: Boolean,
    val name: String
)