package com.simma.simmaapp.presentation.deleveryAndPayment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.simma.simmaapp.R
import com.simma.simmaapp.model.citiesModel.CitiesModelItem
import com.simma.simmaapp.presentation.homePage.HomeActivity
import com.simma.simmaapp.presentation.homePage.ui.theme.Yellow
import com.simma.simmaapp.presentation.homeScreen.HomeScreens
import com.simma.simmaapp.presentation.theme.Black500
import com.simma.simmaapp.presentation.theme.CheckoutAppBarColor
import com.simma.simmaapp.presentation.theme.Dimen.PADDING
import com.simma.simmaapp.presentation.theme.DropDownTextColor
import com.simma.simmaapp.presentation.theme.ErrorColor
import com.simma.simmaapp.presentation.theme.LightGrey
import com.simma.simmaapp.presentation.theme.MedDarkGrey
import com.simma.simmaapp.presentation.theme.SelectedBodyYellow
import com.simma.simmaapp.presentation.theme.SelectedBorderYellow
import com.simma.simmaapp.presentation.theme.ShadowGrey
import com.simma.simmaapp.presentation.theme.Yellow200
import com.simma.simmaapp.presentation.theme.checkoutLightText
import com.simma.simmaapp.utils.Constants.PAYMENT_METHODS
import com.simma.simmaapp.utils.Constants.WALLET_SELECTED
import com.simma.simmaapp.utils.Helpers.formatNumber
import com.simma.simmaapp.utils.ModifierUtil.dropShadow

@Preview(showBackground = true)
@Composable
fun AppBar() {
    val interactionSource = remember { MutableInteractionSource() }

    val painter = painterResource(id = R.drawable.back)
    val context = LocalContext.current
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
                        interactionSource = interactionSource, indication = null
                    ) {
                        (context as HomeActivity).onBackPressedDispatcher.onBackPressed()
                    },
                tint = MedDarkGrey
            )
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun DeliveryAndPaymentScreen(
    navController: NavController? = null
) {
    val interactionSource = remember { MutableInteractionSource() }

    val viewModel: DeliveryAndPaymentViewModel = hiltViewModel()
    var isOneSelected by remember {
        mutableStateOf(false)
    }
    var isTwoSelected by remember {
        mutableStateOf(false)
    }
    var isThreeSelected by remember {
        mutableStateOf(false)
    }
    var isDropDownExpanded by remember {
        mutableStateOf(false)
    }
    var detailedAddress by rememberSaveable {
        mutableStateOf("")
    }
    var showHint by rememberSaveable {
        mutableStateOf(true)
    }

    Box {
        Column(
            Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(start = PADDING, end = PADDING),
        ) {
            AppBar()
            Text(
                text = "Delivery address",
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.font_med)),
                fontWeight = FontWeight(700),
                color = Color(0xff2D2D2D),
                modifier = Modifier.padding()
            )
//            Spacer(modifier = Modifier.size(6.dp))
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
                Box(
                    Modifier
                        .weight(3f)
                        .align(Alignment.Bottom)
                ) {

                    CustomDropDownMenu(
                        modifier = Modifier, isExpanded = isDropDownExpanded, onChangeExpanding = {
                            isDropDownExpanded = !isDropDownExpanded
                        }, viewModel = viewModel
                    )
                }

                Spacer(modifier = Modifier.size(12.dp))
                Column(Modifier.weight(2f), horizontalAlignment = Alignment.Start) {
                    Text(
                        text = "Delivery fees",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.font_med)),
                        fontWeight = FontWeight(400),
                        color = LightGrey
                    )
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(CheckoutAppBarColor)
                            .padding(top = 2.dp, bottom = 2.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "IQD ",
                            fontSize = 11.sp,
                            fontFamily = FontFamily(Font(R.font.font_med)),
                            fontWeight = FontWeight(860),
                            color = Black500,
                        )
                        Text(
                            text = formatNumber(viewModel.deliveryFees),
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.font_med)),
                            fontWeight = FontWeight(860),
                            color = Black500,
                        )
                    }


                }
            }
            Spacer(modifier = Modifier.size(8.dp))
