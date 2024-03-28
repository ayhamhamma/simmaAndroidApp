package com.simma.simmaapp.presentation.confirmOrderPage

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.simma.simmaapp.R
import com.simma.simmaapp.presentation.homePage.HomeActivity
import com.simma.simmaapp.presentation.homePage.ui.theme.Yellow
import com.simma.simmaapp.presentation.homeScreen.HomeScreens
import com.simma.simmaapp.presentation.registrationView.SmsOrWhatsApp
import com.simma.simmaapp.presentation.theme.ActiveResendCodeColor
import com.simma.simmaapp.presentation.theme.BorderColor
import com.simma.simmaapp.presentation.theme.CheckoutAppBarColor
import com.simma.simmaapp.presentation.theme.Dimen.PADDING
import com.simma.simmaapp.presentation.theme.ErrorColor
import com.simma.simmaapp.presentation.theme.InActiveResendCodeColor
import com.simma.simmaapp.presentation.theme.LightGrey
import com.simma.simmaapp.presentation.theme.MedDarkGrey
import com.simma.simmaapp.presentation.theme.ShadowGrey
import com.simma.simmaapp.presentation.theme.checkoutLightText
import com.simma.simmaapp.utils.Constants
import com.simma.simmaapp.utils.Constants.APPLY_DISCOUNT
import com.simma.simmaapp.utils.Constants.CART_NUMBER_OF_ITEMS
import com.simma.simmaapp.utils.Constants.CART_SHIPPING_FEES
import com.simma.simmaapp.utils.Constants.CART_TOTAL
import com.simma.simmaapp.utils.Constants.DELIVERY_CITY_NAME
import com.simma.simmaapp.utils.Constants.DELIVERY_DETAILED_ADDRESS
import com.simma.simmaapp.utils.Constants.DELIVERY_FEES
import com.simma.simmaapp.utils.Helpers.formatNumber
import com.simma.simmaapp.utils.ModifierUtil.dropShadow
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun ConfirmOrderScreen(navController: NavController? = null) {

    val viewModel: ConfirmOrderViewModel = hiltViewModel()
    val editPainter = painterResource(id = R.drawable.edit_icon)
    val context = LocalContext.current
    var isPobUpVisible by remember {
        mutableStateOf(false)
    }
    Color(0xFF000000)
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)

    ) {
        Column(modifier = Modifier.padding(start = PADDING, end = PADDING)) {
            AppBar()
            LazyColumn {
                item {
                    Column(Modifier.padding(bottom = 90.dp)) {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .border(1.dp, LightGrey, RoundedCornerShape(24.dp))
                                .padding(PADDING)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Your order will be delivered to this address",
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(Font(R.font.font_med)),
                                    fontWeight = FontWeight(700),
                                    color = Color(0xff2D2D2D),
                                    modifier = Modifier.fillMaxWidth(0.7f)
                                )
                                Image(
                                    painter = editPainter, contentDescription = "edit",
                                    Modifier
                                        .clip(
                                            RoundedCornerShape(8.dp)
                                        )
                                        .clickable {
                                            (context as HomeActivity).onBackPressedDispatcher.onBackPressed()
                                        }
                                        .background(CheckoutAppBarColor)
                                        .padding(5.dp)
                                        .size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.size(15.dp))
                            Box {
                                Text(
                                    text = "City",
                                    fontSize = 15.sp,
                                    fontFamily = FontFamily(Font(R.font.font_med)),
                                    fontWeight = FontWeight(700),
                                    color = Color(0xff2D2D2D),
                                )
                                Text(
                                    text = DELIVERY_CITY_NAME,
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.font_med)),
                                    fontWeight = FontWeight(300),
                                    color = Color(0xff2D2D2D),
                                    modifier = Modifier.padding(top = 19.dp)
                                )
                            }

                            Spacer(modifier = Modifier.size(8.dp))
                            Box {
                                Text(
                                    text = "Address Details",
                                    fontSize = 15.sp,
                                    fontFamily = FontFamily(Font(R.font.font_med)),
                                    fontWeight = FontWeight(700),
                                    color = Color(0xff2D2D2D),
                                )
                                Text(
                                    text = DELIVERY_DETAILED_ADDRESS,
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.font_med)),
                                    fontWeight = FontWeight(300),
                                    color = Color(0xff2D2D2D),
                                    modifier = Modifier.padding(top = 19.dp)
                                )
                            }

                        }
                        Text(
                            text = "Order details",
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.font_med)),
                            fontWeight = FontWeight(700),
                            color = Color(0xff2D2D2D),
                            modifier = Modifier.padding(top = 20.dp)
                        )
