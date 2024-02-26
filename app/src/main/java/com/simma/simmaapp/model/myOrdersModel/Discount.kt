package com.simma.simmaapp.model.myOrdersModel

data class Discount(
    val _id: String,
    val adminUserId: String,
    val category: String,
    val code: String,
    val commissionPercentage: Int,
    val createdAt: String,
    val description: Description,
    val id: String,
    val isActive: Boolean,
    val quantity: Int,
    val type: String,
    val updatedAt: String,
    val userType: String,
    val value: Double
)