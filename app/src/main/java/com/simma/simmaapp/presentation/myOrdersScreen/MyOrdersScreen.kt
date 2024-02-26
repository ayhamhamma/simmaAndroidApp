package com.simma.simmaapp.presentation.myOrdersScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
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
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.simma.simmaapp.R
import com.simma.simmaapp.model.myOrdersModel.MyOrdersResultData
import com.simma.simmaapp.presentation.homePage.HomeActivity
import com.simma.simmaapp.presentation.theme.CanceledColor
import com.simma.simmaapp.presentation.theme.CheckoutAppBarColor
import com.simma.simmaapp.presentation.theme.ConfirmedColor
import com.simma.simmaapp.presentation.theme.DeliveredColor
import com.simma.simmaapp.presentation.theme.Dimen.PADDING
import com.simma.simmaapp.presentation.theme.MedDarkGrey
import com.simma.simmaapp.presentation.theme.OnHoldColor
import com.simma.simmaapp.presentation.theme.OutForDeliveryColor
import com.simma.simmaapp.presentation.theme.PendingPaymentColor
import com.simma.simmaapp.presentation.theme.ProcessingColor
import com.simma.simmaapp.presentation.theme.checkoutLightText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun MyOrdersScreen(fromHome : Boolean = false) {
    val viewModel: MyOrdersViewModel = hiltViewModel()
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    var itemCount by remember { mutableStateOf(15) }

    fun refresh() = refreshScope.launch {
        refreshing = true
        delay(1500)
        viewModel.getLastOrders()
        itemCount += 5
        refreshing = false
    }
    val state = rememberPullRefreshState(refreshing, ::refresh)

    var showDialog by remember {
        mutableStateOf(true)
    }
    Box(Modifier.pullRefresh(state)) {
        Column(
            Modifier
                .fillMaxSize()
                .background(White)
                .padding(start = PADDING, end = PADDING)
        ) {
            AppBar()
            LazyColumn {
                items(viewModel.state.items.size) {
                    MyOrderItem(order = viewModel.state.items[it])
                }
            }
        }
        if (showDialog && !fromHome) {
            DoneDialog {
                showDialog = false
            }
        }
        PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
    }

}


@Preview(showBackground = true)
@Composable
fun AppBar() {
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
                    .clickable {
                        (context as HomeActivity).onBackPressedDispatcher.onBackPressed()
                    },
                tint = MedDarkGrey
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Preview(showBackground = true)
@Composable
fun MyOrderItem(
    painter: Painter = painterResource(id = R.drawable.simma_logo),
    price: String = "Simma Wallet",
    paymentMethod: Int = 2,
    modifier: Modifier = Modifier,
    freeDelivery: Boolean = true,
    order: MyOrdersResultData? = null
) {
    val context = LocalContext.current
    val color by remember {
        mutableStateOf(
            when (order?.externalStatus) {
                "processing" -> {
                    ProcessingColor
                }

                "confirmed" -> {
                    ConfirmedColor
                }

                "onHold" -> {
                    OnHoldColor
                }

                "outForDelivery" -> {
                    OutForDeliveryColor
                }

                "delivered" -> {
                    DeliveredColor
                }

                "cancelled" -> {
                    CanceledColor
                }

                "paymentRejected" -> {
                    CanceledColor
                }

                "pendingPayment" -> {
                    PendingPaymentColor
                }

                else -> {
                    ProcessingColor
                }
            }
        )
    }

    /***
     *  processing: 'قيد المعالجة',
     *   confirmed: 'تم تأكيد الطلب',
     *   delivered: 'تم التوصيل',
     *   cancelled: 'ملغي',
     *   outForDelivery: 'قيد التوصيل',
     *   paymentRejected: 'الدفعة مرفوضة',
     *   pendingPayment: 'بانتظار الدفع',
     */
    val status by remember {
        mutableStateOf(
            when (order?.externalStatus) {
                "processing" -> {
                    context.getString(R.string.processing)
                }

                "confirmed" -> {
                    context.getString(R.string.confirmed)
                }

                "onHold" -> {
                    context.getString(R.string.onHold)
                }

                "outForDelivery" -> {
                    context.getString(R.string.outForDelivery)
                }

                "delivered" -> {
                    context.getString(R.string.delivered)
                }

                "cancelled" -> {
                    context.getString(R.string.cancelled)
                }

                "paymentRejected" -> {
                    context.getString(R.string.paymentRejected)
                }

                "pendingPayment" -> {
                    context.getString(R.string.pendingPayment)
                }

                else -> {
//                    ProcessingColor
                    context.getString(R.string.processing)
                }
            }
        )
    }
    Column {
        Spacer(modifier = Modifier.size(PADDING))

        Box(
            modifier
                .fillMaxWidth()
                .background(Transparent),

            ) {

            GlideImage(
                model = order!!.merchant.image.originalUrl, contentDescription = "Order Merchant",
                modifier = Modifier
                    .size(68.dp)
                    .clip(
                        RoundedCornerShape(12.dp)
                    )
                    .border(
                        1.dp,
                        Color.LightGray,
                        RoundedCornerShape(12.dp)
                    )
                    .padding(15.dp)
                    .align(Alignment.CenterStart)
            )
            Spacer(modifier = Modifier.size(12.dp))
            Box(
                Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 80.dp)
            ) {
                Text(
                    text = "Grand total",
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontFamily = FontFamily(Font(R.font.font_med)),
                        fontWeight = FontWeight(400),
                        color = checkoutLightText,
                    )
                )
                Row(Modifier.padding(top = 14.dp)) {
                    Text(
                        text = "IQD",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.font_med)),
                            fontWeight = FontWeight(700),
                            color = checkoutLightText,
                        )
                    )
                    Text(
                        text = order!!.paymentDetails.IQDGrandTotal.toString(),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.font_med)),
                            fontWeight = FontWeight(700),
                            color = checkoutLightText,
                        )
                    )
                }
                Row(Modifier.padding(top = 30.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(11.dp)
                            .background(color)
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(
                        text = status,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.font)),
                            fontWeight = FontWeight(700),
                            color = checkoutLightText,
                        )
                    )
                }

            }
            Image(
                painter = painterResource(id = R.drawable.edit_icon), contentDescription = "edit",
                Modifier
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
                    .background(CheckoutAppBarColor)
                    .padding(5.dp)
                    .size(20.dp)
                    .align(Alignment.TopEnd)
            )
            Row(
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 8.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.calender),
                    contentDescription = "date",
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.size(3.dp))
                Text(
                    text = order.delivery.maximumDate,
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontFamily = FontFamily(Font(R.font.font_med)),
                        fontWeight = FontWeight(400),
                        color = checkoutLightText,
                    )
                )

            }


        }
        Spacer(modifier = Modifier.size(PADDING))
        Box(
            Modifier
                .fillMaxWidth()
                .alpha(0.5f)
                .height(1.dp)
                .background(Color.LightGray)
        ) {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DoneDialog(onDismiss: (() -> Unit)? = null) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.order_completed))
    Dialog(onDismissRequest = { onDismiss?.invoke() }) {
        Card(
            modifier = Modifier
                .graphicsLayer {
                    this.shadowElevation = 10f
                    this.shape = RoundedCornerShape(16.dp)
                },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = White
            )
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LottieAnimation(composition = composition, modifier = Modifier.height(124.dp))
                Text(
                    text = "Your order is placed successfully.\n our agents will call you\n to confirm details.",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(700),
                        fontFamily = FontFamily(Font(R.font.font_med)),
                        color = checkoutLightText,
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}