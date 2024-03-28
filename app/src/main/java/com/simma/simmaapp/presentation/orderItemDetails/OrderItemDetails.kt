package com.simma.simmaapp.presentation.orderItemDetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.simma.simmaapp.R
import com.simma.simmaapp.model.myOrdersModel.DiscountModel
import com.simma.simmaapp.model.myOrdersModel.Item
import com.simma.simmaapp.model.myOrdersModel.PaymentMethod
import com.simma.simmaapp.presentation.cartPage.DetailsItem
import com.simma.simmaapp.presentation.homePage.ui.theme.Yellow
import com.simma.simmaapp.presentation.theme.CanceledOrderTextColor
import com.simma.simmaapp.presentation.theme.CheckoutAppBarColor
import com.simma.simmaapp.presentation.theme.Dimen.PADDING
import com.simma.simmaapp.presentation.theme.MedDarkGrey
import com.simma.simmaapp.presentation.theme.ShadowGrey
import com.simma.simmaapp.presentation.theme.checkoutLightText
import com.simma.simmaapp.utils.Helpers.formatDate
import com.simma.simmaapp.utils.Helpers.formatNumber
import com.simma.simmaapp.utils.ModifierUtil.dropShadow


@Preview(showBackground = true)
@Composable
fun OrderItemDetails(
    orderNumber: String = "SH803347290j2972022",
    date: String = "2021-07-26T07:56:05.683Z",
    paymentMethod: String = "Cash on delivery",
    onBackPress: (() -> Unit)? = null,
    onCancelOrderPress: ((String) -> Unit)? = null,
    itemsList: List<Item> = listOf(),
    isExpanded: Boolean = false,
    onItemClick: ((
        itemUrl: String,
        cartUrl: String,
        merchantId: String,
        extractionCode: String,
        paymentMethodList: List<PaymentMethod>,
        item : Item
    ) -> Unit)? = null,
    onItemDelete: ((String) -> Unit)? = null,
    onUnCancelItem: () -> Unit = {},
    onCancelItem: (String) -> Unit = {},
    onExpandChange: (() -> Unit)? = null,
    numberOfItem: Int = 5,
    totalInsight: Double = 20.76,
    shipping: Double = 5.76,
    grandTotal: Double = 732.335,
    discountList: List<DiscountModel> = listOf(),
    cartUrl: String = "",
    merchantId: String = "",
    extractionCode: String = "",
    paymentMethodList: List<PaymentMethod> = listOf(),
    orderId: String = "",
    externalStatus: String = "",
    showCancelPopUp: Boolean = false,
    showCancelItemPopUp: Boolean = false,
    onCancelPopUpVisibilityChange: (Boolean) -> Unit = {},
    onCancelOrderItemVisibilityChange: (Boolean) -> Unit = {}
) {

    val interactionSource = remember { MutableInteractionSource() }
    var itemId by remember {
        mutableStateOf("")
    }
    Scaffold(
        Modifier.background(Color.White),
    ) { paddingValues ->
        Box(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column {

                AppBar(
                    onBackPress = onBackPress,
                    externalStatus = externalStatus,
                    onCancelPopUpVisibilityChange = {
                        onCancelPopUpVisibilityChange(it)
                    }
                )
                MainOrderDetails(
                    list = itemsList,
                    orderNumber = orderNumber,
                    date = date,
                    paymentMethod = paymentMethod,
                    cartUrl = cartUrl,
                    merchantId = merchantId,
                    extractionCode = extractionCode,
                    paymentMethodList = paymentMethodList,
                    onItemClick = onItemClick,
                    onUnCancelItem = onUnCancelItem,
                    onCancelItem = onCancelItem,
                    externalStatus = externalStatus,
                    onCancelOrderItemVisibilityChange = { isVisible, item ->
                        itemId = item
                        onCancelOrderItemVisibilityChange.invoke(isVisible)

                    }
                )
            }
            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn(animationSpec = tween(1000)),
                exit = fadeOut(animationSpec = tween(1000))
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .alpha(0.6f)
                        .background(Color.Black)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            onExpandChange?.invoke()
                        })
            }
            ExpandableBottomBar(
                Modifier.align(Alignment.BottomStart),
                isExpanded,
                grandTotal = grandTotal,
                numberOfItems = numberOfItem,
                totalInsight = totalInsight,
                shipping = shipping,
                discountsList = discountList
            ) {
                onExpandChange?.invoke()
            }
            if (showCancelPopUp)
                CancelOrderDialog(Modifier.align(Alignment.Center),
                    text = "Are you sure you want to cancel your order?", onCancelOrderPressOrItem = {
                        onCancelOrderPress?.invoke(orderId)
                    }, onDismiss = {
                        onCancelPopUpVisibilityChange(false)
                    })
            if (showCancelItemPopUp)
                CancelOrderDialog(Modifier.align(Alignment.Center),
                    text = "Are you sure you want to cancel this item from your order?",
                    onCancelOrderPressOrItem = {
                        onCancelItem.invoke(itemId)
                    },
                    onDismiss = {
                        onCancelOrderItemVisibilityChange(false)
                    })
        }

    }
}

