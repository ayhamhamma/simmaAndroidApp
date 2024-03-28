package com.simma.simmaapp.presentation.homePage

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.simma.simmaapp.R
import com.simma.simmaapp.presentation.categoriesPage.CategoriesItem
import com.simma.simmaapp.presentation.homePage.ui.theme.BackgroundLightGrey
import com.simma.simmaapp.presentation.homeScreen.HomeScreens
import com.simma.simmaapp.presentation.theme.CheckoutAppBarColor
import com.simma.simmaapp.presentation.theme.Dimen.PADDING
import com.simma.simmaapp.presentation.theme.OnBoardingBabyBlue
import com.simma.simmaapp.utils.Constants.CATEGORIES_LIST
import com.simma.simmaapp.utils.Constants.DETAILS_COUNTRY
import com.simma.simmaapp.utils.Constants.DETAILS_COVER_IMAGE
import com.simma.simmaapp.utils.Constants.DETAILS_DEALS
import com.simma.simmaapp.utils.Constants.DETAILS_DESCRIPTION
import com.simma.simmaapp.utils.Constants.DETAILS_IMAGE
import com.simma.simmaapp.utils.Constants.DETAILS_ITEM_PRICED
import com.simma.simmaapp.utils.Constants.DETAILS_MAX_DAYS
import com.simma.simmaapp.utils.Constants.DETAILS_MIN_DAYS
import com.simma.simmaapp.utils.Constants.DETAILS_NAME
import com.simma.simmaapp.utils.Constants.EXTRACTION_CODE
import com.simma.simmaapp.utils.Constants.PAYMENT_METHODS
import com.simma.simmaapp.utils.ModifierUtil.dropShadow
import com.simma.simmaapp.utils.MyScrollableTabRow
import com.vs.simma.model.listingModel.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Preview(showBackground = true)
@Composable
fun HomePage(
    navController: NavController? = null
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val itemWidth = screenWidth


    val interactionSource = remember { MutableInteractionSource() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    ((LocalContext.current) as HomeActivity).window.apply {
        navigationBarColor = ContextCompat.getColor(context, R.color.statusAndNavigation)
        statusBarColor = ContextCompat.getColor(context, R.color.statusAndNavigation)
    }
    val viewModel: HomeViewModel = hiltViewModel()
    Scaffold { padding ->

        var indicatorState by remember {
            mutableStateOf(0)
        }

        var scrollState = rememberLazyListState(0)
        Column {

            AppBar(
                Modifier.dropShadow(

                ),
                navController
            )

            if ((ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED)
            )
                EnableNotificationBanner()
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {

                item {
                    Spacer(modifier = Modifier.size(PADDING))
                }
                item {

                    val density = LocalDensity.current
                    val itemSizePx = with(density) { itemWidth.toPx() }
                    DisposableEffect(true) {
                        var isInMain = true
                        val job = coroutineScope.launch {
                            while (true) {
                                if (isInMain) {
                                    try {
                                        delay(2000) // 1000 milliseconds = 1 second
//                                        scrollState.animateScrollToItem(scrollState.firstVisibleItemIndex + 1)

                                        val itemsScrollCount = 1
                                        scrollState.animateScrollBy(
                                            value = itemSizePx * itemsScrollCount,
                                            animationSpec = tween(durationMillis = 1000)
                                        )
                                        scrollState.animateScrollToItem(scrollState.firstVisibleItemIndex)

                                    } catch (e: Exception) {
                                        Log.e("ayham", e.toString())
                                        // Handle exceptions here
                                    }
                                } else {
                                    break
                                }
                            }
                        }

                        onDispose {
                            job.cancel()
                            isInMain = false
                            // Cancel the coroutine when the Composable is removed from the composition
                        }
                    }


                    if (viewModel.bannerList.isNotEmpty()) {
                        InfiniteCircularList(
                            scrollState = scrollState,
                            screenWidth = screenWidth,
                            items = viewModel.bannerList,
                            initialItem = viewModel.bannerList[0],
                            changeIndicatorState = {
                                indicatorState = it
                            }
                        ) { item ->
                            if (item.isClickable) {
                                viewModel.navigate(item, navController!!, context)
                            }

                        }
                    }

                }
                item { Spacer(modifier = Modifier.size(10.dp)) }

                item {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        DotsIndicator(viewModel.bannerList.size, indicatorState, Color(0xFF131B5C), Color(0xFF848AC0))
                    }
                }


                item {
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Options(
                            modifier = Modifier.padding(
                                start = 8.5.dp,
                                end = 8.5.dp,
                                top = 20.dp
                            )
                        )
                    }
                }

                item{
                    Spacer(modifier = Modifier.size(20.dp))
                    MyScrollableTabRow(selectedTabIndex = 0,
                        contentColor = Color.White,
                        backgroundColor = Color.White,
                        minItemWidth = 50.dp,
                        edgePadding = 0.dp,
                        indicator = { tabPositions ->
                            TabRowDefaults.Indicator(
                                color = Color.Transparent, modifier = Modifier
                            )
                        }) {
                        CATEGORIES_LIST.forEachIndexed { index, category ->
                            CategoriesItem(
                                modifier = Modifier.clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ) {
                                  navController?.navigate(HomeScreens.CategoriesScreen.route)
                                },
                                painter = painterResource(id = category.icon),
                                text = category.name,
                                selected = false
                            )
                        }
                    }
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Featured Stores",
                            fontSize = 23.sp,
                            fontFamily = FontFamily(Font(R.font.font)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFF2D2D2D),
                            modifier = Modifier.padding(top = 20.dp, start = 16.dp)
                        )
                        Card(
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF131B5C)
                            ),
                            modifier = Modifier
                                .padding(top = 20.dp, end = 16.dp)
                                .align(Alignment.CenterVertically)
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ) {
                                    navController?.navigate(HomeScreens.CategoriesScreen.route)
                                }
                        ) {
                            Text(
                                modifier = Modifier.padding(
                                    top = 5.dp,
                                    bottom = 5.dp,
                                    start = 10.dp,
                                    end = 10.dp
                                ),
                                text = "View all",
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.font)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFFFFFFFF),
                            )
                        }


                    }
                }

                item { MerchantsLayout(navController, viewModel.storesList) }

            }
        }


    }


}

