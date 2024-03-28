package com.simma.simmaapp.presentation.categoriesPage

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.simma.simmaapp.R
import com.simma.simmaapp.presentation.homePage.HomeActivity
import com.simma.simmaapp.presentation.homePage.ui.theme.Yellow
import com.simma.simmaapp.presentation.homeScreen.HomeScreens
import com.simma.simmaapp.presentation.theme.CheckoutAppBarColor
import com.simma.simmaapp.presentation.theme.Dimen.PADDING
import com.simma.simmaapp.presentation.theme.MedDarkGrey
import com.simma.simmaapp.presentation.theme.SearchBarColor
import com.simma.simmaapp.presentation.theme.checkoutLightText
import com.simma.simmaapp.utils.Constants
import com.simma.simmaapp.utils.Constants.CATEGORIES_LIST
import com.simma.simmaapp.utils.MyScrollableTabRow
import com.vs.simma.model.listingModel.Result
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun CategoryScreen(
    navController: NavController? = null,
    searchText: String = "",
    categoriesList : List<Category> = CATEGORIES_LIST,
    merchantsList : List<Result> = listOf(),
    searchState: Boolean = true,
    onTextChange: (text: String) -> Unit = {}
) {
    val pagerState = rememberPagerState(pageCount = {
        categoriesList.size
    })
    LaunchedEffect(searchState){
        pagerState.animateScrollToPage(0)
    }
    val fling = PagerDefaults.flingBehavior(
        state = pagerState,
        pagerSnapDistance = PagerSnapDistance.atMost(1)
    )
    val coroutineScope = rememberCoroutineScope()
    Column(Modifier.fillMaxSize()) {
        AppBar(pagerState = pagerState,
            text = searchText,
            categoriesList,
            {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(it)
                }
            }, onTextChange = {
                onTextChange(it)
            })
        HorizontalPager(
            state = pagerState,
            flingBehavior = fling
        ) { page ->
            if(page == 0){
                LazyVerticalGrid(modifier = Modifier.fillMaxSize(), columns = GridCells.Fixed(4)) {
                    items(merchantsList.size) {
                        MerchantItem(
                            Modifier.clickable {
                                val url = URLEncoder.encode(
                                    merchantsList[it].url,
                                    StandardCharsets.UTF_8.toString()
                                )
                                val cartUrl = URLEncoder.encode(
                                    merchantsList[it].cartUrl,
                                    StandardCharsets.UTF_8.toString()
                                )
                                Constants.EXTRACTION_CODE = merchantsList[it].cartCode
                                Constants.DETAILS_COVER_IMAGE =
                                    merchantsList[it].coverImage.originalUrl
                                Constants.DETAILS_IMAGE =
                                    merchantsList[it].image?.originalUrl ?: ""
                                Constants.DETAILS_NAME = merchantsList[it].name.en
                                Constants.DETAILS_DESCRIPTION = ""
                                Constants.DETAILS_COUNTRY = merchantsList[it].country
                                Constants.DETAILS_MIN_DAYS =
                                    merchantsList[it].minimumDaysToDeliver.toString()
                                Constants.DETAILS_MAX_DAYS =
                                    merchantsList[it].maximumDaysToDeliver.toString()
                                Constants.DETAILS_ITEM_PRICED =
                                    merchantsList[it].extraDeliveryPerItem.toString()
                                Constants.DETAILS_DEALS =
                                    merchantsList[it].deals.filter { it.isActive }
                                        .map { it.description.en }
                                navController?.navigate(
                                    HomeScreens.WebViewScreen.withArgs(
                                        url, cartUrl, merchantsList[it].id
                                    )
                                )
                                Constants.PAYMENT_METHODS =
                                    merchantsList[it].paymentMethods
                            },
                            imageUrl = merchantsList[it].image?.originalUrl ?: "",
                            content = merchantsList[it].name.en
                        )
                    }
                }

            }else{
                LazyVerticalGrid(modifier = Modifier.fillMaxSize(), columns = GridCells.Fixed(4)) {
                    items(categoriesList[page].merchants.size) {
                        MerchantItem(
                            Modifier.clickable {
                                val url = URLEncoder.encode(
                                    categoriesList[page].merchants[it].url,
                                    StandardCharsets.UTF_8.toString()
                                )
                                val cartUrl = URLEncoder.encode(
                                    categoriesList[page].merchants[it].cartUrl,
                                    StandardCharsets.UTF_8.toString()
                                )
                                Constants.EXTRACTION_CODE = categoriesList[page].merchants[it].cartCode
                                Constants.DETAILS_COVER_IMAGE =
                                    categoriesList[page].merchants[it].coverImage.originalUrl
                                Constants.DETAILS_IMAGE =
                                    categoriesList[page].merchants[it].image?.originalUrl ?: ""
                                Constants.DETAILS_NAME = categoriesList[page].merchants[it].name.en
                                Constants.DETAILS_DESCRIPTION = ""
                                Constants.DETAILS_COUNTRY = categoriesList[page].merchants[it].country
                                Constants.DETAILS_MIN_DAYS =
                                    categoriesList[page].merchants[it].minimumDaysToDeliver.toString()
                                Constants.DETAILS_MAX_DAYS =
                                    categoriesList[page].merchants[it].maximumDaysToDeliver.toString()
                                Constants.DETAILS_ITEM_PRICED =
                                    categoriesList[page].merchants[it].extraDeliveryPerItem.toString()
                                Constants.DETAILS_DEALS =
                                    categoriesList[page].merchants[it].deals.filter { it.isActive }
                                        .map { it.description.en }
                                navController?.navigate(
                                    HomeScreens.WebViewScreen.withArgs(
                                        url, cartUrl, categoriesList[page].merchants[it].id
                                    )
                                )
                                Constants.PAYMENT_METHODS =
                                    categoriesList[page].merchants[it].paymentMethods
                            },
                            imageUrl = categoriesList[page].merchants[it].image?.originalUrl ?: "",
                            content = categoriesList[page].merchants[it].name.en
                        )
                    }
                }
            }

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun AppBar(
    pagerState: PagerState? = null,
    text: String = "",
    categoriesList :List<Category> = listOf(),
    onPageChanged: (index: Int) -> Unit = {},
    onTextChange: (text: String) -> Unit = {}
) {
    val painter = painterResource(id = R.drawable.back)
    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    val screenWidth = configuration.screenWidthDp.dp
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Row(
        Modifier
            .fillMaxWidth()
            .background(CheckoutAppBarColor)
            .height(120.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var searchVisibility by remember {
            mutableStateOf(false)
        }
        Column(horizontalAlignment = Alignment.Start) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(start = PADDING, end = PADDING)
            ) {
                Icon(painter = painter, contentDescription = "back", modifier = Modifier
                    .clickable {
                        (context as HomeActivity).onBackPressedDispatcher.onBackPressed()
                    }
                    .size(20.dp)
                    .align(Alignment.CenterStart), tint = MedDarkGrey)
                if (!searchVisibility) {
                    Text(
                        text = "Browse By Category",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.font)),
                            fontWeight = FontWeight(400),
                            color = checkoutLightText,
                            textAlign = TextAlign.Center,
                        ),
                        modifier = Modifier.align(Alignment.Center),

                        )
                }
                Row(
                    Modifier
                        .align(Alignment.CenterEnd)
                        .clip(RoundedCornerShape(8.dp))
                        .background(SearchBarColor)
                ) {
                    Image(modifier = Modifier
                        .clickable { searchVisibility = !searchVisibility }
                        .size(32.dp)
                        .padding(5.dp),
                        painter = painterResource(id = R.drawable.search_icon),
                        contentDescription = "Search for items")
                    AnimatedVisibility(visible = searchVisibility) {

                        BasicTextField(value = text,
                            onValueChange = {
                                onTextChange(it)
                            },
                            singleLine = true,
                            modifier = Modifier
                                .height(32.dp)
                                .width(screenWidth * 2 / 3),
                            textStyle = LocalTextStyle.current.copy(color = Color.Black),
                            decorationBox = { innerTextField ->
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    if (text.isEmpty()) {
                                        Text(
                                            "Search your favorite store",

                                            )
                                    }
                                    innerTextField()
                                }

                            })
                    }

                }

            }
            Spacer(modifier = Modifier.size(20.dp))
            MyScrollableTabRow(selectedTabIndex = pagerState!!.currentPage,
                contentColor = CheckoutAppBarColor,
                backgroundColor = CheckoutAppBarColor,
                minItemWidth = 60.dp,
                edgePadding = PADDING,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        color = Color.Transparent, modifier = Modifier
                    )
                }) {
                categoriesList.forEachIndexed { index, category ->
                    CategoriesItem(
                        modifier = Modifier.clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            onPageChanged(index)
                        },
                        painter = painterResource(id = category.icon),
                        text = category.name,
                        selected = (index == (pagerState.currentPage ?: false))
                    )
                }
            }
        }
    }
}


@Composable
fun CategoriesItem(
    modifier: Modifier = Modifier,
    painter: Painter = painterResource(id = R.drawable.apperal),
    text: String = "Apparel",
    selected: Boolean = false
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            Modifier.size(32.dp), contentAlignment = Alignment.Center
        ) {
            Row {
                AnimatedVisibility(visible = selected) {
                    Box(
                        Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Yellow)
                    )
                }
            }
            Icon(
                painter = painter,
                contentDescription = text,
                modifier = Modifier.size(23.dp),
                tint = MedDarkGrey
            )
        }
        Text(
            text = text,
            fontSize = 8.sp,
            fontWeight = FontWeight(590),
            color = Color(0xFF4A4A4A),
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.size(5.dp))
        AnimatedVisibility(
            visible = selected,
            enter = expandHorizontally(),
            exit = shrinkHorizontally()
        ) {
            Box(
                Modifier
                    .width(35.dp)
                    .height(3.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Yellow)
            ) {

            }
        }

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MerchantItem(
    modifier: Modifier = Modifier, imageUrl: String, content: String
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