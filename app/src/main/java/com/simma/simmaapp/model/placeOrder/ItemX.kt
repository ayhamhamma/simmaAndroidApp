package com.vs.simma.model.placeOrder

data class ItemX(
    val _id: String,
    val barcode: String,
    val color: String,
    val financialStatus: String,
    val id: String,
    val imageUrl: String,
    val iqdPrice: Double,
    val iqdUnitPrice: Double,
    val itemStatusUpdates: List<Any>,
    val name: String,
    val originalUnitPrice: Double,
    val price: Double,
    val quantity: Int,
    val size: String,
    val sizeVariants: List<String>,
    val sku: String,
    val status: String,
    val unitPrice: Double,
    val url: String,
    val usdOriginalUnitPrice: Double
)