//                        Spacer(modifier = Modifier.size.dp))
                        DetailsItem(
                            "Number Of Items",
                            CART_NUMBER_OF_ITEMS
                        )
                        DetailsItem(
                            "Total",
                            CART_TOTAL,
                            showPrice = true
                        )
                        DetailsItem(
                            "Shipping fees",
                            CART_SHIPPING_FEES,
                            showPrice = true
                        )
                        viewModel.discountViewList.forEach {
                            DetailsItem(
                                it.description.en,
                                it.iqdDiscountAmount.toString(),
                                showPrice = true,
                                isDiscount = true
                            )
                        }
                        if (viewModel.discountCodeApply) {
                            DetailsItem(
                                viewModel.discountCodeName,
                                viewModel.totalDiscountCodeAmount,
                                showPrice = true,
                                isDiscount = true
                            )
                        }
                        Spacer(modifier = Modifier.size(16.dp))
                        AnimatedVisibility(
                            viewModel.showWalletFreeShipping,
//                            enter = fadeIn(),
//                            exit = fadeOut()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(CheckoutAppBarColor)
                                    .padding(start = 10.dp, top = 5.dp, bottom = 5.dp, end = 5.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Apply free shipping?",
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily(Font(R.font.font_med)),
                                    fontWeight = FontWeight(700),
                                    color = Color(0xff2D2D2D),
                                )
                                Checkbox(
                                    modifier = Modifier
                                        .height(26.dp)
                                        .width(26.dp),
                                    checked = viewModel.check,
                                    onCheckedChange = {
                                        viewModel.changeCheckWFS(it, APPLY_DISCOUNT.value)
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = Yellow,
                                        checkmarkColor = Color.Black
                                    )
                                )
                            }
                        }


                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                modifier = Modifier
                                    .weight(3f) // Takes 3 out of 4 parts
                                    .padding(end = 8.dp),
                                onTextChange = {
                                    viewModel.discountText = it
                                    if (it.isNotEmpty()) {
                                        viewModel.applyColor = Yellow
                                    } else {
                                        viewModel.applyColor = Color(0xFF999999)
                                    }
                                }, hint = "Do you have a discount",
                                text = viewModel.discountText,
                                isError =viewModel.discountError
                            )
                            Box(
                                modifier = Modifier
                                    .weight(1f) // Takes 1 out of 4 parts
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable {
                                        if (!APPLY_DISCOUNT.value) {
                                            viewModel.applyDiscount()
                                        } else {
                                            APPLY_DISCOUNT.value = false
                                        }
                                    }
                                    .background(

                                        viewModel.applyColor
                                    )
                                    .height(40.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = viewModel.discountButtonText,
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        lineHeight = 24.sp,
                                        fontWeight = FontWeight(700),
                                        color = CheckoutAppBarColor,
                                    )
                                )
                            }
                        }
                        if (
                            viewModel.discountError
                        ) {
//                            Spacer(modifier = Modifier.size(10.dp))
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.error_worning),
                                    contentDescription = "Invalid or inapplicable discount code",
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.size(5.dp))
                                Text(
                                    text = "Invalid or inapplicable discount code",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        lineHeight = 24.sp,
                                        fontFamily = FontFamily(Font(R.font.font)),
                                        fontWeight = FontWeight(300),
                                        color = ErrorColor,
                                    )
                                )
                            }
                        }
                        Spacer(modifier = Modifier.size(22.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(CheckoutAppBarColor)
                                .padding(PADDING)

                        ) {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            )
                            {
                                Text(
                                    text = "Order details",
                                    fontSize = 20.sp,
                                    fontFamily = FontFamily(Font(R.font.font_med)),
                                    fontWeight = FontWeight(700),
                                    color = Color(0xff2D2D2D),
                                )

                                Row {
                                    Text(
                                        text = "IQD",
                                        fontSize = 12.sp,
                                        fontFamily = FontFamily(Font(R.font.font_med)),
                                        fontWeight = FontWeight(700),
                                        color = Color(0xff2D2D2D),
                                    )
                                    Text(
                                        text = formatNumber((viewModel.grandTotal.toDouble() + Constants.DELIVERY_FEES.toDouble()).toString()),
                                        fontSize = 20.sp,
                                        fontFamily = FontFamily(Font(R.font.font_med)),
                                        fontWeight = FontWeight(700),
                                        color = Color(0xff2D2D2D),
                                    )

                                }

                            }
                            Spacer(modifier = Modifier.size(5.dp))
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .alpha(0.5f)
                                    .height(1.dp)
                                    .background(LightGray)
                            ) {

                            }
                            Spacer(modifier = Modifier.size(5.dp))
                            Row {
                                Image(
                                    painter = painterResource(id = R.drawable.delivery_icon),
                                    contentDescription = "edit",
                                    Modifier
                                        .clip(
                                            RoundedCornerShape(8.dp)
                                        )
                                        .background(Color.LightGray)
                                        .padding(5.dp)
                                        .size(23.dp)
                                )
                                Spacer(modifier = Modifier.size(10.dp))
                                Column(
                                    Modifier.height(40.dp),
                                    verticalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Row {
                                        Text(
                                            text = "Estimated delivery date: ",
                                            style = TextStyle(
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight(400),
                                                color = checkoutLightText,
                                            )
                                        )
                                        Text(
                                            text = "24\\6 - 12\\7",
                                            style = TextStyle(
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight(700),
                                                color = checkoutLightText,
                                            )
                                        )
                                    }
                                    Row {
                                        Text(
                                            text = "Delivery fees: ",
                                            style = TextStyle(
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight(400),
                                                color = checkoutLightText,
                                            )
                                        )
                                        Text(
                                            text = "IQD",
                                            style = TextStyle(
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight(700),
                                                color = checkoutLightText,
                                            )
                                        )
                                        Text(
                                            text = formatNumber(DELIVERY_FEES),
                                            style = TextStyle(
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight(700),
                                                color = checkoutLightText,
                                            )
                                        )
                                    }

                                }
                            }

                        }
                    }

                }
            }
        }
        BottomAppBar(
            Modifier.align(Alignment.BottomCenter),
            isLoading = viewModel.isBottomButtonLoading,
            onClick = {
                if (Constants.WALLET_SELECTED) {
                    isPobUpVisible = true
                } else {
                    viewModel.placeOrder {
                        navController?.popBackStack()
                        navController?.popBackStack()
                        navController?.popBackStack()
                        navController?.popBackStack()
                        navController?.navigate(
                            HomeScreens.MyOrdersScreen.withArgs(
                                "false"
                            )
                        )
                    }

                }

            })