//            OutlinedTextField(
//                modifier = Modifier
//                    .height(96.dp)
//                    .fillMaxWidth(),
//                hint = "Detailed Address"
//            )
            Box {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp), onTextChange = {
                        viewModel.deliveryAddress = it
                        showHint = it.isEmpty()
                    }, text = viewModel.deliveryAddress , isError = viewModel.isDetailedAddressError
                )

                if (viewModel.isDetailedAddressError) {

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .align(
                                Alignment.BottomEnd
                            )
                            .padding(end = 10.dp, bottom = 10.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.error_worning),
                            contentDescription = "Invalid or inapplicable discount code",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.size(5.dp))
                        Text(
                            text = "Field is required",
                            style = TextStyle(
                                fontSize = 12.sp,
                                lineHeight = 24.sp,
                                fontFamily = FontFamily(Font(R.font.font_med)),
                                fontWeight = FontWeight(300),
                                color = ErrorColor,
                            ),

                            )
                    }
                }

            }


            Spacer(modifier = Modifier.size(25.dp))
            Text(
                text = "Choose your payment method",
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.font_med)),
                fontWeight = FontWeight(700),
                color = checkoutLightText
            )
            Spacer(
                modifier = Modifier.size(16.dp)
            )
            // simma wallet payment
            if (PAYMENT_METHODS.find { it.name == "wallet" }?.active ?: false) {
                PaymentItem(selected = isOneSelected,
                    paymentMethod = 1,
                    discount = viewModel.walletDiscountAmount,
                    showDiscount = viewModel.walletDiscountShow,
                    walletBalance = viewModel.walletBalance,
                    modifier = Modifier.clickable(
                        interactionSource = interactionSource, indication = null
                    ) {
                        isTwoSelected = false
                        isOneSelected = true
                        isThreeSelected = false

                        // this two line of code is for calculating the delivery fees if the selected payment method have free delivery or not
                        viewModel.freeDelivery =
                            PAYMENT_METHODS.find { it.name == "wallet" }!!.freeDelivery
                        viewModel.calculateDelivery()
                        WALLET_SELECTED = true
                        viewModel.noPaymentMethodSelected = false
                    },
                    freeDelivery = PAYMENT_METHODS.find { it.name == "wallet" }!!.freeDelivery
                )
            }
            Spacer(
                modifier = Modifier.size(6.dp)
            )
            // cash on delivery payment
            if (PAYMENT_METHODS.find { it.name == "cashOnDelivery" }?.active ?: true) {
                PaymentItem(
                    selected = isTwoSelected,
                    text = "Cash on delivery",
                    discount = viewModel.codDiscountAmount,
                    showDiscount = viewModel.codDiscountShow,
                    painter = painterResource(id = R.drawable.cash_on_delivary_image),
                    modifier = Modifier.clickable(
                        interactionSource = interactionSource, indication = null
                    ) {
                        isTwoSelected = true
                        isOneSelected = false
                        isThreeSelected = false

                        // this two line of code is for calculating the delivery fees if the selected payment method have free delivery or not
                        viewModel.freeDelivery =
                            PAYMENT_METHODS.find { it.name == "cashOnDelivery" }!!.freeDelivery
                        viewModel.calculateDelivery()
                        WALLET_SELECTED = false
                        viewModel.noPaymentMethodSelected = false
                    },
                    freeDelivery = PAYMENT_METHODS.find { it.name == "cashOnDelivery" }!!.freeDelivery
                )
            }
//            Spacer(
//                modifier = Modifier
//                    .size(6.dp)
//            )
//
//            PaymentItem(selected = isThreeSelected, modifier = Modifier.clickable {
//                isTwoSelected = false
//                isOneSelected = false
//                isThreeSelected = true
//            })


        }
        BottomAppBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            navController = navController,
            viewModel = viewModel
        )

        Box(Modifier.wrapContentSize()) {

            // Dismiss layout
            if (isDropDownExpanded) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .clickable(
                            interactionSource = interactionSource, indication = null
                        ) {
                            isDropDownExpanded = false
                        })
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 170.dp, start = PADDING, end = PADDING)
            ) {
                Box(Modifier.weight(3f)) {
                    CustomExposedDropDownMenu(
                        modifier = Modifier, isDropDownExpanded, onItemChoose = {
                            viewModel.cityDeliveryFees = it.deliveryFees.toString()
                            viewModel.deliveryCity = it.id
                            viewModel.deliveryCityName = it.name.en
                            viewModel.calculateDelivery()
                            isDropDownExpanded = false
                        }, list = viewModel.citiesList
                    )
                }
                Spacer(modifier = Modifier.size(12.dp))

                Box(
                    Modifier
                        .height(40.dp)
                        .weight(2f)
                ) {

                }
            }
        }

        if(viewModel.isWalletFrozen){
            FrozenWalletDialog {
                viewModel.isWalletFrozen = false
            }
        }


    }

}


