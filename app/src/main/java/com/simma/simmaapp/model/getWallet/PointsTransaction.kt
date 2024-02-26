package com.simma.simmaapp.model.getWallet

data class PointsTransaction(
    val _id: String,
    val amount: Int,
    val createdAt: String,
    val details: Details,
    val type: String
)