package com.simma.simmaapp.presentation.cartPage

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
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
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
import com.simma.simmaapp.model.cartResponseModel.Item
import com.simma.simmaapp.presentation.homePage.HomeActivity
import com.simma.simmaapp.presentation.homePage.ui.theme.Yellow
import com.simma.simmaapp.presentation.homeScreen.HomeScreens
import com.simma.simmaapp.presentation.theme.AppBarGreyColor
import com.simma.simmaapp.presentation.theme.BorderColor
import com.simma.simmaapp.presentation.theme.CheckoutAppBarColor
import com.simma.simmaapp.presentation.theme.Dimen.PADDING
import com.simma.simmaapp.presentation.theme.ErrorColor
import com.simma.simmaapp.presentation.theme.ShadowGrey
import com.simma.simmaapp.presentation.theme.checkoutLightText
import com.simma.simmaapp.utils.Constants.APPLY_DISCOUNT
import com.simma.simmaapp.utils.Constants.CART_URL
import com.simma.simmaapp.utils.Constants.DISCOUNT_CODE
import com.simma.simmaapp.utils.Constants.MERCHANT_ID
import com.simma.simmaapp.utils.Constants.selectedUrl
import com.simma.simmaapp.utils.ModifierUtil.dropShadow
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Preview(showBackground = true)
@Composable
fun CartScreen(
    navController: NavController? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    ((LocalContext.current) as HomeActivity).window.apply {
        navigationBarColor = ContextCompat.getColor(context, R.color.statusAndNavigation)
        statusBarColor = ContextCompat.getColor(context, R.color.statusAndNavigation)
    }
    val viewModel: CartViewModel = hiltViewModel()
    Box(Modifier.fillMaxSize()) {
        Column() {
            AppBar(navController!!)
            LazyColumn(modifier = Modifier.padding(bottom = 60.dp)) {
                items(viewModel.listState.size) {
                    CartItems(item = viewModel.listState[it])
                }
            }
        }
        AnimatedVisibility(
            visible = viewModel.expanded,
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
                        viewModel.expanded = false
                    })
        }
        ExpandableBottomBar(Modifier.align(Alignment.BottomCenter), viewModel, navController!!)
    }

}

