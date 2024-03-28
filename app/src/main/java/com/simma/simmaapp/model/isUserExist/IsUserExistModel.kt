package com.simma.simmaapp.model.isUserExist

data class IsUserExistModel(
    val exists: Boolean,
    val isTempUser: Boolean,
    val profileCompleted : Boolean
)