@Preview(showBackground = true)
@Composable
fun PaymentItem(
    painter: Painter = painterResource(id = R.drawable.simma_logo),
    text: String = "Simma Wallet",
    selected: Boolean = false,
    paymentMethod: Int = 2,
    modifier: Modifier = Modifier,
    freeDelivery: Boolean = true,
    walletBalance : String ="0",
    discount : String = "",
    showDiscount : Boolean = false
) {
    Row(
        modifier
            .fillMaxWidth()
            .border(
                1.dp,
                if (selected) SelectedBorderYellow else Color.Transparent,
                RoundedCornerShape(12.dp)
            )
            .clip(
                RoundedCornerShape(12.dp)
            )
            .background(if (selected) SelectedBodyYellow else Color.Transparent),
        verticalAlignment = Alignment.CenterVertically,

        ) {

        Image(
            painter = painter, contentDescription = text,
            Modifier
                .size(68.dp)
                .clip(
                    RoundedCornerShape(12.dp)
                )
                .border(
                    if (!selected) 1.dp else 0.dp,
                    if (!selected) Color.LightGray else Color.Transparent,
                    RoundedCornerShape(12.dp)
                )
                .padding(15.dp)
        )
        Spacer(modifier = Modifier.size(12.dp))
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Box {
                Text(
                    text = text,
                    fontFamily = FontFamily(Font(R.font.font_med)),
                    fontSize = 16.sp,
                    fontWeight = FontWeight(1000),
                    color = checkoutLightText,
                    modifier = Modifier.align(Alignment.TopStart)
                )
                if (showDiscount) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(top = 18.dp)
                    ) {
                        Text(
                            text = "Discount ",
                            fontFamily = FontFamily(Font(R.font.font_med)),
                            fontSize = 12.sp,
                            fontWeight = FontWeight(400),
                            color = ErrorColor,
                        )
                        Text(
                            text = "IQD",
                            fontFamily = FontFamily(Font(R.font.font_med)),
                            fontSize = 9.sp,
                            fontWeight = FontWeight(400),
                            color = ErrorColor,
                        )
                        Text(
                            text = formatNumber(discount),
                            fontFamily = FontFamily(Font(R.font.font_med)),
                            fontSize = 12.sp,
                            fontWeight = FontWeight(400),
                            color = ErrorColor,
                        )
                    }

                }
                if (freeDelivery) {
                    Text(
                        text = "Free Delivery",
                        fontFamily = FontFamily(Font(R.font.font_med)),
                        fontSize = 12.sp,
                        fontWeight = FontWeight(400),
                        color = ErrorColor,
                        modifier = Modifier.padding(top = if (paymentMethod == 1) 32.dp else 18.dp)
                    )
                }
            }
            if (paymentMethod == 1) {
                Row(
                    Modifier
                        .padding(end = 7.dp)
                        .align(Alignment.Top)
                ) {
                    Text(
                        text = "IQD",
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.font_med)),
                        fontWeight = FontWeight(860),
                        color = checkoutLightText,

                        )
                    Text(
                        text = formatNumber(walletBalance),
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.font_med)),
                        fontWeight = FontWeight.Bold,
                        color = checkoutLightText,

                        )
                }
            }

        }


    }

}


@Preview(showBackground = true)
@Composable
fun BottomButton(modifier: Modifier = Modifier, text: String = "View Cart") {
    val painter = painterResource(id = R.drawable.proceed_arrow)
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
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
            Box(
                modifier = Modifier
                    .height(30.dp)
                    .width(1.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Continue",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.font_med)),
                fontWeight = FontWeight(700),
                color = Color(0xFF2D2D2D),
                modifier = Modifier.align(Alignment.CenterVertically)

            )

        }
    }
}

@Composable
fun CustomDropDownMenu(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    onChangeExpanding: () -> Unit,
    viewModel: DeliveryAndPaymentViewModel,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val closedPainter = painterResource(id = R.drawable.clased_drop_done)
    val openedPainter = painterResource(id = R.drawable.open_drop_down)
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        1.dp, LightGrey, RoundedCornerShape(10.dp)
                    )
                    .padding(start = 10.dp)
                    .height(40.dp), contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = viewModel.deliveryCityName, style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontFamily = FontFamily(Font(R.font.font)),
                        fontWeight = FontWeight(400),
                        color = DropDownTextColor,
                    )
                )
                Image(painter = if (isExpanded) openedPainter else closedPainter,
                    contentDescription = "city menu",
                    Modifier
                        .padding(end = 10.dp)
                        .align(
                            Alignment.CenterEnd
                        )
                        .clickable(
                            interactionSource = interactionSource, indication = null
                        ) {
                            onChangeExpanding()

                        })

            }
        }

    }
}


@Composable
fun BottomAppBar(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: DeliveryAndPaymentViewModel
) {

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
        val interactionSource = remember { MutableInteractionSource() }
        BottomButton(modifier = Modifier.clickable(
            interactionSource = interactionSource, indication = null
        ) {
            if (viewModel.saveDeliveryData()) navController?.navigate(HomeScreens.ConfirmOrderScreen.route)
        })
    }
}

