package com.simma.simmaapp.model.cartResponseModel

data class Currency(
    val code: String,
    val exchangeRate: Double,
    val margin: Double,
    val name: Name
)