@Preview(showBackground = true)
@Composable
fun AppBar(
    onBackPress: (() -> Unit)? = null,
    externalStatus: String = "",
    onCancelPopUpVisibilityChange: (Boolean) -> Unit = {}
) {
    val painter = painterResource(id = R.drawable.back)
    Row(
        Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(start = PADDING, end = PADDING)
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Icon(
                painter = painter,
                contentDescription = "Home",
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        onBackPress?.invoke()
                    },
                tint = MedDarkGrey
            )
            AnimatedVisibility(
                visible = (externalStatus == "processing"),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Row(
                    Modifier
                        .height(30.dp)
                        .clip(shape = RoundedCornerShape(size = 7.dp))
                        .clickable {
                            onCancelPopUpVisibilityChange(true)

                        }
                        .border(
                            width = 0.5.dp,
                            color = Color(0xFFC4C3C3),
                            shape = RoundedCornerShape(size = 7.dp)
                        )
                        .padding(7.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val garbageImage = painterResource(id = R.drawable.garbage_icon)
                    Image(
                        modifier = Modifier
                            .height(15.dp)
                            .width(12.dp),
                        painter = garbageImage,
                        contentDescription = "cancel Order"
                    )
                    Spacer(modifier = Modifier.size(7.dp))
                    Text(
                        text = "Cancel order",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.font_med)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFF4A4A4A),
                        )
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainOrderDetails(
    orderNumber: String = "SH803347290j2972022",
    date: String = "27/12/2024",
    paymentMethod: String = "Cash on delivery",
    list: List<Item> = listOf(),
    onItemClick: ((
        itemUrl: String,
        cartUrl: String,
        merchantId: String,
        extractionCode: String,
        paymentMethodList: List<PaymentMethod>,
        item : Item
    ) -> Unit)? = null,
    cartUrl: String = "",
    merchantId: String = "",
    extractionCode: String = "",
    paymentMethodList: List<PaymentMethod> = listOf(),
    onUnCancelItem: () -> Unit = {},
    onCancelItem: (String) -> Unit = {},
    externalStatus: String = "processing",
    onCancelOrderItemVisibilityChange: (Boolean, itemId: String) -> Unit = { _, _ -> },
    updateCanceledItemId: (String) -> Unit = {}


) {
    Column(
        Modifier
            .padding(start = PADDING, end = PADDING)
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFF3F3F3), shape = RoundedCornerShape(size = 4.dp))
                .padding(7.dp)
        ) {
            Text(
                text = "Order number:",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.font)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF4A4A4A),
                )
            )
            Spacer(modifier = Modifier.size(5.dp))
            Text(
                text = orderNumber,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.font_med)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF4A4A4A),
                )
            )
            Spacer(modifier = Modifier.size(6.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier
                        .border(
                            width = 0.5.dp,
                            color = Color(0xFFC4C3C3),
                            shape = RoundedCornerShape(size = 7.dp)
                        )
                        .height(30.dp)
                        .width(30.dp)
                        .background(
                            color = Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(size = 7.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    val calendarPainter = painterResource(id = R.drawable.calender_icon)
                    Image(
                        modifier = Modifier.size(15.dp),
                        painter = calendarPainter,
                        contentDescription = "Date"
                    )
                }
                Spacer(modifier = Modifier.size(6.dp))
                Text(
                    text = formatDate(date),
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.font_med)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF4A4A4A),
                    )
                )
                Spacer(modifier = Modifier.size(23.dp))
                Box(
                    Modifier
                        .border(
                            width = 0.5.dp,
                            color = Color(0xFFC4C3C3),
                            shape = RoundedCornerShape(size = 7.dp)
                        )
                        .height(30.dp)
                        .width(30.dp)
                        .background(
                            color = Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(size = 7.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    val cashPainter = painterResource(id = R.drawable.cash_iconn)
                    Image(
                        modifier = Modifier.size(15.dp),
                        painter = cashPainter,
                        contentDescription = "Date"
                    )
                }
                Spacer(modifier = Modifier.size(6.dp))
                Text(
                    text = if (paymentMethod == "cashOnDelivery") "Cash On Delivery" else "Wallet",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.font_med)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF4A4A4A),
                    )
                )

            }
        }
        LazyColumn() {
            items(list.size) {
                CartItems(
                    isCanceled = list[it].status == "cancelledByUser" ||
                            list[it].status == "cancelledByAdmin" ||
                            list[it].status == "lost" ||
                            list[it].status == "damaged" ||
                            list[it].status == "missed"||
                            list[it].status == "refundedFromMerchant"||
                            list[it].status == "rejectedByCustomer"||
                            list[it].status == "failedDelivery"
                    ,
                    name = list[it].name,
                    sku = list[it].sku,
                    size = list[it].size,
                    quantity = list[it].quantity,
                    iqdPrice = list[it].iqdPrice,
                    imageUrl = list[it].imageUrl,
                    onItemClick = {
                        onItemClick?.invoke(
                            list[it].url,
                            cartUrl,
                            merchantId,
                            extractionCode,
                            paymentMethodList,
                            list[it]
                        )
                    },
                    externalStatus = externalStatus,
                    onCancelItem = {
//                        onCancelItem.invoke(list[it]._id)
                        onCancelOrderItemVisibilityChange.invoke(true, list[it]._id)
                    },
                    onUnCancelItem = onUnCancelItem
                )
            }
            item{
                Box(modifier = Modifier.size(60.dp))
            }
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Preview(showBackground = true)
@Composable
fun CartItems(
    modifier: Modifier = Modifier,
    isCanceled: Boolean = true,
    imageUrl: String = "",
    name: String = "نميباذنا«ىزشسمتسياسزيدازمنسيذانسنايبدزذ ",
    sku: String = "aksjdgjahdgdvcgvdchgsvcg",
    size: String = "One Size",
    quantity: Double = 1.0,
    iqdPrice: Double = 2467.0,
    onItemClick: () -> Unit = {},
    onCancelItem: () -> Unit = {},
    onUnCancelItem: () -> Unit = {},
    changeCancellationTo: (Boolean) -> Unit = {},
    externalStatus: String = "processing"
) {
    val textColor = if (isCanceled) CanceledOrderTextColor else checkoutLightText
    Column(modifier = Modifier
        .clickable {
            onItemClick()
        }
        .padding(start = PADDING, end = PADDING, top = PADDING)) {
        Row(
            modifier = Modifier
                .height(85.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box {
                    GlideImage(
                        model = if(imageUrl.startsWith("http")) imageUrl else "http:" + imageUrl,
                        loading = placeholder(R.drawable.light_grey_shape),
                        contentDescription = "Image", modifier = Modifier
                            .clip(
                                RoundedCornerShape(8.dp)
                            )
                            .size(80.dp), contentScale = ContentScale.Crop
                    )
                    if (isCanceled) {
                        Box(
                            Modifier
                                .size(80.dp)
                                .clip(
                                    RoundedCornerShape(8.dp)
                                )
                                .background(CheckoutAppBarColor),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Cancelled",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily(Font(R.font.font_med)),
                                    fontWeight = FontWeight(700),
                                    color = Color(0xFFFFFFFF),
                                )
                            )
                        }
                    }
                }

//                Image(painter = painter, contentDescription = "",
//                    modifier = Modifier
//                        .clip(
//                            RoundedCornerShape(8.dp)
//                        )
//                        .size(80.dp), contentScale = ContentScale.Crop)
                Spacer(modifier = Modifier.size(10.dp))
                Column() {
                    Row(modifier = Modifier.fillMaxWidth(.6f)) {
                        val continueIcon = painterResource(id = R.drawable.continue_icon)
                        Text(
                            text = name,
                            //item.name.split("\\s+".toRegex())[0] + " " + item.name.split("\\s+".toRegex())[1] + "...",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight(700),
                                color = textColor,
                                textDecoration = TextDecoration.Underline
                            ),
                        )
                        if (!isCanceled) {
                            Image(
                                modifier = Modifier.size(21.dp),
                                painter = continueIcon,
                                contentDescription = "continue",
                                contentScale = ContentScale.None
                            )
                        }

//                        Text(
//                            text = "...",
//                            maxLines = 1,
//                            style = TextStyle(
//                                fontSize = 16.sp,
//                                fontWeight = FontWeight(700),
//                                color = checkoutLightText,
//                                textAlign = TextAlign.Start
//                            ),

//                        )
                    }

                    Text(
                        text = "SKU: ${sku}",
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight(400),
                            color = textColor,
                        ),
                        modifier = Modifier.fillMaxWidth(0.6f)

                    )
                    Text(
                        text = "Size: ${size}",
                        maxLines = 1,
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight(400),
                            color = textColor,
                        )
                    )
                    Text(
                        text = "Quantity: ${formatNumber(quantity.toString())}",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight(400),
                            color = textColor,
                        )
                    )
                }


            }
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End,
                modifier = Modifier.fillMaxHeight()
            ) {
                val garbageImage = painterResource(id = R.drawable.garbage_icon)
                val continueIcon = painterResource(id = R.drawable.continue_icon)
                if (externalStatus == "processing") {
                    Image(
                        painter = if (isCanceled) continueIcon else garbageImage,
                        contentDescription = "delete item ",
                        Modifier
                            .clip(
                                RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                if (isCanceled) {
                                    onItemClick()
                                } else {
                                    onCancelItem()
                                    changeCancellationTo(true)
                                }

                            }
                            .background(CheckoutAppBarColor)
                            .padding(5.dp)
                            .size(20.dp)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .padding(5.dp)
                            .size(20.dp)
                    )
                }
                Row {

                    Text(
                        text = "IQD",
                        fontSize = 11.sp,
                        fontWeight = FontWeight(700),
                        color = textColor,
                    )
                    Text(
                        text = formatNumber(iqdPrice.toString()),
                        fontSize = 16.sp,
                        fontWeight = FontWeight(700),
                        color = textColor,
                    )
                }

            }
        }
        Spacer(modifier = Modifier.size(PADDING))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.LightGray)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ExpandableBottomBar(
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    numberOfItems: Int = 5,
    totalInsight: Double = 20.75,
    shipping: Double = 5.75,
    grandTotal: Double = 732.235,
    discountsList: List<DiscountModel> = listOf(),
    onExpandChange: (() -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier
            .dropShadow(
                color = ShadowGrey,
                blurRadius = 5.dp,
                borderRadius = 35.dp
            )
            .clip(RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp))
            .background(CheckoutAppBarColor)

            .fillMaxWidth()
            .padding(
                start = PADDING,
                top = if (expanded) 20.dp else 10.dp,
                bottom = if (expanded) 20.dp else 10.dp,
                end = PADDING
            )
    ) {
        AnimatedVisibility(visible = expanded) {
            Column {
                Text(
                    text = "Order Details",
                    style = TextStyle(
                        fontSize = 23.sp,
                        fontWeight = FontWeight(700),
                        color = checkoutLightText,
                    )
                )

                Column {
                    DetailsItem("Number Of Items", numberOfItems.toString())
                    DetailsItem("Total", totalInsight.toString(), showPrice = true)
                    DetailsItem("Shipping", shipping.toString(), showPrice = true)
                    discountsList.forEach {
                        DetailsItem(
                            it.description.en,
                            it.value.toString(),
                            showPrice = true,
                            isDiscount = true
                        )
                    }
                }
                Spacer(modifier = Modifier.size(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.LightGray)
                )
                Text(
                    text = "Does not include delivery fee",
                    fontSize = 12.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF8E9093),
                    modifier = Modifier.align(Alignment.End)
                )
            }


        }
        Spacer(
            modifier = Modifier.size(
                if (expanded) {
                    20.dp
                } else {
                    0.dp
                }
            )
        )

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.arrow_animation))
                LottieAnimation(composition = composition,
                    modifier = Modifier
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            onExpandChange?.invoke()
//                            viewModel.expanded = !viewModel.expanded
                        }
                        .width(24.dp)
                        .height(40.dp)
                        .rotate(if (expanded) 180f else 0f),
                    iterations = LottieConstants.IterateForever)
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = "Grand total",
                        fontSize = 16.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF2D2D2D),
                    )
                    Row {
                        Text(
                            text = "IQD",
                            fontSize = 15.sp,
                            fontWeight = FontWeight(700),
                            color = Color(0xFF2D2D2D),
                        )
                        Text(
                            text = formatNumber(grandTotal.toString()),
                            fontSize = 20.sp,
                            fontWeight = FontWeight(700),
                            color = Color(0xFF2D2D2D),
                        )
                    }


                }

            }