@Composable
fun AppBar(navController: NavController) {
    ((LocalContext.current) as HomeActivity).window.apply {
        setNavigationBarColor(ContextCompat.getColor(context, R.color.statusAndNavigation))
        statusBarColor = ContextCompat.getColor(context, R.color.statusAndNavigation)
    }
    val interactionSource = remember { MutableInteractionSource() }
    val painter = painterResource(id = R.drawable.shop_icon)
    Row(
        Modifier
            .fillMaxWidth()
            .background(CheckoutAppBarColor)
            .height(80.dp)
            .padding(start = PADDING, end = PADDING),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable(
            interactionSource = interactionSource,
            indication = null
        ) {
            navController.popBackStack()
            navController.popBackStack()
            navController.popBackStack()
            navController.navigate(HomeScreens.HomeScreen.route)
        }) {
            Image(painter = painter, contentDescription = "Home", modifier = Modifier.size(25.dp))
            Text(
                text = "Home",

                fontSize = 13.sp,
                fontWeight = FontWeight(400),
                color = AppBarGreyColor,
            )
        }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .border(
                    width = 1.dp, color = AppBarGreyColor,
                    RoundedCornerShape(8.dp)
                )
                .padding(start = 14.dp, end = 14.dp, top = 7.dp, bottom = 7.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    val url = URLEncoder.encode(selectedUrl, StandardCharsets.UTF_8.toString())
                    val cartUrl =
                        URLEncoder.encode(CART_URL, StandardCharsets.UTF_8.toString())
                    navController.popBackStack()
                    navController.popBackStack()
                    navController.navigate(
                        HomeScreens.WebViewScreen.withArgs(
                            url,
                            "window.CartVue.carts",
                            cartUrl,
                            MERCHANT_ID
                        )
                    )

                }
        ) {
            Text(
                text = "Continue shopping",
                fontSize = 12.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight(400),
                color = Color.DarkGray,
                letterSpacing = 0.5.sp,
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CartItems(modifier: Modifier = Modifier, item: Item) {
    val painter = painterResource(id = R.drawable.product_img)
    val editPainter = painterResource(id = R.drawable.edit_icon)
    Column(modifier = Modifier.padding(start = PADDING, end = PADDING, top = PADDING)) {
        Row(
            modifier = Modifier
                .height(85.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                GlideImage(
                    model = "http:" + item.imageUrl,
                    loading = placeholder(R.drawable.light_grey_shape),
                    contentDescription = "Image", modifier = Modifier
                        .clip(
                            RoundedCornerShape(8.dp)
                        )
                        .size(80.dp), contentScale = ContentScale.Crop
                )
//                Image(painter = painter, contentDescription = "",
//                    modifier = Modifier
//                        .clip(
//                            RoundedCornerShape(8.dp)
//                        )
//                        .size(80.dp), contentScale = ContentScale.Crop)
                Spacer(modifier = Modifier.size(10.dp))
                Column {
                    Row(modifier = Modifier.fillMaxWidth(.5f)) {
                        Text(
                            text = item.name.split("\\s+".toRegex())[0] + " " + item.name.split("\\s+".toRegex())[1] + "...",
                            maxLines = 1,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight(700),
                                color = checkoutLightText,

                                ),
                        )
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
                        text = "SKU: ${item.sku}",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight(400),
                            color = checkoutLightText,
                        )

                    )
                    Text(
                        text = "Size: ${item.size}",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight(400),
                            color = checkoutLightText,
                        )
                    )
                    Text(
                        text = "Quantity: ${item.quantity}",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight(400),
                            color = checkoutLightText,
                        )
                    )
                }


            }
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End,
                modifier = Modifier.fillMaxHeight()
            ) {
                Image(
                    painter = editPainter, contentDescription = "edit",
                    Modifier
                        .clip(
                            RoundedCornerShape(8.dp)
                        )
                        .background(CheckoutAppBarColor)
                        .padding(5.dp)
                        .size(20.dp)
                )
                Row {

                    Text(
                        text = "IQD",
                        fontSize = 11.sp,
                        fontWeight = FontWeight(700),
                        color = checkoutLightText,
                    )
                    Text(
                        text = item.iqdPrice.toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight(700),
                        color = checkoutLightText,
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

@Composable
fun ExpandableBottomBar(
    modifier: Modifier = Modifier,
    viewModel: CartViewModel,
    navController: NavController
) {
    val interactionSource = remember { MutableInteractionSource() }
    val arrowBottomPainter = painterResource(id = R.drawable.arrow_bottom)
    val arrowTopPainter = painterResource(id = R.drawable.arrow_up)
    var discountText by rememberSaveable {
        mutableStateOf("")
    }
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
                top = if (viewModel.expanded) 20.dp else 10.dp,
                bottom = if (viewModel.expanded) 20.dp else 10.dp,
                end = PADDING
            )
    ) {
        AnimatedVisibility(visible = viewModel.expanded) {
            Column {
                Text(
                    text = "Shipping details",
                    style = TextStyle(
                        fontSize = 23.sp,
                        fontWeight = FontWeight(700),
                        color = checkoutLightText,
                    )
                )

                Column {
                    DetailsItem("Number Of Items", viewModel.numberOfItems)
                    DetailsItem("Total", viewModel.totalInsight, showPrice = true)
                    DetailsItem("Shipping", viewModel.shipping, showPrice = true)
                    viewModel.discountsList.forEach {
                        DetailsItem(it.description.en,it.iqdDiscountAmount.toString(),showPrice = true, isDiscount = true)
                    }
                    if (viewModel.discountCodeApply) {
                        DetailsItem(viewModel.discountCodeName, viewModel.totalDiscountCodeAmount,showPrice = true, isDiscount = true)
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
                        }, hint = "Do you have a discount code?",
                        text = viewModel.discountText
                    )
                    Box(
                        modifier = Modifier

                            .weight(1f) // Takes 1 out of 4 parts
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                if(!APPLY_DISCOUNT.value){
                                    viewModel.applyDiscount()
                                }else{
                                    APPLY_DISCOUNT.value = false
                                }
                            }
                            .background(viewModel.applyColor)
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
                if (viewModel.discountError) {
                    Spacer(modifier = Modifier.size(10.dp))
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
                Spacer(modifier = Modifier.size(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(LightGray)
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
                if (viewModel.expanded) {
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
                            viewModel.expanded = !viewModel.expanded
                        }
                        .width(24.dp)
                        .height(40.dp)
                        .rotate(if (viewModel.expanded) 180f else 0f),
                    iterations = LottieConstants.IterateForever)
                Column {
                    Text(
                        text = "Grand total",
                        fontSize = 12.sp,
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
                            text = viewModel.grandTotal,
                            fontSize = 20.sp,
                            fontWeight = FontWeight(700),
                            color = Color(0xFF2D2D2D),
                        )
                    }


                }

            }
            BottomButton(text = "Proceed", modifier = Modifier.clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                viewModel.saveCartData()
                navController.navigate(HomeScreens.DeliveryAndPaymentScreen.route)
            })

        }


    }

}

@Preview(showBackground = true)
@Composable
fun BottomButton(modifier: Modifier = Modifier, text: String = "View Cart") {
    val painter = painterResource(id = R.drawable.simma_logo)
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


@Preview(showBackground = true)
@Composable
fun DetailsItem(
    title: String = "number of Items",
    value: String = "5",
    showPrice: Boolean = false,
    isDiscount : Boolean = false
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
                color = if(isDiscount) ErrorColor else checkoutLightText
                ,
            )
        )
        Row {
            if (showPrice) {
                Text(
                    text = "IQD",
                    style = TextStyle(
                        fontSize = 11.sp,
                        fontWeight = FontWeight(400),
                        color = if(isDiscount)ErrorColor  else checkoutLightText,
                    )
                )
            }
            Text(
                text = value,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(400),
                    color = if(isDiscount)ErrorColor  else checkoutLightText,
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
    text: String
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
        textStyle = TextStyle( textDirection = TextDirection.Content),
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
                    style = TextStyle(textAlign = TextAlign.Start) // Align the placeholder text to the start
                )
            },
            container = {
                OutlinedTextFieldDefaults.ContainerBox(
                    enabled = true,
                    isError = false,
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