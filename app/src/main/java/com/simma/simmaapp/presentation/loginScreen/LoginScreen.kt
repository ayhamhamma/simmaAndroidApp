package com.simma.simmaapp.presentation.loginScreen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.simma.simmaapp.R
import com.simma.simmaapp.presentation.homePage.HomeActivity
import com.simma.simmaapp.presentation.homePage.ui.theme.Yellow
import com.simma.simmaapp.presentation.registrationView.OutlinedTextField
import com.simma.simmaapp.presentation.theme.BorderColor
import com.simma.simmaapp.presentation.theme.CheckoutAppBarColor
import com.simma.simmaapp.presentation.theme.MedDarkGrey
import com.simma.simmaapp.presentation.theme.checkoutLightText
@Preview(showBackground = true)
@Composable
fun LoginScreen(navController: NavController? = null) {
    ((LocalContext.current) as LoginActivity).window.apply {
        navigationBarColor = ContextCompat.getColor(context, R.color.statusAndNavigation)
        statusBarColor = ContextCompat.getColor(context, R.color.statusAndNavigation)
    }
    val context = LocalContext.current
    var phoneNumberText by rememberSaveable {
        mutableStateOf("")
    }
    var passwordText by rememberSaveable {
        mutableStateOf("")
    }
    LazyColumn(
        Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp, bottom = 30.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        item {
        Column(
            Modifier
                .fillMaxWidth()
        ) {
            AppBar()
            Spacer(modifier = Modifier.size(35.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(362f / 278f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(CheckoutAppBarColor)
            ) {}
            Spacer(modifier = Modifier.size(30.dp))
            Text(
                text = "Phone number",
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.font)),
                fontWeight = FontWeight(700),
                color = MedDarkGrey,
            )
            Spacer(modifier = Modifier.size(8.dp))
            Row {
                Box(
                    modifier = Modifier
                        .height(48.dp)
                        .weight(1f)
                        .border(
                            1.dp, BorderColor,
                            RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "+964",
                        fontSize = 20.sp,
                        fontWeight = FontWeight(400),
                        textAlign = TextAlign.Center,
                        color = MedDarkGrey,
                    )
                }

                Spacer(modifier = Modifier.size(12.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .height(48.dp)
                        .weight(3f), onTextChange = {
                        phoneNumberText = it
                    },
                    text = phoneNumberText
                )
            }
            Spacer(modifier = Modifier.size(13.dp))
            Text(
                text = "Password",
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.font)),
                fontWeight = FontWeight(700),
                color = Color(0xFF4A4A4A),
            )
            Spacer(modifier = Modifier.size(8.dp))
            OutlinedTextField(
                modifier = Modifier
                    .height(48.dp)

                    .fillMaxWidth(), onTextChange = {
                    passwordText = it
                },
                text = passwordText
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            val interactionSource = remember { MutableInteractionSource() }
            Box(
                Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Yellow)
                    .fillMaxWidth()
                    .height(56.dp)
                    .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                        context.startActivity(Intent(context, HomeActivity::class.java))
                    }, contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Continue",
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(700),
                        fontFamily = FontFamily(Font(R.font.font)),
                        color = Color(0xFF2D2D2D),
                    )
                )
            }
            Row {
                Text(
                    text = "Don’t have an account? ",
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    fontFamily = FontFamily(Font(R.font.font)),
                    color = Color(0xFF4A4A4A),
                )
                Text(
                    text = "Sign up",
                    fontSize = 14.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFF4A4A4A),
                    fontFamily = FontFamily(Font(R.font.font)),
                    modifier = Modifier.clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                        navController?.navigate(Screen.RegistrationScreen.route)
                    }
                )
            }
        }


    }
    }
}

@Preview(showBackground = true)
@Composable
fun AppBar() {
    val painter = painterResource(id = R.drawable.simma_login_logo)
    Row(
        Modifier
            .fillMaxWidth()
            .height(80.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painter, contentDescription = "", Modifier.size(74.dp))

        Text(
            text = "العربية",
            fontSize = 20.sp,
            fontWeight = FontWeight(400),
            color = checkoutLightText,
            fontFamily = FontFamily(Font(R.font.font)),
            modifier = Modifier
                .clip(
                    RoundedCornerShape(12.dp)
                )
                .background(CheckoutAppBarColor)
                .padding(start = 25.dp, end = 25.dp, top = 12.dp, bottom = 12.dp)
        )
    }
}

