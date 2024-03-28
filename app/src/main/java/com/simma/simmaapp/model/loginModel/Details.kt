package com.simma.simmaapp.model.loginModel

data class Details(
    val _id: String,
    val adminUserEmail: String,
    val adminUserId: Any,
    val currency: String,
    val description: Description,
    val orderId: String,
    val orderNumber: String,
    val subType: String,
    val userReferralData: UserReferralData
)