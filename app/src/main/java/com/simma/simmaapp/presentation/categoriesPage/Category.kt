package com.simma.simmaapp.presentation.categoriesPage

import androidx.annotation.DrawableRes
import androidx.compose.runtime.MutableState
import com.vs.simma.model.listingModel.Result

data class Category(
    val id : String,
    val name : String,
    @DrawableRes val icon : Int,
    var merchants : MutableList<Result>
)
