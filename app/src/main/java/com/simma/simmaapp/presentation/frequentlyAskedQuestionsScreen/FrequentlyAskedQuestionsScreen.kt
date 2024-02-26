package com.simma.simmaapp.presentation.frequentlyAskedQuestionsScreen

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.simma.simmaapp.R
import com.simma.simmaapp.presentation.homePage.HomeActivity
import com.simma.simmaapp.presentation.theme.CheckoutAppBarColor
import com.simma.simmaapp.presentation.theme.Dimen.PADDING
import com.simma.simmaapp.presentation.theme.MedDarkGrey

@Preview(showBackground = true)
@Composable
fun FrequentlyAskedQuestionsScreen() {

    val viewModel: FrequentlyAskedQuestionsViewModel = hiltViewModel()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = PADDING, end = PADDING)
    ) {
        AppBar()
        Spacer(modifier = Modifier.size(PADDING))
        Text(
            text = "Frequently Asked Questions! ",
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.font_med)),
                fontWeight = FontWeight(700),
                color = MedDarkGrey,
            )
        )
        Spacer(modifier = Modifier.size(20.dp))
        LazyColumn {
            items(viewModel.questionsList.size) {
                val data = viewModel.questionsList[it]
                FrequentlyAskedQuestionsScreenItem(
                    question = data.questionDesc.en,
                    answer = data.answer.en
                )
                Spacer(modifier = Modifier.size(8.dp))

            }
        }
    }

}

@Preview
@Composable
fun FrequentlyAskedQuestionsScreenItem(
    modifier: Modifier = Modifier,
    question: String = "How do I shop through Simma?",
    answer: String = "Shopping on Simma is easy, Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam"
) {
    var isExposed by remember {
        mutableStateOf(false)
    }
    Column(modifier = modifier) {
        Row(
            Modifier
                .fillMaxWidth()
                .heightIn(min = 40.dp)
                .background(color = CheckoutAppBarColor, shape = RoundedCornerShape(size = 4.dp))
                .clickable {
                    isExposed = !isExposed
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = question,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.font_med)),
                    fontWeight = FontWeight(700),
                    color = MedDarkGrey,
                ),
                modifier = Modifier
                    .weight(4f)
                    .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
            )
            Box (modifier = Modifier.weight(1f)){
                Image(
                    painter = painterResource(id = if (isExposed) R.drawable.open_drop_down else R.drawable.clased_drop_done),
                    contentDescription = "Open Help Options",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(8.dp)
                        .size(32.dp)
                )
            }
        }
        AnimatedVisibility(visible = isExposed) {
            Text(
                text = answer,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.font)),
                    fontWeight = FontWeight(400),
                    color = MedDarkGrey,
                ),
                modifier = Modifier
                    .padding(top = 8.dp)
                    .border(
                        width = 1.dp,
                        color = Color(0xFFAFAFAF),
                        shape = RoundedCornerShape(size = 8.dp)
                    )
                    .fillMaxWidth()
                    .background(
                        color = CheckoutAppBarColor,
                        shape = RoundedCornerShape(size = 8.dp)
                    )
                    .padding(PADDING)
            )
        }
    }
}

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
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        (context as HomeActivity).onBackPressedDispatcher.onBackPressed()
                    },
                tint = MedDarkGrey
            )
        }
    }
}
