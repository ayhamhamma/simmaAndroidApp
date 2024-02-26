package com.simma.simmaapp.model.myOrdersModel

data class Deal(
    val _id: String,
    val createdAt: String,
    val description: Description,
    val id: String,
    val isActive: Boolean,
    val orderSort: Int,
    val updatedAt: String
)