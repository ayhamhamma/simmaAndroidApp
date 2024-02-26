package com.simma.simmaapp.model.applyDiscountCode

import com.simma.simmaapp.model.cartResponseModel.CartViewResponseModel

data class ApplyDiscountModel(
    val includeCouponCodeTypes : Boolean,
    val order : CartViewResponseModel,
    val couponCode : String
)
