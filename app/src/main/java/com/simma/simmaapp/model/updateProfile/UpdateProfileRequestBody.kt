package com.simma.simmaapp.model.updateProfile

import android.provider.ContactsContract.CommonDataKinds.Email
import org.intellij.lang.annotations.Language

data class UpdateProfileRequestBody(
    val firstName :String,
    val lastName : String,
    val email: String?,
    val language: String,
    val address: Address

)