//        AnimatedVisibility(
//            visible = isPobUpVisible,
//            enter = expandHorizontally(animationSpec = tween(700)),
//            exit = shrinkHorizontally(animationSpec = tween(700))
//        ) {
        MyDialog(
            modifier = Modifier.align(Alignment.Center),
            visible = isPobUpVisible,
            onDismiss = {
                isPobUpVisible = false
            },
            navController = navController,
            viewModel = viewModel
        )
//        }

    }


}

@Preview(showBackground = true)
@Composable
fun AppBar() {
    val painter = painterResource(id = R.drawable.back)
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        Modifier

            .fillMaxWidth()

            .background(Color.White)

            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painter,
                contentDescription = "Home",
                modifier = Modifier
                    .size(20.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        (context as HomeActivity).onBackPressedDispatcher.onBackPressed()
                    },
                tint = MedDarkGrey
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsItem(
    title: String = "number of Items",
    value: String = "5",
    showPrice: Boolean = false,
    isDiscount: Boolean = false
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 5.dp), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(400),
                color = if (isDiscount) ErrorColor else checkoutLightText,
            )
        )
        Row {
            if (showPrice) {
                Text(
                    text = "IQD",
                    style = TextStyle(
                        fontSize = 11.sp,
                        fontWeight = FontWeight(400),
                        color = if (isDiscount) ErrorColor else checkoutLightText,
                    )
                )
            }
            Text(
                text = formatNumber(value),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(400),
                    color = if (isDiscount) ErrorColor else checkoutLightText,
                )
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedTextField(
    modifier: Modifier,
    hint: String = "",
    onTextChange: (String) -> Unit,
    text: String,
    isError : Boolean
) {
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = text,
        singleLine = true,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(Color.Black),
        onValueChange = { newText ->
            onTextChange(newText)
        },
        textStyle = TextStyle(textDirection = TextDirection.Content),
        modifier = modifier
            .height(40.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White),
    ) { innerTextField ->
        OutlinedTextFieldDefaults.DecorationBox(
            value = text,
            innerTextField = innerTextField,
            enabled = true,
            singleLine = true,
            contentPadding = PaddingValues(start = 10.dp, end = 10.dp),
            interactionSource = interactionSource,
            visualTransformation = VisualTransformation.None,
            placeholder = {
                Text(
                    text = hint,
                    style = TextStyle(textAlign = TextAlign.Start)
                )
            },
            container = {
                OutlinedTextFieldDefaults.ContainerBox(
                    enabled = true,
                    isError = isError,
                    interactionSource = interactionSource,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Yellow, // You can adjust the color as needed
                        unfocusedBorderColor = BorderColor
                    ),
                    shape = RoundedCornerShape(10.dp),
                    focusedBorderThickness = 1.dp,
                    unfocusedBorderThickness = 1.dp,
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomAppBar(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    isLoading: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier
            .height(60.dp)
            .fillMaxWidth()
            .dropShadow(
                color = ShadowGrey,
                blurRadius = 5.dp,
                borderRadius = 25.dp,

                )
            .clip(
                RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
            )
            .background(CheckoutAppBarColor), contentAlignment = Alignment.Center


    ) {
        BottomButton(isLoading = isLoading, onClick = {
            onClick?.invoke()
        })
    }
}

@Preview(showBackground = true)
@Composable
fun BottomButton(
    modifier: Modifier = Modifier,
    text: String = "View Cart",
    onClick: (() -> Unit)? = null,
    isLoading: Boolean = false
) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading_animation))
    val painter = painterResource(id = R.drawable.place_order_icon)
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .clickable {
                onClick?.invoke()
            }
            .background(Yellow)
            .padding(start = 25.dp, end = 25.dp, top = 5.dp, bottom = 5.dp),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(!isLoading, exit = shrinkHorizontally(), enter = expandHorizontally()) {
            Row {

                Image(
                    painter = painter,
                    contentDescription = text,
                    Modifier.align(Alignment.CenterVertically)
                )
                Box(
                    modifier = Modifier

                        .height(30.dp)
                        .width(1.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Place order",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.font_med)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF2D2D2D),
                    modifier = Modifier.align(Alignment.CenterVertically)

                )

            }
        }
        AnimatedVisibility(visible = isLoading, enter = fadeIn(), exit = fadeOut()) {
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
fun popUp(navController: NavController? = null, viewModel: ConfirmOrderViewModel) {
    var firstVisible by remember {
        mutableStateOf(true)
    }
    var secondVisible by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    Box(contentAlignment = Alignment.Center) {
        AnimatedVisibility(
            visible = firstVisible,
            exit = fadeOut(animationSpec = tween(100))
        ) {
            firstScreen() {
                scope.launch {
                    secondVisible = true
                    firstVisible = false
                    viewModel.requestWhatsAppOtp()
                }
            }
        }
        val density = LocalDensity.current
        AnimatedVisibility(
            visible = secondVisible,
            enter = expandVertically(
                // Expand from the top.
                expandFrom = Alignment.Top
            ) + fadeIn(
                animationSpec = tween(500)
            )
        ) {
            SecondScreen(navController = navController, onClick = {

            }, viewModel = viewModel)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun firstScreen(onClick: (() -> Unit)? = null) {
    val interactionSource = remember { MutableInteractionSource() }
    Column(Modifier.padding(23.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Specify your preferred verification method:  ",
            fontFamily = FontFamily(Font(R.font.font_med)),
            fontSize = 20.sp,
            fontWeight = FontWeight(1000),
            color = MedDarkGrey,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(9.dp))
        Row(Modifier.fillMaxWidth()) {
            SmsOrWhatsApp(
                Modifier
                    .weight(1f)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
//                        viewModel.textMessageSelected = true
//                        viewModel.whatsAppSelected = false
                        onClick?.invoke()
                    },
                selected = false,
                height = 45.dp
                //                viewModel.textMessageSelected
            )
            Spacer(modifier = Modifier.size(16.dp))
            SmsOrWhatsApp(
                Modifier
                    .weight(1f)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
//                        viewModel.textMessageSelected = false
//                        viewModel.whatsAppSelected = true
                        onClick?.invoke()
                    }, selected = false,
                height = 45.dp
//                viewModel.whatsAppSelected
                ,
                painter = painterResource(
                    id = R.drawable.whats_app_icon
                ), text = "WhatsApp"
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun SecondScreen(
    onClick: (() -> Unit)? = null,
    navController: NavController? = null,
    viewModel: ConfirmOrderViewModel? = null
) {
    val context = LocalContext.current
    DisposableEffect(Unit) {
        viewModel?.startTimer()
        onDispose {
            viewModel?.stopTimer()
        }
    }
    val interactionSource = remember { MutableInteractionSource() }
    val clockPainter = painterResource(id = R.drawable.clock_image)
    Column(Modifier.padding(26.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Enter Your verification code",
            fontFamily = FontFamily(Font(R.font.font_med)),
            fontSize = 20.sp,
            fontWeight = FontWeight(1000),
            color = MedDarkGrey,
            textAlign = TextAlign.Center
        )
        Text(
            text = "we’ve sent you a 6-digit code to +7899930202",
            fontFamily = FontFamily(Font(R.font.font_med)),
            fontSize = 16.sp,
            fontWeight = FontWeight(400),
            color = MedDarkGrey,
            textAlign = TextAlign.Center
        )
        OTPView(
            viewModel = viewModel
        )

        Row(Modifier.fillMaxWidth()) {
            SmsOrWhatsApp(
                Modifier
                    .weight(1f)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        onClick?.invoke()
//                        viewModel.textMessageSelected = true
//                        viewModel.whatsAppSelected = false
                    },
                selected = false,
                iconShow = false,
                text = "Cancel",
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                height = 45.dp
//                viewModel.textMessageSelected
            )
            Spacer(modifier = Modifier.size(16.dp))

            SmsOrWhatsApp(
                Modifier
                    .weight(1f)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onClick?.invoke()
                        viewModel?.verifyOtp(context) {
                            navController?.navigate(
                                HomeScreens.MyOrdersScreen.withArgs(
                                    "false"
                                )
                            )
                        }
                    }, selected = true,
                iconShow = false,
                text = "Submit",
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                height = 45.dp
//                viewModel.whatsAppSelected
                ,
                painter = painterResource(
                    id = R.drawable.whats_app_icon
                )
            )
        }
        Spacer(modifier = Modifier.size(25.dp))
        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Resend code in",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.font)),
                    fontWeight = FontWeight(400),
                    color = MedDarkGrey,

                    )
                Spacer(modifier = Modifier.size(6.dp))
                Row(
                    Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(CheckoutAppBarColor)
                        .padding(1.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = clockPainter,
                        contentDescription = "time",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.size(9.dp))
                    Text(
                        text = "00:${(viewModel?.timerState)?.collectAsState()?.value}",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.font)),
                        fontWeight = FontWeight(400),
                        color = MedDarkGrey,
                    )
                }
            }
            Text(
                text = "RESEND CODE ",
                fontSize = 18.sp,
                fontWeight = FontWeight(860),
                color = if (viewModel?.isResendActive
                        ?: false
                ) ActiveResendCodeColor else InActiveResendCodeColor,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.font)),
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    if (viewModel?.isResendActive ?: false)
                        viewModel?.resendOTP(context)

                }
            )
            Spacer(modifier = Modifier.size(32.dp))

        }

        Text(
            text = "or use a different confirmation method",
            fontFamily = FontFamily(Font(R.font.font_med)),
            fontSize = 16.sp,
            maxLines = 1,
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight(400),
            color = MedDarkGrey,
            textAlign = TextAlign.Center
        )
    }

}

