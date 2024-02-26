package com.simma.simmaapp.presentation.webview

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.simma.simmaapp.R
import com.simma.simmaapp.presentation.homePage.HomeActivity
import com.simma.simmaapp.presentation.homePage.ui.theme.Yellow
import com.simma.simmaapp.presentation.homeScreen.HomeScreens
import com.simma.simmaapp.presentation.theme.AppBarGreyColor
import com.simma.simmaapp.presentation.theme.CheckoutAppBarColor
import com.simma.simmaapp.presentation.theme.Dimen.PADDING
import com.simma.simmaapp.presentation.theme.ShadowGrey
import com.simma.simmaapp.utils.Constants.CART_URL
import com.simma.simmaapp.utils.Constants.MERCHANT_ID
import com.simma.simmaapp.utils.Constants.selectedUrl
import com.simma.simmaapp.utils.ModifierUtil.dropShadow
import com.vs.simma.ui.webview.WebViewEvent
import kotlinx.coroutines.delay

@Preview(showBackground = true)
@Composable
fun WebView(
    navController: NavController? = null,
    shopUrl: String = "",
    cartUrl: String = "",
    extractionCode: String = "",
    id: String = ""
) {
    val interactionSource = remember { MutableInteractionSource() }
//    var context = LocalContext.current
//    ((LocalContext.current) as HomeActivity).window.apply {
//        setNavigationBarColor(ContextCompat.getColor(context, R.color.babyBlue))
//        statusBarColor = ContextCompat.getColor(context, R.color.babyBlue)
//    }
    selectedUrl = shopUrl
    CART_URL = cartUrl
    MERCHANT_ID = id
    val viewModel: WebViewViewModel = hiltViewModel()
    Box(Modifier.fillMaxSize()) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp)
        ) {
            WebViewScreen(
                viewModel, modifier = Modifier.padding(top = 60.dp)
            )
            AppBar(modifier = Modifier.align(Alignment.TopCenter), viewModel)

        }
        BottomAppBar(
            Modifier.align(Alignment.BottomCenter),
            navController,
            viewModel = viewModel,
            cartUrl = cartUrl,
            extractionCode = extractionCode,
            shopId = id
        )
    }

}

@Composable
fun AppBar(modifier: Modifier = Modifier, viewModel: WebViewViewModel) {
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }
    val storesPainter = painterResource(id = R.drawable.stores_icon)
    val backPainter = painterResource(id = R.drawable.back)
    val reloadPainter = painterResource(id = R.drawable.refresh)
    val forwardPainter = painterResource(id = R.drawable.forward)
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
        Icon(
            painter = storesPainter,
            contentDescription = "stores",
            modifier = Modifier
                .size(23.dp)
                .padding(3.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    (context as HomeActivity).onBackPressedDispatcher.onBackPressed()
                },
            tint = AppBarGreyColor
        )
        Icon(
            painter = backPainter,
            contentDescription = "back",
            modifier = Modifier
                .size(23.dp)
                .padding(3.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    viewModel.webView.goBack()
                },
            tint = AppBarGreyColor
        )
        Icon(
            painter = reloadPainter,
            contentDescription = "reload",
            modifier = Modifier
                .size(23.dp)
                .padding(3.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    viewModel.webView.reload()
                },
            tint = AppBarGreyColor
        )
        Icon(
            painter = forwardPainter,
            contentDescription = "forward",
            modifier = Modifier
                .size(23.dp)
                .padding(3.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    viewModel.webView.goForward()
                },
            tint = AppBarGreyColor
        )
        Icon(
            painter = helpPainter,
            contentDescription = "help",
            modifier = Modifier
                .size(25.dp)
                .padding(3.dp),
            tint = AppBarGreyColor
        )
    }

}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(
    viewModel: WebViewViewModel,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            viewModel.webView

        }, update = { view ->
            view.apply {
                if (url != viewModel.url) {
                    loadUrl(viewModel.url)
                }
            }
        }
    )
}

@Composable
fun BottomButton(
    modifier: Modifier = Modifier,
    text: String = "View Cart",
    isAnimated: Boolean = false,
    onClick : ()->Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val painter = painterResource(id = R.drawable.simma_logo)
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
        Row {
            Image(
                painter = painter,
                contentDescription = text,
                Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .background(Color.DarkGray)
                    .height(30.dp)
                    .width(1.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
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
    shopId: String
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
        BottomButton(Modifier, viewModel.state.btnText, isAnimated = viewModel.isAnimated){
            viewModel.onEvent(
                WebViewEvent.OnClick(
                    cartUrl,
                    extractionCode, shopId,
                    { navController?.navigate(HomeScreens.CartScreen.route) })
            )
        }
    }
}