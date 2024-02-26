package com.simma.simmaapp.model.sendInquiry

data class SendInquiryRequestBody(
    val categoryId: String,
    val createdBy: String,
    val desc: String
)