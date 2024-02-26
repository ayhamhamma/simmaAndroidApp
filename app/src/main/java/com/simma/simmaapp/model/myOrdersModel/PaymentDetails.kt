package com.simma.simmaapp.model.myOrdersModel

data class PaymentDetails(
    val IQDGrandTotal: Double,
    val IQDShippingCost: Double,
    val IQDTotal: Double,
    val IQDUnitShipmentFee: Double,
    val exchangeRate: Double
)