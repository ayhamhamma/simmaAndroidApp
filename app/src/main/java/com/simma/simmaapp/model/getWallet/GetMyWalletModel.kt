package com.simma.simmaapp.model.getWallet

data class GetMyWalletModel(
    val _id: String,
    val balance: Double,
    val freezedAt: Any,
    val isFreezed: Boolean,
    val pointsBalance: Int,
    val pointsTransactions: List<PointsTransaction>,
    val promotions: List<WalletPromotions>,
    val transactions: List<Any>
)