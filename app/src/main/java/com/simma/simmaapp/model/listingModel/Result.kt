package com.vs.simma.model.listingModel

import androidx.annotation.Keep

@Keep
data class Result(
    val __v: Int,
    val _id: String,
    val bannerAr: BannerAr,
    val bannerArId: String,
    val bannerEn: BannerEn,
    val bannerEnId: String,
    val cartUrl: String,
    val categorys: List<String>,
    val country: String,
    val coverImage: CoverImage,
    val coverImageId: String,
    val coverIsActive: Boolean,
    val createdAt: String,
    val currency: Currency,
    val currencyId: String,
    val deals: List<Deal>,
    val demoMode: Boolean,
    val discounts: List<Any>,
    val extraDeliveryPerItem: Double,
    val extraDeliveryPerOrder: Int,
    val extractionCode: String,
    val extractionPrice: String,
    val id: String,
    val image: Image? = null,
    val imageId: String,
    val isCommissionElligible: Boolean,
    val isWoocommerce: Boolean,
    val maximumDaysToDeliver: Int,
    val minimumDaysToDeliver: Int,
    val name: NameX,
    val onLoadCompleteCode: String,
    val paymentMethods: List<PaymentMethod>,
    val rank: Double,
    val referenceName: String,
    val scraperCodes: ScraperCodes,
    val shortDescription: ShortDescription,
    val status: String,
    val storeAbbreviation: String,
    val translation: Boolean,
    val updatedAt: String,
    val url: String,
    val cartCode : String,
    val subCategorys : List<String>
)