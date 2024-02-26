package com.simma.simmaapp.model.getWallet

data class Details(
    val _id: String,
    val adminUserEmail: String,
    val adminUserId: Any,
    val currency: String,
    val description: Description,
    val subType: String,
    val userReferralData: UserReferralData
)