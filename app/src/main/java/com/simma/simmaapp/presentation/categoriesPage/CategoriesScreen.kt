package com.simma.simmaapp.presentation.categoriesPage

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simma.simmaapp.R
import com.simma.simmaapp.presentation.homePage.ui.theme.Yellow
import com.simma.simmaapp.presentation.homeScreen.HomeScreens
import com.simma.simmaapp.presentation.theme.CheckoutAppBarColor
import com.simma.simmaapp.presentation.theme.MedDarkGrey

@Preview(showBackground = true)
@Composable
fun CategoryScreen(navController: NavController? = null) {
    Column(Modifier.fillMaxSize()) {
        AppBar()
        LazyColumn {
            items(7) {
                StoreItem(Modifier.clickable {
                    navController?.navigate(HomeScreens.WebViewScreen.route)
                })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppBar() {
    val painter = painterResource(id = R.drawable.back)
    Row(
        Modifier
            .fillMaxWidth()
            .background(CheckoutAppBarColor)
            .height(120.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Box {
                Icon(
                    painter = painter,
                    contentDescription = "Home",
                    modifier = Modifier
                        .padding(start = 30.dp)
                        .size(20.dp),
                    tint = MedDarkGrey
                )

            }
            Spacer(modifier = Modifier.size(20.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(6),
                Modifier.align(Alignment.CenterHorizontally)
            ) {
                items(6) {
                    if (it == 0) {
                        CategoriesItem(Modifier.clickable {

                        }, selected = true)
                    } else {
                        CategoriesItem(Modifier.clickable {

                        }, selected = false)
                    }
                }
            }
        }
    }


}

@Composable
fun CategoriesItem(
    modifier: Modifier,
    painter: Painter = painterResource(id = R.drawable.apperal),
    text: String = "Apparel",
    selected: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(start = 10.dp, end = 10.dp)
    ) {
        Icon(
            painter = painter,
            contentDescription = text,
            modifier = Modifier.size(28.dp),
            tint = if (selected) Yellow else MedDarkGrey
        )
        Text(
            text = text,
            fontSize = 8.sp,
            fontWeight = FontWeight(590),
            color = Color(0xFF4A4A4A),
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.size(5.dp))
        if (selected) {
            Box(
                Modifier
                    .width(35.dp)
                    .height(3.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Yellow)
            ) {

            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun StoreItem(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp, top = 16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.shine), contentDescription = "shine",
            Modifier
                .size(75.dp)
                .border(
                    width = 2.dp,
                    Color.LightGray, RoundedCornerShape(12.dp)
                )
                .padding(10.dp)
        )
        Spacer(modifier = Modifier.size(20.dp))
        Text(
            text = "Shein",
            fontSize = 20.sp,
            fontWeight = FontWeight(700),
            color = Color(0xFF4A4A4A),
        )
    }
}