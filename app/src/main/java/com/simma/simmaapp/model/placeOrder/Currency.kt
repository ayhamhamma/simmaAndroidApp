package com.vs.simma.model.placeOrder

data class Currency(
    val code: String,
    val exchangeRate: Int,
    val margin: Int,
    val name: Name
)