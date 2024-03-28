package com.simma.simmaapp.presentation.accountPage

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.simma.simmaapp.R
import com.simma.simmaapp.presentation.helpCenterScreen.openFacebookLink
import com.simma.simmaapp.presentation.helpCenterScreen.openInstagramLink
import com.simma.simmaapp.presentation.helpCenterScreen.openLinkInBrowser
import com.simma.simmaapp.presentation.homePage.HomeActivity
import com.simma.simmaapp.presentation.homePage.ui.theme.BackgroundLightGrey
import com.simma.simmaapp.presentation.homePage.ui.theme.Yellow
import com.simma.simmaapp.presentation.homeScreen.HomeScreens
import com.simma.simmaapp.presentation.loginScreen.LoginActivity
import com.simma.simmaapp.presentation.theme.Dimen.PADDING
import com.simma.simmaapp.presentation.theme.MedDarkGrey
import com.simma.simmaapp.utils.Helpers.clearUserData
import com.simma.simmaapp.utils.Helpers.isLoggedIn

@Preview(showBackground = true)
@Composable
fun AccountScreen(navController: NavController? = null) {
    var showDialog by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(PADDING)
        ) {
            Spacer(modifier = Modifier.size(12.dp))
            SettingsItem() {
                navController?.navigate(HomeScreens.ProfileScreen.route)
            }
            Spacer(modifier = Modifier.size(22.dp))
            SettingsItem(
                icon = R.drawable.unselected_wallet_icon,
                title = "Simma Wallet"
            ) {
                navController?.navigate(HomeScreens.WalletScreen.route)
            }
            Spacer(modifier = Modifier.size(22.dp))
            SettingsItem(
                icon = R.drawable.my_orders_icon,
                title = "My Orders"
            ) {
                // on click
                navController?.navigate(
                    HomeScreens.MyOrdersScreen.withArgs(
                        "true"
                    )
                )
            }
            Spacer(modifier = Modifier.size(22.dp))
            SettingsItem(
                icon = R.drawable.refer_and_earn_icon,
                title = "Refer and Earn",
                isNew = true
            ) {
                navController?.navigate(HomeScreens.ReferAndEarnScreen.route)
            }
            Spacer(modifier = Modifier.size(22.dp))
            SettingsItem(
                icon = R.drawable.change_language,
                title = "Language"
            ) {
                showDialog = true
            }

            if ((ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED)
            ) {
                Spacer(modifier = Modifier.size(22.dp))
                SettingsItem(
                    icon = R.drawable.enable_notifications_icon,
                    title = "Enable Notifications"
                ) {
                    requestNotificationPermission((context as HomeActivity))
                }
            }

            Spacer(modifier = Modifier.size(30.dp))
            Text(
                text = "You need any help?",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.font_med)),
                    fontWeight = FontWeight(1000),
                    color = MedDarkGrey,
                )
            )
            Spacer(modifier = Modifier.size(23.dp))
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

                Row(Modifier.clickable {
                    if (isLoggedIn(context)) {
                        clearUserData(context = context)
                        context.startActivity(Intent(context, LoginActivity::class.java))
                        (context as HomeActivity).finish()
                    } else {
                        clearUserData(context = context)
                        context.startActivity(Intent(context, LoginActivity::class.java))
                        (context as HomeActivity).finish()
                    }
                }) {

                    Image(
                        painter = painterResource(id = R.drawable.logout_icon),
                        contentDescription = if (isLoggedIn(context)) "Logout" else "Login",
                        modifier = Modifier
                            .size(20.dp)
                            .padding(1.dp)
                    )
                    Spacer(modifier = Modifier.size(3.dp))
                    Text(
                        text = if (isLoggedIn(context)) "Log Out" else "Log in",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontFamily = FontFamily(Font(R.font.font)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFF4A4A4A),
                            textDecoration = TextDecoration.Underline,
                        ),
                        modifier = Modifier.padding(end = 10.dp)
                    )
                }


            }
            Spacer(modifier = Modifier.size(17.dp))
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Row() {
                    Image(
                        painter = painterResource(id = R.drawable.facebook_item),
                        contentDescription = "Facebook",
                        Modifier
                            .width(20.3823.dp)
                            .height(22.99294.dp).clickable {
                                openFacebookLink(context,"https://www.facebook.com/Simma.io/")
                            }
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Image(
                        painter = painterResource(id = R.drawable.instagrame_icon),
                        contentDescription = "Instagram",
                        Modifier
                            .width(20.3823.dp)
                            .height(22.99294.dp)
                            .clickable {
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
                }
            }

        }

        if (showDialog) {
            DoneDialog() {
                showDialog = false
            }
        }


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

@Preview(showBackground = true)
@Composable
fun DoneDialog(onDismiss: (() -> Unit)? = null) {
    var selected by remember {
        mutableStateOf("en")
    }
    Dialog(onDismissRequest = { onDismiss?.invoke() }) {
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
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        selected = "en"
                    }
                    .padding(PADDING), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = "English",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.font_med)),
                            fontWeight = FontWeight(
                                if (selected == "en") 700 else 300
                            ),
                            color = Color(0xFF4A4A4A),
                        )
                    )
                    if (selected == "en")
                        Image(painterResource(id = R.drawable.check), contentDescription = "")

                }
                Box(
                    modifier = Modifier
                        .alpha(0.2f)
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(MedDarkGrey)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selected = "ar"
                        }
                        .padding(PADDING),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "العربية",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.font_med)),
                            fontWeight = FontWeight(
                                if (selected == "ar") 700 else 300
                            ),
                            color = Color(0xFF4A4A4A),
                        )
                    )
                    if (selected == "ar")
                        Image(painterResource(id = R.drawable.check), contentDescription = "")

                }
            }
            SaveButton(
                Modifier
                    .fillMaxWidth()
                    .padding(start = PADDING, end = PADDING, bottom = 16.dp),
                "Done"
            ) {
                onDismiss?.invoke()
                // todo change language
            }
        }
    }
}


@Composable
fun SaveButton(modifier: Modifier = Modifier, text: String = "Save", onClick: () -> Unit) {
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
fun requestNotificationPermission(activity: Activity) {
    val intent = Intent()
    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    intent.data = Uri.fromParts("package", activity.packageName, null)
    activity.startActivity(intent)
    Toast.makeText(activity, "Please enable notifications for this app", Toast.LENGTH_LONG).show()
}