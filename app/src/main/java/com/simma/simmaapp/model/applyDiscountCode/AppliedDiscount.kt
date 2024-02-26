package com.simma.simmaapp.model.applyDiscountCode

data class AppliedDiscount(
    val category: String,
    val code: String,
    val description: Description,
    val iqdDiscountAmount: Double,
    val usdDiscountAmount: Double
)