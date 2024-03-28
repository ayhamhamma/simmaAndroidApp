package com.simma.simmaapp.model.loginModel

data class Transaction(
    val _id: String,
    val amount: Int,
    val createdAt: String,
    val details: DetailsX,
    val type: String
)