package com.vs.simma.model.loginUserModel

import androidx.annotation.Keep

@Keep
data class Token(
    val accessToken: String,
    val expiresIn: String,
    val refreshToken: String,
    val tokenType: String
)