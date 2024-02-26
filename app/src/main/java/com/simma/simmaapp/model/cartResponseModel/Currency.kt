package com.simma.simmaapp.model.cartResponseModel

data class Currency(
    val code: String,
    val exchangeRate: Int,
    val margin: Int,
    val name: Name
)