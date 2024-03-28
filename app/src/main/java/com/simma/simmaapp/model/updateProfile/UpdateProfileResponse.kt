package com.simma.simmaapp.model.updateProfile

data class UpdateProfileResponse(
    val __v: Int,
    val _id: String,
    val address: AddressX? = null,
    val balance: Int,
    val createdAt: String,
    val email: String,
    val firebaseId: String,
    val firstName: String,
    val id: String,
    val image: Any,
    val isBlocked: Boolean,
    val isEmailVerified: Boolean,
    val isVerified: Boolean,
    val language: String,
    val lastName: String,
    val phoneNumber: String,
    val referralCode: String,
    val updatedAt: String
)