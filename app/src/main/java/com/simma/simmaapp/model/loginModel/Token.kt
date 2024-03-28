package com.simma.simmaapp.model.loginModel

data class Token(
    val accessToken: String,
    val expiresIn: String,
    val refreshToken: String,
    val tokenType: String
)