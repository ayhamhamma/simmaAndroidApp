package com.vs.simma.model.loginUserModel

import androidx.annotation.Keep

@Keep
data class User(
    val __v: Int,
    val _id: String,
    val authenticationProvider: String,
    val balance: Int,
    val createdAt: String,
    val email: String,
    val firebaseId: String,
    val firstName: String,
    val id: String,
    val isBlocked: Boolean,
    val isEmailVerified: Boolean,
    val isVerified: Boolean,
    val lastName: String,
    val phoneNumber: String,
    val referralCode: String,
    val updatedAt: String
)