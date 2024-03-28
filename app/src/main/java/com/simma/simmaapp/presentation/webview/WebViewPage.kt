package com.simma.simmaapp.presentation.webview

import android.view.ViewGroup
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.simma.simmaapp.R
import com.simma.simmaapp.presentation.homePage.HomeActivity
import com.simma.simmaapp.presentation.homePage.ui.theme.Yellow
import com.simma.simmaapp.presentation.homeScreen.HomeScreens
import com.simma.simmaapp.presentation.registrationView.SmsOrWhatsApp
import com.simma.simmaapp.presentation.theme.AppBarGreyColor
import com.simma.simmaapp.presentation.theme.CheckoutAppBarColor
import com.simma.simmaapp.presentation.theme.Dimen.PADDING
import com.simma.simmaapp.presentation.theme.SearchBarColor
import com.simma.simmaapp.presentation.theme.ShadowGrey
import com.simma.simmaapp.presentation.theme.checkoutLightText
import com.simma.simmaapp.utils.Constants.EXTRACTION_CODE
import com.simma.simmaapp.utils.Helpers.getCoachMarkState
import com.simma.simmaapp.utils.Helpers.setCoachMarkToFalse
import com.simma.simmaapp.utils.ModifierUtil.dropShadow
import com.vs.simma.ui.webview.WebViewEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WebView(
    navController: NavController? = null,
    shopUrl: String = "",
    cartUrl: String = "",
    id: String = "",
//    imageUrl : String = "",
//    backgroundImageUrl : String,
//    storeName : String,
//    storeDescription  :String,
    viewModel: WebViewViewModel
) {
    var showDialog by remember {
        mutableStateOf(true)
    }
    val context = LocalContext.current
    val swipeToRefreshLayout = SwipeRefreshLayout(context)

    viewModel.cartUrl = cartUrl
    Scaffold(
        Modifier.fillMaxSize(),
        topBar = { AppBar(modifier = Modifier, viewModel, navController) },
        bottomBar = {
            BottomAppBar(
                Modifier,
                navController,
                viewModel = viewModel,
                cartUrl = cartUrl,
                extractionCode = EXTRACTION_CODE,
            )
        },
    ) {

        WebViewScreen(
            viewModel, modifier = Modifier.padding(it), swipeToRefreshLayout
        )
        if (getCoachMarkState(context)) {
            if (showDialog)
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CoachMark(onDismiss = { showDialog = false }, navController = navController)
                }

        }
    }

}

@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    viewModel: WebViewViewModel,
    navController: NavController? = null
) {
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }
    val storesPainter = painterResource(id = R.drawable.simma_logo)
//    val backPainter = painterResource(id = R.drawable.back)
//    val reloadPainter = painterResource(id = R.drawable.refresh)
//    val forwardPainter = painterResource(id = R.drawable.forward)
    val helpPainter = painterResource(id = R.drawable.help)
    Row(
        modifier = modifier
            .graphicsLayer {
                this.shadowElevation = 15.dp.toPx()
            }
            .fillMaxWidth()
            .height(60.dp)
            .background(CheckoutAppBarColor)
            .padding(start = PADDING, end = PADDING),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
            Box(
                Modifier
                    .padding(3.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(SearchBarColor)) {
                Icon(
                    painter = storesPainter,
                    contentDescription = "stores",
                    modifier = Modifier
                        .size(29.dp)
                        .padding(3.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            (context as HomeActivity).onBackPressedDispatcher.onBackPressed()
                        },
                    tint = checkoutLightText
                )
            }
            Text(
                text = "Home",
                style = TextStyle(
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(R.font.font)),
                    fontWeight = FontWeight(400),
                    color = checkoutLightText,
                )
            )
        }

        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
            Box(
                Modifier
                    .padding(3.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(SearchBarColor)) {
                Icon(
                    painter = helpPainter,
                    contentDescription = "About Store",
                    modifier = Modifier
                        .size(29.dp)
                        .padding(3.dp)
                        .clickable {
                            navController?.navigate(HomeScreens.StoreInfoScreen.route)

                        },
                    tint = checkoutLightText
                )

            }
            Text(
                text = "Info",
                style = TextStyle(
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(R.font.font)),
                    fontWeight = FontWeight(400),
                    color = checkoutLightText,
                )
            )
        }
    }

}

