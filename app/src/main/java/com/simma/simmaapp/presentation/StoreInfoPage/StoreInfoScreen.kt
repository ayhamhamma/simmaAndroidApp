package com.simma.simmaapp.presentation.StoreInfoPage

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.simma.simmaapp.R
import com.simma.simmaapp.presentation.homePage.HomeActivity
import com.simma.simmaapp.presentation.theme.AlpaWHITE
import com.simma.simmaapp.presentation.theme.Dimen.PADDING
import com.simma.simmaapp.presentation.theme.LightGrey
import com.simma.simmaapp.presentation.theme.checkoutLightText
import com.simma.simmaapp.utils.Constants.DETAILS_COUNTRY
import com.simma.simmaapp.utils.Constants.DETAILS_COVER_IMAGE
import com.simma.simmaapp.utils.Constants.DETAILS_DEALS
import com.simma.simmaapp.utils.Constants.DETAILS_DESCRIPTION
import com.simma.simmaapp.utils.Constants.DETAILS_IMAGE
import com.simma.simmaapp.utils.Constants.DETAILS_ITEM_PRICED
import com.simma.simmaapp.utils.Constants.DETAILS_MAX_DAYS
import com.simma.simmaapp.utils.Constants.DETAILS_MIN_DAYS
import com.simma.simmaapp.utils.Constants.DETAILS_NAME

@Preview
@Composable
fun StoreInfoScreen() {
    var deals = ""
    DETAILS_DEALS.forEach {
        deals += (it + "\n")
    }
    Scaffold(topBar = { AppBar() }) { paddingValues ->
        Column(
            Modifier.padding(
                top = paddingValues.calculateTopPadding(),
                start = PADDING,
                end = PADDING
            )
        ) {
            Spacer(modifier = Modifier.size(19.dp))
            Text(
                text = DETAILS_NAME,
                style = TextStyle(
                    fontSize = 35.sp,
                    fontFamily = FontFamily(Font(R.font.font_bold)),
                    fontWeight = FontWeight(700),
                    color = checkoutLightText,
                )
            )
            Spacer(modifier = Modifier.size(19.dp))
            Text(
                text = DETAILS_DESCRIPTION,
                style = TextStyle(
                    fontSize = 17.sp,
                    fontFamily = FontFamily(Font(R.font.font_med)),
                    fontWeight = FontWeight(400),
                    color = checkoutLightText,
                )
            )
            Spacer(
                modifier = Modifier.size(30.dp)
            )
            Row(Modifier.fillMaxWidth()) {
                detailsItem(
                    title = "Country",
                    description = DETAILS_COUNTRY ?: "",
                    Modifier.weight(1f)
                )
                Spacer(
                    modifier = Modifier.size(5.dp)
                )
                detailsItem(
                    title = "Shipping time",
                    description = "$DETAILS_MIN_DAYS - $DETAILS_MAX_DAYS Days",
                    Modifier.weight(1.5f)
                )
                Spacer(
                    modifier = Modifier.size(5.dp)
                )
                detailsItem(
                    title = "Shipping price",
                    description = "\$$DETAILS_ITEM_PRICED a piece",
                    Modifier.weight(1.5f)
                )
            }
            Spacer(
                modifier = Modifier.size(7.dp)
            )
            if (!DETAILS_DEALS.isEmpty()) {
                detailsItem(
                    title = "Deals",
                    description = deals,
                    Modifier.fillMaxWidth(),
                    paddingStart = 12.dp
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AppBar() {
    val context = LocalContext.current
    val painter = painterResource(id = R.drawable.store_info_page_cover_image)
    val backPainter = painterResource(id = R.drawable.back_btn)
    Box(
        Modifier
            .fillMaxWidth()
            .height(240.dp)
    ) {
        GlideImage(
            model = if (DETAILS_COVER_IMAGE.startsWith("http")) DETAILS_COVER_IMAGE else "http:" + DETAILS_COVER_IMAGE,
            loading = placeholder(R.drawable.light_grey_shape),
            contentDescription = "Image",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Card(
            Modifier
                .padding(end = PADDING)
                .size(75.dp)
                .align(Alignment.CenterEnd), elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp,
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            GlideImage(
                model = if (DETAILS_IMAGE.startsWith("http")) DETAILS_IMAGE else "http:" + DETAILS_IMAGE,
                loading = placeholder(R.drawable.light_grey_shape),
                contentDescription = "Image",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            )
        }
        Box(modifier = Modifier
            .padding(start = PADDING)
            .size(40.dp)
            .align(Alignment.CenterStart)
            .clip(CircleShape)
            .clickable {
                (context as HomeActivity).onBackPressedDispatcher.onBackPressed()
            }
            .background(AlpaWHITE),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = backPainter, contentDescription = "back",
                Modifier
                    .size(40.dp)
                    .padding(5.dp)
            )
        }
    }
}

@Composable
fun detailsItem(
    title: String,
    description: String,
    modifier: Modifier,
    paddingStart: Dp = 0.dp
) {
    Box(
        modifier
            .border(width = 1.dp, color = LightGrey, shape = RoundedCornerShape(12.dp))
            .padding(5.dp, top = 10.dp, bottom = 13.dp, end = 5.dp)
    ) {
        Column {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.font)),
                    fontWeight = FontWeight(400),
                    color = LightGrey,
                )
            )
            Spacer(modifier = Modifier.size(5.dp))
            Text(
                text = description,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.font)),
                    fontWeight = FontWeight(700),
                    color = checkoutLightText,
                ),
                modifier = Modifier.padding(start = paddingStart)
            )
        }

    }
}