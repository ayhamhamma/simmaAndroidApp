package com.simma.simmaapp.model.applyDiscountCode

data class ApplyDiscountCodeResponse(
    val appliedDiscount: AppliedDiscount,
    val isValid: Boolean
)