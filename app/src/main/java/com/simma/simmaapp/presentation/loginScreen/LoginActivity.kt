package com.simma.simmaapp.presentation.loginScreen

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.simma.simmaapp.R
import com.simma.simmaapp.presentation.OtpScreen.OtpScreen
import com.simma.simmaapp.presentation.deleveryAndPayment.DeliveryAndPaymentScreen
import com.simma.simmaapp.presentation.registrationView.RegistrationScreen
import com.simma.simmaapp.presentation.setupPassword.SetUpPassword
import com.simma.simmaapp.presentation.theme.SimmaAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
//            Scaffold(
//                bottomBar ={
//                    BottomNavigation {
//                        val navList = listOf(
//                            BottomNavItem.Home,
//                            BottomNavItem.Profile,
//                            BottomNavItem.Search
//                        )
//                        val navBackStackEntry by navController.currentBackStackEntryAsState()
//                        val currentRoute = navBackStackEntry?.destination?.route
//                        navList.forEach { item->
//                            BottomNavigationItem(
//                                selected = currentRoute == item.route,
//                                onClick = {
//                                    navController.navigate(item.route) {
//                                        popUpTo(navController.graph.startDestinationId)
//                                        launchSingleTop = true
//                                    }
//                                },
////                                icon = { Icon(item.icon, contentDescription = null) },
//                                label = { Text(item.label) },
//                                icon = Icon
//                            )
//                        }
//                    }

//                }
//            ) {
//                padding ->

                NavHost(
                    navController = navController,
                    startDestination = Screen.LoginScreen.route
                ) {
                    composable(route = Screen.LoginScreen.route){
                        LoginScreen(navController)
                    }
                    composable(route = Screen.RegistrationScreen.route){
                        RegistrationScreen(navController)
                    }
                    composable(route = Screen.OtpScreen.route){
                        OtpScreen(navController)
                    }
                    composable(route = Screen.SetPasswordScreen.route){
                        SetUpPassword(navController)
                    }

                }
//                DeliveryAndPaymentScreen()
            }
//            }


    }
    sealed class BottomNavItem(val route: String, val label: String, @DrawableRes val icon :Int) {
        object Home : BottomNavItem("home","home",R.drawable.merchants)
        object Search : BottomNavItem("wallet","wallet",R.drawable.wallet_icon)
        object Profile : BottomNavItem("my_account", "my_account",R.drawable.account_icon)
    }
}

