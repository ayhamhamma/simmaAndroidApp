package com.vs.simma.model.placeOrder

data class PaymentDetails(
    val IQDGrandTotal: Double,
    val IQDShippingCost: Double,
    val IQDTotal: Double,
    val IQDUnitShipmentFee: Double,
    val exchangeRate: Double
)