package com.simma.simmaapp.presentation.homePage


import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.simma.simmaapp.R
import com.simma.simmaapp.model.myOrdersModel.Item
import com.simma.simmaapp.model.myOrdersModel.PaymentMethod
import com.simma.simmaapp.presentation.StoreInfoPage.StoreInfoScreen
import com.simma.simmaapp.presentation.accountPage.AccountScreen
import com.simma.simmaapp.presentation.cartPage.CartScreen
import com.simma.simmaapp.presentation.categoriesPage.CategoriesSearchViewModel
import com.simma.simmaapp.presentation.categoriesPage.CategoryScreen
import com.simma.simmaapp.presentation.confirmOrderPage.ConfirmOrderScreen
import com.simma.simmaapp.presentation.deleveryAndPayment.DeliveryAndPaymentScreen
import com.simma.simmaapp.presentation.frequentlyAskedQuestionsScreen.FrequentlyAskedQuestionsScreen
import com.simma.simmaapp.presentation.helpCenterScreen.HelpCenterScreen
import com.simma.simmaapp.presentation.homePage.ui.theme.BackgroundLightGrey
import com.simma.simmaapp.presentation.homePage.ui.theme.SimmaAppTheme
import com.simma.simmaapp.presentation.homePage.ui.theme.Yellow
import com.simma.simmaapp.presentation.homeScreen.HomeScreens
import com.simma.simmaapp.presentation.loginScreen.LoginActivity
import com.simma.simmaapp.presentation.myOrdersScreen.MyOrdersScreen
import com.simma.simmaapp.presentation.orderItemDetails.OrderDetailsViewModel
import com.simma.simmaapp.presentation.orderItemDetails.OrderItemDetails
import com.simma.simmaapp.presentation.profilePage.ProfileScreen
import com.simma.simmaapp.presentation.sendInquiryScreen.SendInquiryScreen
import com.simma.simmaapp.presentation.theme.Dimen.PADDING
import com.simma.simmaapp.presentation.theme.ErrorToastBackgroundColor
import com.simma.simmaapp.presentation.theme.MedDarkGrey
import com.simma.simmaapp.presentation.theme.ShadowGrey
import com.simma.simmaapp.presentation.webview.WebView
import com.simma.simmaapp.presentation.webview.WebViewViewModel
import com.simma.simmaapp.utils.Constants
import com.simma.simmaapp.utils.Constants.NAVIGATE_TO_CART
import com.simma.simmaapp.utils.Helpers
import com.simma.simmaapp.utils.Helpers.isLoggedIn
import com.simma.simmaapp.utils.ModifierUtil.dropShadow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private var isOrdersScreen: Boolean = false

    companion object {

        var showErrorToast = MutableStateFlow(false)
        var showSuccessToast = MutableStateFlow(false)
        var toastMessageText = MutableStateFlow("")

        /**
         * function to show toast for all Home Screens
         * @param type true -> Success false -> Error
         * @param text the toast message
         */
        fun showToast(
            type: Boolean,
            text: String,
        ) {
            if (type) {

                GlobalScope.launch {
                    toastMessageText.update {
                        text
                    }
                    showSuccessToast.update {
                        true
                    }
                    delay(2000)

                    showSuccessToast.update {
                        false
                    }
                }
            } else {
                GlobalScope.launch {

                    toastMessageText.update {
                        text
                    }
                    showErrorToast.update {
                        true
                    }

                    delay(2000)
                    showErrorToast.update {
                        false
                    }
                }
            }

        }
    }

    private var webViewModel: WebViewViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Helpers.getNumberOfTimes(this) == 2) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                        1000212
                    )
                } else {
                    // Permission already granted
                }
            } else {
                // For versions below Android 13, notification permission is not required
            }
        }

        window.apply {
            navigationBarColor = ContextCompat.getColor(context, R.color.statusAndNavigation)
            statusBarColor = ContextCompat.getColor(context, R.color.statusAndNavigation)
        }
        setContent {
            SimmaAppTheme {
                navController = rememberNavController()
                Box(modifier = Modifier.fillMaxSize()) {

                    NavHost(
                        navController = navController,
                        startDestination = HomeScreens.HomeScreen.route
                    ) {
                        composable(HomeScreens.HomeScreen.route) {
                            isOrdersScreen = false
                            HomePage(navController)
                        }
                        composable(HomeScreens.CategoriesScreen.route) {
                            isOrdersScreen = false
                            val viewModel : CategoriesSearchViewModel = hiltViewModel()
                            CategoryScreen(navController,
                                viewModel.search,
                                viewModel.categoriesList,
                                viewModel.merchantsList,
                                viewModel.searchState
                            ){
                                viewModel.changeSearchText(it)
                            }
                        }
                        composable(
                            route = HomeScreens.WebViewScreen.route + "/{shopUrl}/{cartUrl}/{id}",
                            arguments = listOf(
                                navArgument("shopUrl") {
                                    type = NavType.StringType
                                    defaultValue = "https://m.shein.com/ar/"
                                    nullable = false
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
                            isOrdersScreen = false
                            val shopUrl = backStackEntry.arguments?.getString("shopUrl")
                            val cartUrl = backStackEntry.arguments?.getString("cartUrl")
                            val id = backStackEntry.arguments?.getString("id")
                            NAVIGATE_TO_CART =
                                {
                                    navController.navigate(HomeScreens.CartScreen.route) {
                                        popUpTo(HomeScreens.CartScreen.route) {
                                            inclusive = true
                                        }
                                    }
                                }
                            Constants.selectedUrl = shopUrl!!
                            Constants.CART_URL = cartUrl!!
                            Constants.MERCHANT_ID = id!!
                            Constants.SHOP_ID = id
                            webViewModel = hiltViewModel()
                            WebView(
                                navController = navController,
                                shopUrl ?: "",
                                cartUrl ?: "",
                                id ?: "",
                                webViewModel!!
                            )

                        }

                        composable(route = HomeScreens.CartScreen.route) {
                            isOrdersScreen = false
                            CartScreen(navController)
                        }
                        composable(route = HomeScreens.DeliveryAndPaymentScreen.route) {
                            isOrdersScreen = false
                            DeliveryAndPaymentScreen(navController)
                        }
                        composable(route = HomeScreens.ConfirmOrderScreen.route) {
                            isOrdersScreen = false
                            ConfirmOrderScreen(navController)
                        }
                        composable(route = HomeScreens.MyOrdersScreen.route + "/{fromHome}",
                            arguments = listOf(
                                navArgument("fromHome") {
                                    type = NavType.BoolType
                                    defaultValue = false
                                    nullable = false
                                }
                            )
                        ) { backStackEntry ->
                            isOrdersScreen = true
                            val fromHome = backStackEntry.arguments?.getBoolean("fromHome")
                            MyOrdersScreen(fromHome = fromHome ?: false, navController)
                        }
                        composable(route = HomeScreens.WalletScreen.route) {
                            isOrdersScreen = false
                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .background(Color.Green)
                            ) {

                            }
                        }
                        composable(route = HomeScreens.UserProfileOptions.route) {
                            isOrdersScreen = false
                            AccountScreen(navController)
                        }
                        composable(route = HomeScreens.ProfileScreen.route) {
                            isOrdersScreen = false
                            ProfileScreen(navController)
                        }
                        composable(route = HomeScreens.ReferAndEarnScreen.route) {
                            isOrdersScreen = false
                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .background(Color.Red)
                            ) {

                            }
                        }
                        composable(route = HomeScreens.SendInquiryScreen.route) {
                            isOrdersScreen = false
                            SendInquiryScreen(navController = navController)
                        }
                        composable(route = HomeScreens.ContactUsScreen.route) {
                            isOrdersScreen = false
                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .background(Color.Blue)
                            ) {

                            }
                        }
                        composable(route = HomeScreens.FrequentlyAskedQuestions.route) {
                            isOrdersScreen = false
                            FrequentlyAskedQuestionsScreen()
                        }
                        composable(
                            route = HomeScreens.OrderDetailsScreen.route
                        ) {
                            isOrdersScreen = false
                            val viewModel: OrderDetailsViewModel = hiltViewModel()
                            OrderItemDetails(
                                viewModel.orderNumber,
                                date = viewModel.date,
                                paymentMethod = viewModel.paymentMethod,
                                onBackPress = { onBackPressedDispatcher.onBackPressed() },
                                onCancelOrderPress = {
                                    viewModel.cancelOrderOrCancelItem()

                                },
                                itemsList = viewModel.itemsList,
                                isExpanded = viewModel.isExpanded,
                                onItemClick = { itemUrl: String,
                                                cartUrl: String,
                                                merchantId: String,
                                                extractionCode: String,
                                                paymentMethodList: List<PaymentMethod>,
                                                item: Item ->
                                    val url = URLEncoder.encode(
                                        itemUrl,
                                        StandardCharsets.UTF_8.toString()
                                    )
                                    val encodedCartUrl =
                                        URLEncoder.encode(
                                            cartUrl,
                                            StandardCharsets.UTF_8.toString()
                                        )
                                    Constants.EXTRACTION_CODE = extractionCode
                                    navController.navigate(
                                        HomeScreens.WebViewScreen.withArgs(
                                            url,
                                            encodedCartUrl,
                                            merchantId
                                        )
                                    )
                                    Constants.PAYMENT_METHODS = paymentMethodList.map {
                                        com.vs.simma.model.listingModel.PaymentMethod(
                                            _id = it._id,
                                            active = it.active,
                                            freeDelivery = it.freeDelivery,
                                            name = it.name
                                        )
                                    }

                                },
                                onItemDelete = {
                                    //todo delete Item API
                                },
                                onExpandChange = {
                                    viewModel.isExpanded = !viewModel.isExpanded
                                },
                                numberOfItem = viewModel.numberOfItem,
                                totalInsight = viewModel.totalInsight,
                                shipping = viewModel.shipping,
                                grandTotal = viewModel.grandTotal,
                                discountList = viewModel.discounts,
                                cartUrl = viewModel.cartUrl,
                                extractionCode = viewModel.extractionCode,
                                merchantId = viewModel.merchantId,
                                paymentMethodList = viewModel.paymentMethodsList,
                                orderId = viewModel.orderId,
                                onCancelItem = {
                                    viewModel.cancelOrderOrCancelItem(it)
                                },
                                onUnCancelItem = {

                                },
                                externalStatus = viewModel.externalStatus,
                                showCancelPopUp = viewModel.isCancelOrderPopUpVisible,
                                onCancelPopUpVisibilityChange = {
                                    viewModel.isCancelOrderPopUpVisible = it
                                },
                                showCancelItemPopUp = viewModel.showCancelItemPopUp,
                                onCancelOrderItemVisibilityChange = {
                                    viewModel.showCancelItemPopUp = it
                                }
                            )
                        }
                        composable(route = HomeScreens.HelpCenterScreen.route) {
                            isOrdersScreen = false
                            HelpCenterScreen(navController)
                        }
                        composable(route = HomeScreens.StoreInfoScreen.route) {
                            isOrdersScreen = false
                            StoreInfoScreen()
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
                    AnimatedVisibility(
                        visible = (showSuccessToast.collectAsState().value || showErrorToast.collectAsState().value),
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            SuccessToast(
                                text = toastMessageText.collectAsState().value,
                                show = showSuccessToast.collectAsState().value,
                                modifier = Modifier.align(Alignment.Center)
                            )
                            ErrorToast(
                                text = toastMessageText.collectAsState().value,
                                show = showErrorToast.collectAsState().value,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }


                }

            }

        }
    }

    override fun onBackPressed() {
        if (webViewModel?.webView?.canGoBack() ?: false) {
            webViewModel?.webView?.goBack()
        } else {
            if (isOrdersScreen) {
                navController.navigate(HomeScreens.UserProfileOptions.route) {
                    popUpTo(HomeScreens.HomeScreen.route) {
                        inclusive = false
                    }
                }
            } else {
                super.onBackPressed()
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
        val context = LocalContext.current
        if (isLoggedIn(context)) {
            BottomNavigation(backgroundColor = BackgroundLightGrey) {
                screens.forEach {
                    AddItem(it, currentDestination, navController)
                }
            }
        } else {
            NotLoggedInBottomBar()
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

@Preview
@Composable
fun NotLoggedInBottomBar() {
    val context = LocalContext.current
    Row(
        horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .fillMaxWidth()
            .padding(start = PADDING, end = PADDING, top = PADDING)
    ) {
        Text(
            text = "Start your Simma journey\n and shop the world",
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.font_med)),
                fontWeight = FontWeight(700),
                color = Color(0xFF2D2D2D),
            )
        )
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    context.startActivity(Intent(context, LoginActivity::class.java).apply {
                    })
                }
                .background(Yellow)
                .padding(start = 24.dp, end = 24.dp, top = 10.dp, bottom = 10.dp)
        ) {
            Text(
                text = "Sign up",

                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 24.sp,
                    fontFamily = FontFamily(Font(R.font.font_med)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF2D2D2D),
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SuccessToast(
    text: String = "",
    show: Boolean = false,
    modifier: Modifier = Modifier
) {
    val painter = painterResource(id = R.drawable.success_icon_toast)
    AnimatedVisibility(visible = show, enter = fadeIn(), exit = fadeOut()) {
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Row(
                modifier
                    .padding(PADDING)
                    .clip(RoundedCornerShape(24.dp))
                    .background(ErrorToastBackgroundColor)
                    .padding(
                        PADDING
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painter,
                    contentDescription = "error",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = text,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.font_med)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFFFFFF),
                    )
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ErrorToast(
    text: String = "",
    show: Boolean = false,
    modifier: Modifier = Modifier
) {
    val painter = painterResource(id = R.drawable.error_toast_icon)
    AnimatedVisibility(visible = show, enter = fadeIn(), exit = fadeOut()) {
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Row(
                modifier
                    .padding(PADDING)
                    .clip(RoundedCornerShape(24.dp))
                    .background(ErrorToastBackgroundColor)
                    .padding(
                        PADDING
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painter,
                    contentDescription = "error",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = text,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.font_med)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFFFFFF),
                    )
                )
            }
        }
    }
}



