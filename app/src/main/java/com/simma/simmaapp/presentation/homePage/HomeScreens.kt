package com.simma.simmaapp.presentation.homeScreen

import android.graphics.drawable.Icon
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.simma.simmaapp.R

sealed class HomeScreens(
    val route: String,
    @DrawableRes val selectedIcon: Int? = null,
    @DrawableRes val unselectedIcon : Int? = null,
    val title : String = "",

) {
    object HomeScreen : HomeScreens("HomeScreen",
        selectedIcon = R.drawable.selected_stores_icon,
        unselectedIcon = R.drawable.unselected_stores_icon,
        title = "Shop"
        )
    object WalletScreen : HomeScreens("wallet",
        selectedIcon = R.drawable.selected_wallet_icon,
        unselectedIcon = R.drawable.unselected_wallet_icon,
        title = "Wallet"
        )
    object UserProfileOptions : HomeScreens("profile",
        selectedIcon = R.drawable.selected_user_profile_icon,
        unselectedIcon = R.drawable.unselected_user_profile_icon,
        title = "Account"
        )
    object CategoriesScreen : HomeScreens("CategoriesScreen")
    object WebViewScreen : HomeScreens("WebViewScreen") {
        fun withArgs(vararg args: String): String {
            return buildString {
                append(route)
                args.forEach { arg ->
                    append("/$arg")
                }
            }
        }

    }

    object CartScreen : HomeScreens("CartScreen")
    object DeliveryAndPaymentScreen : HomeScreens("Checkout")
    object ConfirmOrderScreen : HomeScreens("ConfirmOrderScreen")
    object MyOrdersScreen : HomeScreens("MyOrdersScreen"){
        fun withArgs(vararg args: String): String {
            return buildString {
                append(route)
                args.forEach { arg ->
                    append("/$arg")
                }
            }
        }
    }
    object ProfileScreen : HomeScreens("ProfileScreen")
    object ReferAndEarnScreen : HomeScreens("ReferAndEarnScreen")
    object SendInquiryScreen : HomeScreens("SendInquiryScreen")
    object ContactUsScreen : HomeScreens("ContactUsScreen")
    object FrequentlyAskedQuestions : HomeScreens("FrequentlyAskedQuestions")


}