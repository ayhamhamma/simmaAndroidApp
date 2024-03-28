package com.simma.simmaapp.model.loginModel

data class User(
    val __v: Int,
    val _id: String,
    val address: Address,
    val balance: Int,
    val bioKeys: List<Any>,
    val createdAt: String,
    val email: String,
    val firebaseId: String,
    val firstName: String,
    val id: String,
    val isBlocked: Boolean,
    val isEmailVerified: Boolean,
    val isReferralActive: Boolean,
    val isVerified: Boolean,
    val language: String,
    val lastName: String,
    val phoneNumber: String,
    val profileCompleted: Boolean,
    val redemptionCount: Int,
    val referralCode: String,
    val referralReport: ReferralReport,
    val updatedAt: String,
    val wallet: Wallet
)