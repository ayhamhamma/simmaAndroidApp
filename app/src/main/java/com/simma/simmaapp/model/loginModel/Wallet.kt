package com.simma.simmaapp.model.loginModel

data class Wallet(
    val _id: String,
    val balance: Int,
    val freezedAt: Any,
    val isFreezed: Boolean,
    val pointsBalance: Int,
    val pointsTransactions: List<PointsTransaction>,
    val promotions: List<Promotion>,
    val transactions: List<Transaction>
)