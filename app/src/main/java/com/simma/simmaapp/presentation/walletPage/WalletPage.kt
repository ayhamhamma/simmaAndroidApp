package com.simma.simmaapp.presentation.walletPage

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simma.simmaapp.R

@Preview
@Composable
fun WalletPage() {
    Scaffold (topBar = {
        AppBar()
    }){
        paddingValues ->
        paddingValues
        
    }
    
}

@Composable
fun AppBar() {
    val infoPainter = painterResource(id = R.drawable.info_icon)
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)){

    }
    
}