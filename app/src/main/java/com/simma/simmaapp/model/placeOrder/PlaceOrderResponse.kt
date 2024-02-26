package com.vs.simma.model.placeOrder

data class PlaceOrderResponse(
    val __v: Int,
    val _id: String,
    val commission: Commission,
    val createdAt: String,
    val currency: Currency,
    val customerAddress: CustomerAddressX,
    val deletedDiscounts: List<Any>,
    val delivery: Delivery,
    val discounts: List<Any>,
    val externalStatus: String,
    val financialStatus: String,
    val grandTotal: Double,
    val id: String,
    val isRounded: Boolean,
    val items: List<ItemX>,
    val merchantId: String,
    val orderNumber: String,
    val paymentDetails: PaymentDetails,
    val paymentMethod: String,
    val shippingCost: Int,
    val status: String,
    val statusUpdates: List<Any>,
    val total: Double,
    val transactions: List<Transaction>,
    val unitShipmentFee: Int,
    val updatedAt: String,
    val userId: String
)