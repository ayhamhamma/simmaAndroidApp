package com.simma.simmaapp.presentation.helpCenterScreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import androidx.navigation.NavController
import com.simma.simmaapp.R
import com.simma.simmaapp.presentation.homePage.HomeActivity
import com.simma.simmaapp.presentation.homePage.ui.theme.BackgroundLightGrey
import com.simma.simmaapp.presentation.homePage.ui.theme.Yellow
import com.simma.simmaapp.presentation.homeScreen.HomeScreens
import com.simma.simmaapp.presentation.loginScreen.LoginActivity
import com.simma.simmaapp.presentation.theme.CheckoutAppBarColor
import com.simma.simmaapp.presentation.theme.Dimen
import com.simma.simmaapp.presentation.theme.Dimen.PADDING
import com.simma.simmaapp.presentation.theme.MedDarkGrey
import com.simma.simmaapp.presentation.theme.checkoutLightText
import com.simma.simmaapp.utils.Helpers

@Preview
@Composable
fun HelpCenterScreen(
    navController: NavController? = null
) {
    val context = LocalContext.current
    Scaffold(
        topBar ={
            AppBar()
        },
        containerColor = Color.White
    ) {
        paddingValues ->

        Column(modifier = Modifier.padding(top = paddingValues.calculateTopPadding(), start = PADDING,end = PADDING)) {
            Spacer(modifier = Modifier.size(22.dp))
            SettingsItem(
                icon = R.drawable.send_inquiry,
                title = "Send Inquiry"
            ) {
            navController?.navigate(HomeScreens.SendInquiryScreen.route)
            }
            Spacer(modifier = Modifier.size(22.dp))
            SettingsItem(
                icon = R.drawable.contect_us_icon,
                title = "Contact Us "
            ) {
                val phoneNumber = "+9647840385186"
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:$phoneNumber")
                }
                context.startActivity(intent)
            }
            Spacer(modifier = Modifier.size(22.dp))
            SettingsItem(
                icon = R.drawable.frequent_asked,
                title = "Frequently Asked Questions "
            ) {
            navController?.navigate(HomeScreens.FrequentlyAskedQuestions.route)
            }
            Spacer(modifier = Modifier.size(22.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(BackgroundLightGrey),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Simma 6.3.1",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.font)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF4A4A4A),
                    ),
                    modifier = Modifier.padding(11.dp)
                )

                Row() {
                    Image(
                        painter = painterResource(id = R.drawable.facebook_item),
                        contentDescription = "Facebook",
                        Modifier
                            .width(20.3823.dp)
                            .height(22.99294.dp)
                            .clickable {
                                openFacebookLink(context,"https://www.facebook.com/Simma.io/")
                        }
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Image(
                        painter = painterResource(id = R.drawable.instagrame_icon),
                        contentDescription = "Instagram",
                        Modifier
                            .width(20.3823.dp)
                            .height(22.99294.dp).clickable {
                                openInstagramLink(context,"https://www.instagram.com/simma.io/?hl=en")
                            }
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Image(
                        painter = painterResource(id = R.drawable.change_language),
                        contentDescription = "Our Website",
                        Modifier
                            .width(20.3823.dp)
                            .height(22.99294.dp)
                            .clickable {
                                openLinkInBrowser(context,"https://www.simma.io/")
                            }
                    )
                    Spacer(modifier = Modifier.size(11.dp))
                }

            }
        }

    }
}

@Preview
@Composable
fun AppBar() {
    val painter = painterResource(id = R.drawable.back)
    val context = LocalContext.current
    Box(
        Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(CheckoutAppBarColor)
            .padding(start = Dimen.PADDING, end = Dimen.PADDING)
    ) {
        Icon(
            painter = painter,
            contentDescription = "Back",
            modifier = Modifier
                .clickable {
                    try {
                        (context as HomeActivity).onBackPressedDispatcher.onBackPressed()
                    }catch (e:ClassCastException){
                        (context as LoginActivity).onBackPressedDispatcher.onBackPressed()
                    }
                }
                .size(20.dp)
                .align(Alignment.CenterStart),
            tint = MedDarkGrey
        )
        Text(
            text = "Help center",
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
}
@Composable
fun SettingsItem(
    @DrawableRes icon: Int = R.drawable.personal_info_icon,
    title: String = "Personal Information",
    isNew: Boolean = false,
    onClick: () -> Unit = {}
) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = icon), contentDescription = title,
                Modifier
                    .width(20.3823.dp)
                    .height(22.99294.dp)
            )
            Spacer(modifier = Modifier.size(11.dp))
            Text(
                text = title,
                color = MedDarkGrey,
                fontFamily = FontFamily(Font(R.font.font_med)), fontSize = 16.sp
            )
            if (isNew) {
                Spacer(modifier = Modifier.size(13.dp))
                Text(
                    text = "New",
                    color = MedDarkGrey,
                    fontFamily = FontFamily(Font(R.font.font_med)),
                    fontSize = 15.sp,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = Yellow)
                        .padding(start = 8.dp, end = 8.dp)

                )
            }
        }
        Image(
            painter = painterResource(id = R.drawable.continue_arrow),
            contentDescription = title,
            Modifier.size(22.dp)
        )

    }
}

fun openFacebookLink(context: Context, facebookUrl: String) {
    // Check if Facebook app is installed
    val facebookAppIntent = context.packageManager.getLaunchIntentForPackage("com.facebook.katana")
    if (facebookAppIntent != null) {
        // Facebook app is installed, open link in the app
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl))
        intent.`package` = "com.facebook.katana"
        context.startActivity(intent)
    } else {
        // Facebook app is not installed, open link in default browser
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl))
        context.startActivity(intent)
    }
}
fun openInstagramLink(context: Context, instagramUrl: String) {
    // Check if Instagram app is installed
    val instagramAppIntent = context.packageManager.getLaunchIntentForPackage("com.instagram.android")
    if (instagramAppIntent != null) {
        // Instagram app is installed, open link in the app
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl))
        intent.`package` = "com.instagram.android"
        context.startActivity(intent)
    } else {
        // Instagram app is not installed, open link in default browser
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl))
        context.startActivity(intent)
    }
}

fun openLinkInBrowser(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}