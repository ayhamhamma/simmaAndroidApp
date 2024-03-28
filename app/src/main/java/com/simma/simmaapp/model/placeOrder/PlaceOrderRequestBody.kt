package com.vs.simma.model.placeOrder

data class PlaceOrderRequestBody(
    val applyFreeShipping: Boolean,
    val customerAddress: CustomerAddress,
    val items: List<PlaceOrderItem>,
    val merchantId: String,
    val paymentMethod: String,
    val couponCode : String,
    val otpToken : String? = null
)
data class PlaceOrderNoCouponRequestBody(
    val applyFreeShipping: Boolean,
    val customerAddress: CustomerAddress,
    val items: List<PlaceOrderItem>,
    val merchantId: String,
    val paymentMethod: String,
    val otpToken : String? = null
)