@Preview(showBackground = true)
@Composable
fun ThirdScreen() {

}

@Composable
fun OtpChar(
    modifier: Modifier = Modifier,
    onTextChange: (data: String) -> Unit = {},
    keyboardAction: KeyboardActions? = null,
) {
    val painter = painterResource(id = R.drawable.dot)
    val pattern = remember { Regex("^[^\\t]*\$") } //to not accept the tab key as value
    var (text, setText) = remember { mutableStateOf("") }
    var isFull by remember {
        mutableStateOf(false)
    }
    val maxChar = 1
    val focusManager = LocalFocusManager.current

    LaunchedEffect(
        key1 = text,
    ) {
        if (text.isNotEmpty()) {
            focusManager.moveFocus(
                focusDirection = FocusDirection.Next,
            )
        }
    }

    Box(
        contentAlignment = Alignment.Center
    ) {
        TextField(
            value = text,
            onValueChange = {
                if (it.length <= maxChar &&
                    ((it.isEmpty() || it.matches(pattern)))
                ) {
                    setText(it)
                    onTextChange(it)
                    isFull = it.isNotEmpty()
                }
            },
            keyboardActions = keyboardAction ?: KeyboardActions.Default,
            modifier = modifier
                .width(45.dp)
                .onKeyEvent {
                    if (it.key == Key.Tab) {
                        focusManager.moveFocus(FocusDirection.Next)
                        true
                    }
                    if (text.isEmpty() && it.key == Key.Backspace) {
                        focusManager.moveFocus(FocusDirection.Previous)
                    }
                    false
                },
            textStyle = LocalTextStyle.current.copy(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.font_med)),
                textAlign = TextAlign.Center
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = if (keyboardAction == null) ImeAction.Next else ImeAction.Done
            ),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent
            ),
        )
        if (!isFull) {
            Image(painter = painter, contentDescription = "")
        }

    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OTPView(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: ConfirmOrderViewModel? = null
) {
    val context = LocalContext.current
    val (item1, item2, item3, item4, item5, item6) = FocusRequester.createRefs()
    Box(
        modifier, contentAlignment = Alignment.Center
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            OtpChar(
                modifier = Modifier
                    .focusRequester(item1)
                    .focusProperties {
                        next = item2
                        previous = item1
                    },
                onTextChange = {
                    viewModel?.updateOtpAtIndex(0, it)
                }
            )
            OtpChar(
                modifier = Modifier
                    .focusRequester(item2)
                    .focusProperties {
                        next = item3
                        previous = item1
                    },
                onTextChange = {
                    viewModel?.updateOtpAtIndex(1, it)
                }
            )
            OtpChar(
                modifier = Modifier
                    .focusRequester(item3)
                    .focusProperties {
                        next = item4
                        previous = item2
                    },
                onTextChange = {
                    viewModel?.updateOtpAtIndex(2, it)
                }
            )
            OtpChar(
                modifier = Modifier
                    .focusRequester(item4)
                    .focusProperties {
                        previous = item3
                        next = item5
                    },
                onTextChange = {
                    viewModel?.updateOtpAtIndex(3, it)
                }
            )
            OtpChar(
                modifier = Modifier
                    .focusRequester(item5)
                    .focusProperties {
                        previous = item4
                        next = item6
                    },
                onTextChange = {
                    viewModel?.updateOtpAtIndex(4, it)
                }
            )
            OtpChar(
                modifier = Modifier
                    .focusRequester(item6)
                    .focusProperties {
                        previous = item5
                        next = item6
                    },
                onTextChange = {
                    viewModel?.updateOtpAtIndex(5, it)
                },
//                keyboardAction = KeyboardActions(
//                    onDone = {
////                        viewModel.verifyOtp(context){
////                            navController?.navigate(com.simma.simmaapp.presentation.loginScreen.Screen.SetPasswordScreen.route)
////                        }
//
//                    }
//                )
            )

        }
    }
}

