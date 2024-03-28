package com.simma.simmaapp.presentation.webview

import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
@Preview
@Composable
fun TermsAndConditionsScreen() {
        AndroidView(
            factory = {
                WebView(it)
            },
            update = {
                it.loadUrl("https://www.simma.io/terms-privacy")
        })
    }
