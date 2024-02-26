package com.vs.simma.model.auth

import androidx.annotation.Keep

@Keep
data class SendOtpRequestBody(
    val encryptedData : String
)
