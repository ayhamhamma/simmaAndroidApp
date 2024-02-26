package com.vs.simma.model.placeOrder

data class Transaction(
    val __v: Int,
    val _id: String,
    val amount: Int,
    val createdAt: String,
    val currency: String,
    val gateway: String,
    val message: String,
    val onDeliveryDetails: OnDeliveryDetails,
    val simmaTxnId: String,
    val txnStatus: String,
    val updatedAt: String
)