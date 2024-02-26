package com.simma.simmaapp.presentation.homePage


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.simma.simmaapp.R
import com.simma.simmaapp.presentation.accountPage.AccountScreen
import com.simma.simmaapp.presentation.cartPage.CartScreen
import com.simma.simmaapp.presentation.categoriesPage.CategoryScreen
import com.simma.simmaapp.presentation.confirmOrderPage.ConfirmOrderScreen
import com.simma.simmaapp.presentation.deleveryAndPayment.DeliveryAndPaymentScreen
import com.simma.simmaapp.presentation.frequentlyAskedQuestionsScreen.FrequentlyAskedQuestionsScreen
import com.simma.simmaapp.presentation.homePage.ui.theme.BackgroundLightGrey
import com.simma.simmaapp.presentation.homePage.ui.theme.SimmaAppTheme
import com.simma.simmaapp.presentation.homePage.ui.theme.Yellow
import com.simma.simmaapp.presentation.homeScreen.HomeScreens
import com.simma.simmaapp.presentation.myOrdersScreen.MyOrdersScreen
import com.simma.simmaapp.presentation.profilePage.ProfileScreen
import com.simma.simmaapp.presentation.sendInquiryScreen.SendInquiryScreen
import com.simma.simmaapp.presentation.theme.MedDarkGrey
import com.simma.simmaapp.presentation.theme.ShadowGrey
import com.simma.simmaapp.presentation.webview.WebView
import com.simma.simmaapp.utils.ModifierUtil.dropShadow
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.apply {
            navigationBarColor = ContextCompat.getColor(context, R.color.statusAndNavigation)
            statusBarColor = ContextCompat.getColor(context, R.color.statusAndNavigation)
        }
        setContent {
            SimmaAppTheme {
                val navController = rememberNavController()
                Box(modifier = Modifier.fillMaxSize()) {
                    NavHost(
                        navController = navController,
                        startDestination = HomeScreens.HomeScreen.route
                    ) {
                        composable(HomeScreens.HomeScreen.route) {
                            HomePage(navController)
                        }
                        composable(HomeScreens.CategoriesScreen.route) {
                            CategoryScreen(navController)
                        }
                        composable(
                            route = HomeScreens.WebViewScreen.route + "/{shopUrl}/{extractionCode}/{cartUrl}/{id}",
                            arguments = listOf(
                                navArgument("shopUrl") {
                                    type = NavType.StringType
                                    defaultValue = "https://m.shein.com/ar/"
                                    nullable = false
                                },
                                navArgument("extractionCode") {
                                    type = NavType.StringType
                                    defaultValue = "window.CartVue.carts"
                                },
                                navArgument("cartUrl") {
                                    type = NavType.StringType
                                    defaultValue = "https://m.shein.com/ar/cart"
                                },
                                navArgument("id") {
                                    type = NavType.StringType
                                    defaultValue = "id"
                                }
                            )
                        ) { backStackEntry ->
                            val shopUrl = backStackEntry.arguments?.getString("shopUrl")
                            val extractionCode =
                                backStackEntry.arguments?.getString("extractionCode")
                            val cartUrl = backStackEntry.arguments?.getString("cartUrl")
                            val id = backStackEntry.arguments?.getString("id")

                            WebView(
                                navController = navController,
                                shopUrl ?: "",
                                cartUrl ?: "",
                                extractionCode ?: "",
                                id ?: ""
                            )
                        }

                        composable(route = HomeScreens.CartScreen.route) {
                            CartScreen(navController)
                        }
                        composable(route = HomeScreens.DeliveryAndPaymentScreen.route) {
                            DeliveryAndPaymentScreen(navController)
                        }
                        composable(route = HomeScreens.ConfirmOrderScreen.route) {
                            ConfirmOrderScreen(navController)
                        }
                        composable(route = HomeScreens.MyOrdersScreen.route+"/{fromHome}",
                            arguments = listOf(
                                navArgument("fromHome") {
                                    type = NavType.BoolType
                                    defaultValue = false
                                    nullable = false
                                }
                            )
                            ) {
                                backStackEntry ->
                            val fromHome = backStackEntry.arguments?.getBoolean("fromHome")
                            MyOrdersScreen(fromHome = fromHome ?: false)
                        }
                        composable(route = HomeScreens.WalletScreen.route) {
                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .background(Color.Green)
                            ) {

                            }
                        }
                        composable(route = HomeScreens.UserProfileOptions.route) {
                            AccountScreen(navController)
                        }
                        composable(route = HomeScreens.ProfileScreen.route) {
                            ProfileScreen(navController)
                        }
                        composable(route = HomeScreens.ReferAndEarnScreen.route) {
                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .background(Color.Red)
                            ) {

                            }
                        }
                        composable(route = HomeScreens.SendInquiryScreen.route){
                            SendInquiryScreen(navController = navController)
                        }
                        composable(route = HomeScreens.ContactUsScreen.route){
                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .background(Color.Blue)
                            ) {

                            }
                        }
                        composable(route = HomeScreens.FrequentlyAskedQuestions.route){
                            FrequentlyAskedQuestionsScreen()
                        }
                    }
                    // bottom nav hide and show logic
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    if (currentDestination?.hierarchy?.any {
                            it.route == HomeScreens.HomeScreen.route ||
                                    it.route == HomeScreens.WalletScreen.route ||
                                    it.route == HomeScreens.UserProfileOptions.route
                        } == true) {
                        BottomNav(
                            navController = navController,
                            Modifier.align(Alignment.BottomCenter),
                            currentDestination
                        )
                    }

                }

            }
        }
    }
}


@Composable
fun BottomNav(
    navController: NavController,
    modifier: Modifier,
    currentDestination: NavDestination?
) {
    Box(
        modifier
            .fillMaxWidth()
            .dropShadow(
                color = ShadowGrey,
                blurRadius = 5.dp,
                borderRadius = 25.dp
            )
            .clip(
                RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
            )
            .background(BackgroundLightGrey)


    ) {
        val screens = listOf(
            HomeScreens.HomeScreen,
            HomeScreens.WalletScreen,
            HomeScreens.UserProfileOptions
        )

        BottomNavigation(backgroundColor = BackgroundLightGrey) {
            screens.forEach {
                AddItem(it, currentDestination, navController)
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: HomeScreens,
    currentDestination: NavDestination?,
    navController: NavController
) {
    BottomNavigationItem(
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = { navController.navigate(screen.route) },
        icon = {
            if (currentDestination?.hierarchy?.any {
                    it.route == screen.route
                } == true) {
                Column {
                    Image(
                        painter = painterResource(id = screen.selectedIcon!!),
                        contentDescription = screen.title,
                        modifier = Modifier.padding(bottom = 5.dp, top = 10.dp)
                    )
                }

            } else {
                Column {
                    Image(
                        painter = painterResource(id = screen.unselectedIcon!!),
                        contentDescription = screen.title,
                        modifier = Modifier.padding(bottom = 5.dp, top = 10.dp)
                    )
                }

            }
        },
        label = {
            Text(text = screen.title,
                color =
                if (currentDestination?.hierarchy?.any {
                        it.route == screen.route
                    } == true) {
                    Yellow
                } else {
                    MedDarkGrey
                },
                fontFamily = FontFamily(Font(R.font.font_med)), fontSize = 12.sp
            )
        }
    )
}


