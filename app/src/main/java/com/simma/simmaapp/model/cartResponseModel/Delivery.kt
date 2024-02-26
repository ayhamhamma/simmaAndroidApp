package com.simma.simmaapp.model.cartResponseModel

data class Delivery(
    val currency: String,
    val maximumDate: String,
    val minimumDate: String,
    val recipientName: String
)