@Composable
fun MyDialog(
    modifier: Modifier = Modifier,
    onDismiss: (() -> Unit)? = null,
    visible: Boolean,
    navController: NavController? = null,
    viewModel: ConfirmOrderViewModel
) {
    val scale1 = remember {
        Animatable(0.6f)
    }
    var scope = rememberCoroutineScope()
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier
            .fillMaxSize()
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween()),
            exit = fadeOut()
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .alpha(0.2f)
                    .background(Color.Black)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        scope.launch { scale1.animateTo(0.6f, animationSpec = tween(500)) }
                        onDismiss?.invoke()
                    }
            ) {
                DisposableEffect(Unit) {
                    ((context) as HomeActivity).window.apply {
                        navigationBarColor =
                            ContextCompat.getColor(context, R.color.statusAndNavigationDialog)
                        statusBarColor =
                            ContextCompat.getColor(context, R.color.statusAndNavigationDialog)
                    }
                    onDispose {
                        ((context) as HomeActivity).window.apply {
                            navigationBarColor =
                                ContextCompat.getColor(context, R.color.statusAndNavigation)
                            statusBarColor =
                                ContextCompat.getColor(context, R.color.statusAndNavigation)
                        }
                    }
                }
            }
        }
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300)),
            modifier = Modifier.align(Alignment.Center)
        ) {
            DisposableEffect(Unit) {
                scope.launch { scale1.animateTo(1f, animationSpec = tween(500)) }
                onDispose {
                    scope.launch { scale1.animateTo(0.6f, animationSpec = tween(500)) }
                }
            }
            Card(
                modifier = Modifier
                    .padding(start = 20.dp, end = 10.dp)
//                    .graphicsLayer {
//                        this.shadowElevation = 10f
//                        this.shape = RoundedCornerShape(16.dp)
//                        this.scaleX = scale1.value
//                        this.scaleY = scale1.value
//                    }
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .scale(scale1.value),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                popUp(navController = navController, viewModel)
            }
        }
    }
}
