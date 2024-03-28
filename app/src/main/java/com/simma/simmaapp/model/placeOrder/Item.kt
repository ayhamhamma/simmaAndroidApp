package com.vs.simma.model.placeOrder

data class PlaceOrderItem(
    val color: String?,
    val imageUrl: String?,
    val name: String?,
    val originalUnitPrice: Double?,
    val quantity: Double?,
    val size: String?,
    val sizeVariants: List<String>?,
    val sku: String?,
    val url: String?
)