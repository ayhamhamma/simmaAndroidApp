package com.simma.simmaapp.model.cartResponseModel

data class CartViewResponseModel(
    val _id: String,
    val commission: Commission,
    val currency: Currency,
    val deletedDiscounts: List<Any>,
    val delivery: Delivery,
    val deliveryPartner: DeliveryPartner,
    val discountOptions: List<DiscountOption>,
    val discounts: List<Any>,
    val grandTotal: Double,
    val isRounded: Boolean,
    val items: List<Item>,
    val merchantInfo: MerchantInfo,
    val paymentDetails: PaymentDetails,
    val shippingCost: Int,
    val status: String,
    val total: Double,
    val transactions: List<Any>,
    val unitShipmentFee: Int
)