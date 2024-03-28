package com.simma.simmaapp.presentation.loginScreen

import android.content.Intent
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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.simma.simmaapp.presentation.theme.CanceledOrderTextColor
import com.simma.simmaapp.presentation.theme.Dimen
import com.simma.simmaapp.presentation.theme.Dimen.PADDING
import com.simma.simmaapp.presentation.theme.OnBoardingBabyBlue
import com.simma.simmaapp.presentation.theme.SimmaBlue
import com.simma.simmaapp.presentation.theme.checkoutLightText

@Preview
@Composable
fun LoginSignupScreen(navController: NavController? =null) {
    val context = LocalContext.current
    LaunchedEffect(Unit){
        ((context) as LoginActivity).window.apply {
            navigationBarColor =
                ContextCompat.getColor(context, R.color.babyBlue1)
            statusBarColor =
                ContextCompat.getColor(context, R.color.babyBlue1)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar()
        }
    ) { paddingValues ->
        MainSignup(paddingValues, navController)
    }
}

@Preview
@Composable
fun TopAppBar() {
    val painter = painterResource(id = R.drawable.simma_logo1)
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = Dimen.PADDING, end = Dimen.PADDING, top = Dimen.PADDING),
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

@Composable
fun MainSignup(paddingValues: PaddingValues,navController:NavController ? = null) {
    val context = LocalContext.current
    Box {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .scale(2f)
                .clip(RoundedCornerShape(bottomStart = 350.dp, bottomEnd = 200.dp))
                .background(OnBoardingBabyBlue)
                .size(270.dp)
        )
        Column(Modifier.fillMaxSize(),verticalArrangement = Arrangement.SpaceAround) {
//            Spacer(modifier = Modifier.size(paddingValues.calculateTopPadding()))
            Image(
                modifier = Modifier.fillMaxWidth().aspectRatio(1f),
                painter = painterResource(id = R.drawable.login_image),
                contentDescription = "Login Image",
                contentScale = ContentScale.FillHeight
            )
            Text(
                text = "Embark on your new shopping journey. ",
                style = TextStyle(
                    fontSize = 30.sp,
                    fontFamily = FontFamily(Font(R.font.font_bold)),
                    fontWeight = FontWeight(1000),
                    color = SimmaBlue,
                    textAlign = TextAlign.Center,
                )
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = PADDING, end = PADDING)
            ) {
                Box(
                    Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            navController?.navigate(Screen.RegistrationScreen.route)
                        }
                        .background(Yellow)
                        .fillMaxWidth()
                        .height(56.dp), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Sign up",
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight(700),
                            fontFamily = FontFamily(Font(R.font.font)),
                            color = Color(0xFF2D2D2D),
                        )
                    )
                }
                Spacer(modifier = Modifier.size(13.dp))
                Box(
                    Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            context.startActivity(Intent(context, HomeActivity::class.java))
                        }
                        .border(
                            width = 1.dp,
                            color = CanceledOrderTextColor,
                            shape = RoundedCornerShape(size = 12.dp)
                        )
                        .height(56.dp)
                        .background(Color.White)
                        .fillMaxWidth()
                        .height(56.dp), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Browse Stores",
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight(700),
                            fontFamily = FontFamily(Font(R.font.font)),
                            color = checkoutLightText,
                        )
                    )
                }
                Spacer(modifier = Modifier.size(13.dp))
                Row {
                    val interactionSource = remember { MutableInteractionSource() }
                    Text(
                        text = "Already have an account? ",
                        fontSize = 14.sp,
                        fontWeight = FontWeight(400),
                        fontFamily = FontFamily(Font(R.font.font)),
                        color = Color(0xFF4A4A4A),
                    )
                    Text(
                        text = "Login",
                        fontSize = 14.sp,
                        fontWeight = FontWeight(1000),
                        color = Color(0xFF4A4A4A),
                        fontFamily = FontFamily(Font(R.font.font_med)),
                        modifier = Modifier.clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            navController?.navigate(Screen.LoginMethodScreen.route)
                        }
                    )
                }
            }
        }

    }
}