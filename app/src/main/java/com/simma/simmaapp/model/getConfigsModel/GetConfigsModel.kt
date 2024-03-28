package com.simma.simmaapp.model.getConfigsModel

data class GetConfigsModel(
    val __v: Int,
    val _id: String,
    val adsBannerAr: Any,
    val adsBannerArId: String,
    val adsBannerEn: Any,
    val adsBannerEnId: String,
    val blockDuration: Int,
    val changeToWalletDays: Int,
    val createdAt: String,
    val deviceTypes: DeviceTypes,
    val enableBiometricLogin: Boolean,
    val enableOtpLogin: Boolean,
    val enableWhatsappLogin: Boolean,
    val id: String,
    val infoVideoUrl: InfoVideoUrl,
    val isAdsEnabled: Boolean,
    val loginWindow: Int,
    val maintenanceMode: Boolean,
    val maxLoginAttempts: Int,
    val minimumRequiredVersion: String,
    val supportEmail: String,
    val termsOfServiceUrl: TermsOfServiceUrl,
    val twitter: String,
    val updatedAt: String,
    val youtube: String
)