@Composable
fun MenuItem(item: CitiesModelItem, onItemSelected: (String) -> Unit) {
    Box(
        Modifier.background(Color.White)
    ) {
        DropdownMenuItem(text = {
            Text(
                text = item.name.en,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.font_med)),
                lineHeight = 24.sp,
                color = DropDownTextColor,
                fontWeight = FontWeight(400),
            )
        }, onClick = {
            onItemSelected(
                item.name.en
            )
        }, modifier = Modifier.background(Color.Transparent)

        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .alpha(0.6f)
                .padding(start = 10.dp, end = 10.dp)
                .height(1.dp)
                .background(LightGrey)
        )
    }
}

// outlined top start gravity no hint animation outlined text field
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedTextField(
    modifier: Modifier,
    hint: String = "Detailed Address",
    onTextChange: (String) -> Unit,
    text: String,
    isError: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = text,
        singleLine = false, // Set singleLine to false for multi-line support
        interactionSource = interactionSource,
        cursorBrush = SolidColor(Color.Black),
        onValueChange = { newText ->
            onTextChange(newText)
        },
        textStyle = TextStyle(
            textDirection = TextDirection.Content,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontFamily = FontFamily(Font(R.font.font)),
            fontWeight = FontWeight(400),
            color = DropDownTextColor,
        ),
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White),
    ) { innerTextField ->
        OutlinedTextFieldDefaults.DecorationBox(value = text,
            innerTextField = innerTextField,
            enabled = true,
            singleLine = false,
            contentPadding = PaddingValues(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
            interactionSource = interactionSource,
            visualTransformation = VisualTransformation.None,
            placeholder = {
                Text(
                    text = hint, style = TextStyle(
                        textAlign = TextAlign.Start,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontFamily = FontFamily(Font(R.font.font)),
                        fontWeight = FontWeight(400),
                        color = LightGrey,
                    )


                )
            },
            container = {
                OutlinedTextFieldDefaults.ContainerBox(
                    enabled = true,
                    isError = isError,
                    interactionSource = interactionSource,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Yellow, // You can adjust the color as needed
                        unfocusedBorderColor = LightGrey,
                        focusedLabelColor = Yellow,
                        unfocusedLabelColor = LightGrey,
                        errorBorderColor = ErrorColor,
                        errorLabelColor = ErrorColor
                    ),
                    shape = RoundedCornerShape(10.dp),
                    focusedBorderThickness = 1.dp,
                    unfocusedBorderThickness = 1.dp,
                )
            })
    }
}

@Composable
fun CustomExposedDropDownMenu(
    modifier: Modifier = Modifier,
    isExposed: Boolean = true,
    list: List<CitiesModelItem>,
    onItemChoose: (CitiesModelItem) -> Unit = {},
) {
    var isListExposed by remember {
        mutableStateOf(isExposed)
    }


    AnimatedVisibility(
        visible = isExposed,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        DisposableEffect(Unit) {
            isListExposed = isExposed
            onDispose {
                isListExposed = false
            }
        }
        Box(
            modifier.fillMaxSize()
        ) {
            AnimatedVisibility(
                visible = isListExposed, enter = expandVertically(), exit = shrinkVertically()
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    border = BorderStroke(1.dp, LightGrey),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    )
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 170.dp),
                        contentPadding = PaddingValues(5.dp),
                    ) {
                        items(list) { item ->
                            MenuItem(item) { selectedItem ->
                                onItemChoose(item)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FrozenWalletDialog(onDismiss: (() -> Unit)? = null) {
    val context = LocalContext.current
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.wallet_frozen))
    Dialog(onDismissRequest = { onDismiss?.invoke() }) {
        Card(
            modifier = Modifier.graphicsLayer {
                    this.shadowElevation = 10f
                    this.shape = RoundedCornerShape(16.dp)
                }, shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LottieAnimation(composition = composition, modifier = Modifier.height(124.dp))
                Text(
                    text = "Your wallet is frozen and cannot be used at the moment. For assistance please contact customer support\n",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.font_med)),
                        fontWeight = FontWeight(590),
                        color = Color(0xFF2D2D2D),
                        textAlign = TextAlign.Center,
                    )
                )

                Row(
                    Modifier
                        .height(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Yellow200)
                        .border(1.dp, Yellow, RoundedCornerShape(12.dp))
                        .clickable {
                            // add calling intent and change the lottifile
                            val phoneNumber = "1234567890"
                            val intent = Intent(Intent.ACTION_DIAL)
                            intent.data = Uri.parse("tel:$phoneNumber")
                            context.startActivity(intent)

                        }
                        .padding(start = 36.dp, end = 36.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.call),
                        contentDescription = "call support",
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        text = "Call us", style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.font)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFF4A4A4A),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }
        }
    }
}

