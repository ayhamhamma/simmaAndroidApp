package com.simma.simmaapp.presentation.onBoardingScreen

import androidx.annotation.DrawableRes
import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.simma.simmaapp.R
import com.simma.simmaapp.presentation.loginScreen.LoginActivity
import com.simma.simmaapp.presentation.loginScreen.Screen
import com.simma.simmaapp.presentation.theme.Dimen.PADDING
import com.simma.simmaapp.presentation.theme.OnBoardingBabyBlue
import com.simma.simmaapp.presentation.theme.OnBoardingPink
import com.simma.simmaapp.presentation.theme.OnBoardingYellow
import com.simma.simmaapp.presentation.theme.SimmaBlue
import com.simma.simmaapp.utils.Helpers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Preview
@Composable
fun OnBoardingScreen(navController: NavController? = null) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {

        ((context) as LoginActivity).window.apply {
            navigationBarColor =
                ContextCompat.getColor(context, R.color.white)
            statusBarColor =
                ContextCompat.getColor(context, R.color.white)
        }
    }
    Scaffold(
        topBar = {
            AppBar()
        }
    ) { paddingValues ->
        BoardingBody(modifier = Modifier.padding(paddingValues), navController)

    }

}

@Preview
@Composable
fun AppBar() {
    val painter = painterResource(id = R.drawable.simma_logo1)
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = PADDING, end = PADDING, top = PADDING),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painter, contentDescription = "Simma", modifier = Modifier.size(60.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(SimmaBlue), contentAlignment = Alignment.Center
        ) {
            Text(
                text = "العربيه",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.font_med))
                ),
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)

            )

        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun BoardingBody(
    modifier: Modifier = Modifier.padding(PADDING),
    navController: NavController? = null
) {

    val context = LocalContext.current

    val lazyList = listOf(
        OnBoardingItem(
            R.drawable.onboarding001,
            OnBoardingBabyBlue,
            "Making the\nunfamiliar... familiar ",
            "Ready to redefine your\nshopping experience?"
        ),
        OnBoardingItem(
            R.drawable.onboarding002,
            OnBoardingYellow,
            "Shop global brands,\n delivered locally ",
            "Shop your favorite international\n brands, conveniently delivered\n to Iraq"
        ),
        OnBoardingItem(
            R.drawable.onboarding003,
            OnBoardingPink,
            "Shop Smarter\n& Save More",
            "Redeem vouchers &\npoints for exclusive deals"
        ) ,
        OnBoardingItem(
            R.drawable.empty_drawable,
            OnBoardingBabyBlue,
            "",
            ""
        )
    )
    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var isTitleVisible by remember {
        mutableStateOf(false)
    }
    var isDiscVisible by remember {
        mutableStateOf(false)
    }
    var selectedIndex by remember {
        mutableStateOf(0)
    }
    val color = remember { Animatable(OnBoardingBabyBlue) }
    val pagerState = rememberPagerState(pageCount = {
        4
    })
    (context as LoginActivity).window.navigationBarColor = color.value.toArgb()
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(pagerState) {

        snapshotFlow {
            pagerState.currentPage
        }.collect { index ->
            selectedIndex = index
            if (selectedIndex > 2) {
                Helpers.setFirstTimeOpenToFalse(context)
                coroutineScope.launch(Dispatchers.Unconfined) {
                    color.animateTo(
                        lazyList[index].color,
                        animationSpec = tween(1000, easing = EaseInOut)
                    )
                }
                navController?.navigate(Screen.LoginScreen.route)
            } else {
                coroutineScope.launch(Dispatchers.Unconfined) {
                    color.animateTo(
                        lazyList[index].color,
                        animationSpec = tween(1000, easing = EaseInOut)
                    )
                }
                isDiscVisible = false
                isTitleVisible = false
                delay(500)
                title = lazyList[index].title
                description = lazyList[index].description
                isTitleVisible = true
                delay(500)
                isDiscVisible = true
            }
        }
    }
    Box(
        modifier.fillMaxSize(),
    ) {
        Column(Modifier.fillMaxWidth().align(Alignment.TopCenter), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.size(30.dp))
            AnimatedVisibility(
                visible = isTitleVisible,
                exit = fadeOut()
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 30.sp,
                        color = SimmaBlue,
                        fontWeight = FontWeight(1000),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.font_bold))
                    ),
                    modifier = Modifier

                )


            }

            Spacer(modifier = Modifier.size(10.dp))

            AnimatedVisibility(
                visible = isDiscVisible,
                exit = fadeOut()
            ) {
                Text(
                    text = description,
                    style = TextStyle(
                        fontSize = 24.sp,
                        color = SimmaBlue,
                        fontWeight = FontWeight(100),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.font_med))
                    ),
                    modifier = Modifier.padding()

                )
            }
        }

        Box( Modifier
            .fillMaxSize().align(Alignment.BottomCenter)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(2f)
                    .clip(RoundedCornerShape(topEnd = 350.dp, topStart = 350.dp))
                    .background(color.value)
                    .size(270.dp)
                    .align(Alignment.BottomCenter)
            )
            val fling = PagerDefaults.flingBehavior(
                state = pagerState,
                pagerSnapDistance = PagerSnapDistance.atMost(1)
            )
            Box(Modifier.fillMaxWidth()) {
                HorizontalPager(
                    state = pagerState,
                    flingBehavior = fling
                ) { page ->
                    Box (Modifier.fillMaxSize().padding(bottom = 40.dp), contentAlignment = Alignment.BottomCenter){
                        Image(
                            painter = painterResource(id = lazyList[page].image),
                            contentDescription = "image",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .aspectRatio(370f / 320f)
                                .fillMaxWidth()
                                .graphicsLayer {
                                    // Calculate the absolute offset for the current page from the
                                    // scroll position. We use the absolute value which allows us to mirror
                                    // any effects for both directions
                                    val pageOffset = (
                                            (pagerState.currentPage - page) + pagerState
                                                .currentPageOffsetFraction
                                            ).absoluteValue

                                    // We animate the alpha, between 50% and 100%
                                    alpha = lerp(
                                        start = 0.5f,
                                        stop = 1f,
                                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                    )
                                }
                        )
                    }


                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .align(Alignment.BottomCenter)
                        .padding(start = PADDING, end = PADDING),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val interactionSource = remember { MutableInteractionSource() }
                    Text(
                        text = "Skip",
                        style = TextStyle(
                            fontSize = 24.sp,
                            color = Color.Black,
                            textDecoration = TextDecoration.Underline,
                            fontWeight = FontWeight(100),
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(Font(R.font.font))
                        ),
                        modifier = Modifier.clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            Helpers.setFirstTimeOpenToFalse(context)
                            coroutineScope.launch(Dispatchers.Unconfined) {
                                color.animateTo(
                                    OnBoardingBabyBlue,
                                    animationSpec = tween(1000, easing = EaseInOut)
                                )
                            }
                            navController?.navigate(Screen.LoginScreen.route)
                        }
                    )
                    PagerIndicator(selectedIndex = selectedIndex)
                }
                Spacer(modifier = Modifier.size(20.dp))
            }

        }

    }
}

@Composable
fun PagerIndicator(selectedIndex: Int = 0) {
    Row {
        (0..2).forEach {
            Box(
                Modifier
                    .width(25.dp)
                    .height(5.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 5.dp,
                            topEnd = 5.dp,
                            bottomEnd = 5.dp,
                            bottomStart = 5.dp
                        )
                    )
                    .background(if (it == selectedIndex) SimmaBlue else Color.White)
            )
            Spacer(modifier = Modifier.size(3.dp))
        }
    }

}

class OnBoardingItem(
    @DrawableRes val image: Int,
    val color: Color,
    val title: String,
    val description: String
)