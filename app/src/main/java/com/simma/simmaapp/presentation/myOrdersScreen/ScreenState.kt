package com.simma.simmaapp.presentation.myOrdersScreen

import com.simma.simmaapp.model.myOrdersModel.MyOrdersResultData


data class ScreenState(
    val items: List<MyOrdersResultData> = emptyList(),
    val endReached: Boolean = false,
    val page: Int = 1,
)