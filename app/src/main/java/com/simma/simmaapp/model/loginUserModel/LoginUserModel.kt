package com.vs.simma.model.loginUserModel

import androidx.annotation.Keep

@Keep
data class LoginUserModel(
    val token: Token,
    val user: User
)