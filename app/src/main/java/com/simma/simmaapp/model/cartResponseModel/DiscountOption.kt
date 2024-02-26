package com.simma.simmaapp.model.cartResponseModel

data class DiscountOption(
    val category: String,
    val code: String,
    val description: Description,
    val iqdDiscountAmount: Double,
    val usdDiscountAmount: Double
)