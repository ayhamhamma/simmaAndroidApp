package com.simma.simmaapp.model.myOrdersModel

data class MyOrdersResultData(
    val __v: Double,
    val _id: String,
    val commission: Commission,
    val createdAt: String,
    val delivery: Delivery,
    val deliveryPartner: DeliveryPartner,
    val discounts: List<DiscountModel>,
    val externalStatus: String,
    val financialStatus: String,
    val grandTotal: Double,
    val id: String,
    val isRounded: Boolean,
    val items: List<Item>,
    val merchant: Merchant,
    val merchantInfo: MerchantInfo,
    val orderNumber: String,
    val paymentDetails: PaymentDetails,
    val paymentMethod: String,
    val shippingCost: Double,
    val total: Double,
    val unitShipmentFee: Double,
    val updatedAt: String,
    val user: User
)