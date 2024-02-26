package com.vs.simma.model.placeOrder

data class Delivery(
    val address: String,
    val currency: String,
    val fees: Int,
    val maximumDate: String,
    val minimumDate: String,
    val recipientName: String
)