@Preview(showBackground = true)
@Composable
fun AppBar(modifier: Modifier = Modifier, navController: NavController? = null) {
    val painter = painterResource(id = R.drawable.simma_app_bar_logo)
    val helpPainter = painterResource(id = R.drawable.help_icon)
    val searchPainter = painterResource(id = R.drawable.search_icon)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .background(BackgroundLightGrey)
            .wrapContentHeight(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = BackgroundLightGrey
        )

    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Image(
                        painter = painter,
                        contentDescription = "logo",
                        modifier = Modifier
                            .width(33.dp)
                            .height(33.dp)
                            .align(Alignment.CenterVertically)
                    )

                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = "Welcome!",
                        fontFamily = FontFamily(Font(R.font.font)),
                        fontSize = 23.sp,
                        fontWeight = FontWeight(1000),
                        color = Color(0xFF2D2D2D)
                    )
                }

                Image(
                    painter = helpPainter,
                    contentDescription = "Help",
                    modifier = Modifier
                        .clickable {
                            navController?.navigate(HomeScreens.HelpCenterScreen.route)
                        }
                        .width(33.dp)
                        .height(33.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .padding(4.dp)
                )

            }
            var searchText by rememberSaveable { mutableStateOf("") }
            val interactionSource = remember { MutableInteractionSource() }

