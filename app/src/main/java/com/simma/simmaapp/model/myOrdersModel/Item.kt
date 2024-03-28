package com.simma.simmaapp.model.myOrdersModel

data class Item(
    val _id: String,
    val barcode: String,
    val color: String,
    val financialStatus: String,
    val id: String,
    val imageUrl: String,
    val iqdPrice: Double,
    val iqdUnitPrice: Double,
    val name: String,
    val originalUnitPrice: Double,
    val price: Double,
    val quantity: Double,
    val size: String,
    val sizeVariants: List<String>,
    val sku: String,
    val status: String,
    val unitPrice: Double,
    val url: String,
    val usdOriginalUnitPrice: Double
)