//            BottomButton(text = "Proceed", modifier = Modifier.clickable(
//                interactionSource = interactionSource,
//                indication = null
//            ) {
//                viewModel.saveCartData()
//                navController.navigate(HomeScreens.DeliveryAndPaymentScreen.route)
//            })

        }


    }

}

@Preview(showBackground = true)
@Composable
fun CancelOrderDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    text: String = "Are you sure you want to cancel this item from your order?",
    onCancelOrderPressOrItem: () -> Unit = {}
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = modifier
                .graphicsLayer {
                    this.shadowElevation = 10f
                    this.shape = RoundedCornerShape(16.dp)
                },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )


        ) {
            Text(
                text = text,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.font)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF4A4A4A),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(start = PADDING, end = PADDING, top = PADDING)

            )
            Spacer(modifier = Modifier.size(20.dp))
            Row {
                SaveButton(
                    Modifier
                        .weight(1f)
                        .padding(start = PADDING, end = 8.dp, bottom = PADDING),
                    text = "Yes"
                ) {
                    onCancelOrderPressOrItem.invoke()
                }
                SaveButton(
                    Modifier
                        .weight(1f)
                        .padding(start = 8.dp, end = PADDING, bottom = PADDING),
                    text = "No"
                ) {
                    onDismiss.invoke()
                }

            }

        }
    }
}

@Composable
fun SaveButton(modifier: Modifier = Modifier, text: String = "", onClick: () -> Unit) {
    Box(
        modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Yellow)
            .fillMaxWidth()
            .height(39.dp)

            .clickable {
                onClick()
            }, contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(1000),
                color = Color(0xFF2D2D2D),
            )
        )
    }

}