//            BasicTextField(
//                value = searchText,
//                singleLine = true,
//                interactionSource = interactionSource,
//                cursorBrush = SolidColor(Color.White),
//                onValueChange = { newText -> searchText = newText },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
//                    .height(40.dp)
//                    .clip(
//                        RoundedCornerShape(10.dp)
//                    )
//                    .background(
//                        Color.White
//                    ),
//            ) { innerTextField ->
//                OutlinedTextFieldDefaults.DecorationBox(
//                    value = searchText,
//                    innerTextField = innerTextField,
//                    enabled = true,
//                    singleLine = true,
//                    prefix = {
//                        Image(
//                            painter = searchPainter,
//                            contentDescription = "search",
//                            Modifier.size(24.dp)
//                        )
//                    },
//                    contentPadding = PaddingValues(start = 10.dp, end = 16.dp),
//                    interactionSource = interactionSource,
//                    visualTransformation = VisualTransformation.None,
//                    placeholder = {
//                        Text(
//                            text = "",
//                            fontFamily = FontFamily(Font(R.font.font)),
//                        )
//                    },
//                    container = {
//                        OutlinedTextFieldDefaults.ContainerBox(
//                            enabled = true,
//                            isError = false,
//                            interactionSource = interactionSource,
//                            colors = OutlinedTextFieldDefaults.colors(
//                                focusedBorderColor = Yellow
//                            ),
//                            shape = RoundedCornerShape(10.dp),
//                            focusedBorderThickness = 1.dp,
//                            unfocusedBorderThickness = 1.dp,
//                        )
//                    }
//                )
//            }
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BannerItem(
    modifier: Modifier = Modifier,
    painter: Painter = painterResource(id = R.drawable.banner_demo_image),
    imageUrl: String
) {
    GlideImage(
        if (imageUrl.startsWith("http")) imageUrl else "http:" + imageUrl,
        contentDescription = "Banner Image",
        modifier = modifier
            .clip(RoundedCornerShape(10.dp)),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color,
    unSelectedColor: Color
) {

    LazyRow(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()

    ) {

        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(selectedColor)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(unSelectedColor)
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}

@Composable
fun Options(modifier: Modifier = Modifier) {
    val chargeWalletPainter = painterResource(id = R.drawable.charge_wallet)
    val referAFriendPainter = painterResource(id = R.drawable.refer_friend_image)
    val getVoucherPainter = painterResource(id = R.drawable.get_voucher)
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Row {
            OptionItem(chargeWalletPainter, "Charge Wallet", Modifier.weight(1f))
            OptionItem(referAFriendPainter, "Refer a Friend", Modifier.weight(1f))
            OptionItem(getVoucherPainter, "Get a Voucher", Modifier.weight(1f))
        }
    }
}

@Composable
fun OptionItem(painter: Painter, content: String, modifier: Modifier = Modifier) {
    val brush = Brush.verticalGradient(listOf(Color.Transparent, Color.DarkGray))

    val shadowPainter = painterResource(id = R.drawable.shadow)
    Box(
        modifier = modifier
            .padding(start = 7.5f.dp, end = 7.5f.dp)
            .clip(RoundedCornerShape(10.dp))
    )
    {
        Image(
            painter = painter, contentDescription = content,
            modifier = Modifier
                .aspectRatio(112f / 148f),
            contentScale = ContentScale.Crop
        )
        Canvas(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(70.dp)
                .fillMaxWidth(),
            onDraw = {
                drawRect(brush)
            }
        )
        Text(
            text = content, fontSize = 16.sp,
            fontWeight = FontWeight(860),
            color = Color(0xFFFFFFFF),
            fontFamily = FontFamily(Font(R.font.font)),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 15.dp, start = 10.dp, end = 10.dp)
        )

    }


}

