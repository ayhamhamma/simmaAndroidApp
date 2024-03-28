package com.simma.simmaapp.model.myOrdersModel

data class DiscountModel(
    val _id: String,
    val adminUserId: Any,
    val category: String,
    val code: String,
    val commissionPercentage: Double,
    val commissionValue: Double,
    val createdAt: String,
    val description: Description,
    val id: String,
    val iqdValue: Double,
    val quantity: Double,
    val type: String,
    val updatedAt: String,
    val usdValue: Double,
    val userType: String,
    val value: Double
)