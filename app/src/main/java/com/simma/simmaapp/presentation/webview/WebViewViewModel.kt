package com.simma.simmaapp.presentation.webview


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.util.Log.e
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonSyntaxException
import com.simma.simmaapp.remote.Repository
import com.simma.simmaapp.utils.Constants.EXTRACTION_DATA
import com.simma.simmaapp.utils.Constants.SHOP_ID
import com.simma.simmaapp.utils.Constants.selectedUrl
import com.simma.simmaapp.utils.Encryption
import com.simma.simmaapp.utils.Helpers.getUserId
import com.vs.simma.ui.webview.WebViewEvent
import com.vs.simma.ui.webview.WebViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class WebViewViewModel @Inject constructor(
    private val repository: Repository,
    @ApplicationContext private val appContext: Context
) : ViewModel() {


    var state by mutableStateOf(WebViewState())
    var url by mutableStateOf(selectedUrl)
    var isAnimated by mutableStateOf(false)
    private var isBack = false

    @SuppressLint("StaticFieldLeak")
    val webView = WebView(appContext).apply {
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        settings.setSupportZoom(true)
        setLayerType(View.LAYER_TYPE_HARDWARE, null)
        clearCache(true)
        setInitialScale(1)
        // set up webView
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.allowFileAccess = true
        settings.allowContentAccess = true
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

        webViewClient = object : WebViewClient() {
            override fun doUpdateVisitedHistory(
                view: WebView?,
                url: String?,
                isReload: Boolean
            ) {
                Log.e("update", "${view?.url}")
                onEvent(WebViewEvent.UpdateUrl(view?.url!!))
                super.doUpdateVisitedHistory(view, url, isReload)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.e("finish", "${view?.url}")

            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                Log.e("start", "${view?.url}")
            }
        }
        webChromeClient = WebChromeClient()

    }


    fun onEvent(event: WebViewEvent) {
        when (event) {
            is WebViewEvent.UpdateUrl -> {
                isBack = false
                state = if (event.url.contains("cart")) {
                    isAnimated = true
                    state.copy(btnText = "Checkout", goToCheckout = true)
                } else {
                    isAnimated = false
                    state.copy(btnText = "View Cart", goToCheckout = false)
                }
                url = event.url
            }

            is WebViewEvent.OnClick -> {
                if (state.goToCheckout) {
                    Log.e(
                        "response",
                        "evaluation started"
                    )
                    isBack = true

                    url = event.cartUrl
                    Log.w("response", "s = ${event.extractionJavaScript}")
                    webView.evaluateJavascript(event.extractionJavaScript) { data ->
                        val gson = Gson()
                        try {
                            val jsonArray = gson.fromJson(data, JsonArray::class.java)
                            val encryptedData =
                                Encryption.encryptData(data, iv = getUserId(appContext))
                            val resultMap = mapOf<String, Any>(
                                "cart" to jsonArray
                            )
                            e("ayham", resultMap.toString())
                            SHOP_ID = event.id
                            EXTRACTION_DATA = resultMap
                            event.navigate()
                        } catch (e: JsonSyntaxException) {
                            e("ayham","Please wait")
                        }
                    }


                } else {
                    state = state.copy(btnText = "Checkout", goToCheckout = true)
                    url = event.cartUrl
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        webView.clearHistory()
        webView.clearCache(true)
        webView.destroy()
    }


}