@Composable
fun MerchantsLayout(
    navController: NavController?,
    list: List<Result> = listOf()
) {
    val interactionSource = remember { MutableInteractionSource() }
    if (list.isNotEmpty()) {
        LazyVerticalGrid(
            modifier = Modifier
                .padding(start = 6.dp, end = 6.dp)
                .fillMaxWidth()
                .aspectRatio(1f / .9f), columns = GridCells.Fixed(4)
        ) {
            items(8) {
                MerchantItem(
                    Modifier.clickable {
                        val url = URLEncoder.encode(list[it].url, StandardCharsets.UTF_8.toString())
                        val cartUrl =
                            URLEncoder.encode(list[it].cartUrl, StandardCharsets.UTF_8.toString())

                        EXTRACTION_CODE = list[it].cartCode
                        DETAILS_COVER_IMAGE = list[it].coverImage.originalUrl
                        DETAILS_IMAGE = list[it].image?.originalUrl ?: ""
                        DETAILS_NAME = list[it].name.en
                        DETAILS_DESCRIPTION = ""
                        DETAILS_COUNTRY = list[it].country
                        DETAILS_MIN_DAYS = list[it].minimumDaysToDeliver.toString()
                        DETAILS_MAX_DAYS = list[it].maximumDaysToDeliver.toString()
                        DETAILS_ITEM_PRICED = list[it].extraDeliveryPerItem.toString()
                        DETAILS_DEALS =
                            list[it].deals.filter { it.isActive }.map { it.description.en }

                        navController?.navigate(
                            HomeScreens.WebViewScreen.withArgs(
                                url,
                                cartUrl,
                                list[it].id
                            )
                        )
                        PAYMENT_METHODS = list[it].paymentMethods
                    },
                    imageUrl = list[it].image?.originalUrl ?: "",
                    content = list[it].name.en
                )
            }
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MerchantItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    content: String
) {
    Column {
        Card(
            modifier = modifier
                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                .aspectRatio(1f),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )

        ) {
            GlideImage(
                model = if (imageUrl.startsWith("http")) imageUrl else "http:" + imageUrl,
                loading = placeholder(R.drawable.light_grey_shape),
                contentDescription = "Image",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            )
        }
        Text(
            text = content,
            fontSize = 12.sp,
            fontWeight = FontWeight(400),
            fontFamily = FontFamily(Font(R.font.font)),
            color = Color(0xFF4A4A4A),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 5.dp)
        )

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InfiniteCircularList(
    scrollState: LazyListState,
    screenWidth: Dp,
    items: List<com.simma.simmaapp.model.advertismentResponse.Result>,
    initialItem: com.simma.simmaapp.model.advertismentResponse.Result,
    changeIndicatorState: (Int) -> Unit,
    onItemClick: (com.simma.simmaapp.model.advertismentResponse.Result) -> Unit
) {
    val density = LocalDensity.current
    var lastSelectedIndex by remember {
        mutableStateOf(0)
    }
    var itemsState by remember {
        mutableStateOf(items)
    }

    LaunchedEffect(items) {
        var targetIndex = items.indexOf(initialItem) - 1
        targetIndex += ((Int.MAX_VALUE / 2) / items.size) * items.size
        itemsState = items
        lastSelectedIndex = targetIndex
//        val itemWidth = screenWidth
//        val itemSizePx = with(density) { itemWidth.toPx() }
//        val itemsScrollCount = 2
//        scrollState.animateScrollBy(
//            value = itemSizePx * itemsScrollCount,
//            animationSpec = tween(durationMillis = 1000)
//        )
//        scrollState.animateScrollToItem(scrollState.firstVisibleItemIndex)
        scrollState.scrollToItem(targetIndex)
    }



    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        state = scrollState,
        flingBehavior = rememberSnapFlingBehavior(
            snapLayoutInfoProvider = SnapLayoutInfoProvider(scrollState)
        )
    ) {
        items(
            count = Int.MAX_VALUE,
            itemContent = { i ->
                val item = itemsState[i % itemsState.size]
                val visibleItems = items.indexOfFirst { it == item }
                changeIndicatorState(visibleItems)
                BannerItem(
                    Modifier
                        .width(screenWidth)
                        .aspectRatio(315f / 138)
                        .clickable {
                            onItemClick(item)
                        }
                        .padding(start = 10.dp, end = 10.dp),
                    imageUrl = item.enBannerAd.originalUrl
                )
            }
        )
    }
}

@Preview
@Composable
fun EnableNotificationBanner() {
    var context = LocalContext.current
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.bell))
    Box(
        Modifier
            .fillMaxWidth()
            .clickable {
                requestNotificationPermission((context as HomeActivity))
            }, contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(OnBoardingBabyBlue),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = " Stay updated on our offers!",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.font_bold)),
                    fontWeight = FontWeight(1000),
                    color = Color(0xFF2D2D2D),
                )
            )
            Text(
                text = " Click to enable notifications",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.font)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF2D2D2D),
                )
            )

        }
    }
}

fun requestNotificationPermission(activity: Activity) {
    val intent = Intent()
    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    intent.data = Uri.fromParts("package", activity.packageName, null)
    activity.startActivity(intent)
    Toast.makeText(activity, "Please enable notifications for this app", Toast.LENGTH_LONG).show()
}


//@Composable
//fun BottomAppBarItem(painter: Painter, content: String, selected: Boolean) {
//    Column {
//        Icon(painter = painter, contentDescription = content,
//            Modifier
//                .size(25.dp)
//                .padding(3.dp)
//                .align(Alignment.CenterHorizontally))
//        Text(
//            text = content,
//            fontSize = 10.sp,
//            fontWeight = FontWeight(400),
//            fontFamily = FontFamily(Font(R.font.font)),
//            color = Color(0xFF2D2D2D),
//            modifier = Modifier.align(Alignment.CenterHorizontally)
//        )
//        if (selected) (
//                Box(
//                    modifier = Modifier
//                        .height(2.dp)
//                        .width(22.dp)
//                        .background(Yellow)
//                        .align(Alignment.CenterHorizontally)
//                )
//                )
//    }
//}



