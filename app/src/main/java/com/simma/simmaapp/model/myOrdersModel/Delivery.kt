package com.simma.simmaapp.model.myOrdersModel

data class Delivery(
    val address: String,
    val currency: String,
    val fees: Double,
    val maximumDate: String,
    val minimumDate: String,
    val recipientName: String
)