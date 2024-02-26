package com.vs.simma.model.placeOrder

data class PaymentDetails(
    val IQDGrandTotal: Int,
    val IQDShippingCost: Int,
    val IQDTotal: Int,
    val IQDUnitShipmentFee: Int,
    val exchangeRate: Int
)