@Composable
fun WebViewScreen(
    viewModel: WebViewViewModel,
    modifier: Modifier = Modifier,
    view: SwipeRefreshLayout
) {
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    AndroidView(
        modifier = modifier
            .fillMaxSize(),
        factory = { context ->
            view.apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setOnRefreshListener {
                    refreshScope.launch {
                        refreshing = true
                        viewModel.webView.reload()
                        delay(1500)
                        refreshing = false
                    }

                }
                if (viewModel.webView.getParent() != null) {
                    (viewModel.webView.getParent() as ViewGroup).removeView(viewModel.webView) // <- fix
                }
                addView(viewModel.webView.apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                })
//                removeAllViews()
            }
        },
        update = { view ->
            view.isRefreshing = refreshing
            if (viewModel.webView.url != viewModel.url) {
                viewModel.webView.loadUrl(viewModel.url)
            }
        }
    )

}


@Composable
fun BottomButton(
    modifier: Modifier = Modifier,
    text: String = "View Cart",
    isAnimated: Boolean = false,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val viewCartPainter = painterResource(id = R.drawable.view_cart_icon)
    val checkOutPainter = painterResource(id = R.drawable.checkout_icon)
//    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
//    val scale by infiniteTransition.animateFloat(
//        initialValue = 1f,
//        targetValue = 1.1f,
//        animationSpec = infiniteRepeatable(tween(500), RepeatMode.Reverse),
//        label = "scale"
//    )
    val scale1 = remember {
        Animatable(1f)
    }
    LaunchedEffect(key1 = isAnimated) {
        while (isAnimated) {
            scale1.animateTo(
                1.1f,
                animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
            )
            scale1.animateTo(
                1f,
                animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
            )
            scale1.animateTo(
                1.1f,
                animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
            )
            scale1.animateTo(
                1f,
                animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
            )
            delay(2000)
        }
    }

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale1.value
                scaleY = scale1.value
                transformOrigin = TransformOrigin.Center
            }
//            .graphicsLayer {
//                this.shadowElevation = 3.dp.toPx()
//                this.shape = RoundedCornerShape(15.dp)
//            }
            .clip(RoundedCornerShape(15.dp))
            .clickable(
            ) {
                onClick()


            }
            .background(Yellow)
            .padding(start = 25.dp, end = 25.dp, top = 5.dp, bottom = 5.dp),
        contentAlignment = Alignment.Center
    ) {
        Row (Modifier.height(30.dp), horizontalArrangement = Arrangement.SpaceAround){
            Image(
                painter = if(isAnimated) checkOutPainter else viewCartPainter,
                contentDescription = text,
                Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.size(17.dp))
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight(700),
                color = Color(0xFF2D2D2D),
                modifier = Modifier.align(Alignment.CenterVertically)

            )

        }
    }
}

@Composable
fun BottomAppBar(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    cartUrl: String,
    viewModel: WebViewViewModel,
    extractionCode: String,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier
            .height(60.dp)
            .fillMaxWidth()
            .dropShadow(
                color = ShadowGrey,
                blurRadius = 10.dp,
                borderRadius = 25.dp
            )

            .clip(
                RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
            )
            .background(CheckoutAppBarColor), contentAlignment = Alignment.Center


    ) {
        BottomButton(Modifier, viewModel.state.btnText, isAnimated = viewModel.isAnimated) {
            viewModel.onEvent(
                WebViewEvent.OnClick(
                    cartUrl,
                    extractionCode
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CoachMark(navController: NavController? = null, onDismiss: (() -> Unit)? = null) {
    val context = LocalContext.current
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.questions))
    Dialog(onDismissRequest = {
        onDismiss?.invoke()
        setCoachMarkToFalse(context)
    }) {
        Card(
            modifier = Modifier
                .graphicsLayer {
                    this.shadowElevation = 10f
                    this.shape = RoundedCornerShape(16.dp)
                },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LottieAnimation(composition = composition, modifier = Modifier.height(124.dp))
                Text(
                    text = "Would you like to know the steps how to shop from Simma ? Click Yes for more information.",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(700),
                        fontFamily = FontFamily(Font(R.font.font_med)),
                        color = checkoutLightText,
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(12.dp))
                Row(Modifier.fillMaxWidth()) {
                    SmsOrWhatsApp(
                        iconShow = false,
                        selected = false,
                        text = "No",
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                onDismiss?.invoke()
                                setCoachMarkToFalse(context)
                            }
                    )
                    Spacer(modifier = Modifier.size(13.dp))
                    SmsOrWhatsApp(iconShow = false,
                        selected = true,
                        text = "Yes",
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                navController?.navigate(HomeScreens.FrequentlyAskedQuestions.route)
                                setCoachMarkToFalse(context)
                            }
                    )
                }
            }
        }
    }
}


