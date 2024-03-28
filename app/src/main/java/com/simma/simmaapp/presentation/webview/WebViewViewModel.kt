package com.simma.simmaapp.presentation.webview


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.util.Log.e
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import com.simma.simmaapp.model.cartResponseModel.CartViewResponseModel
import com.simma.simmaapp.presentation.homePage.HomeActivity
import com.simma.simmaapp.remote.Repository
import com.simma.simmaapp.utils.Constants
//import com.simma.simmaapp.utils.Constants.EXTRACTION_DATA
import com.simma.simmaapp.utils.Constants.NAVIGATE_TO_CART
import com.simma.simmaapp.utils.Constants.SHOP_ID
import com.simma.simmaapp.utils.Constants.selectedUrl
import com.simma.simmaapp.utils.Encryption
import com.simma.simmaapp.utils.Helpers.getUserId
import com.vs.simma.model.listingModel.Resource
import com.vs.simma.ui.webview.WebViewEvent
import com.vs.simma.ui.webview.WebViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WebViewViewModel @Inject constructor(
    private val repository: Repository,
    @ApplicationContext private val appContext: Context
) : ViewModel() {

//    init {
//        viewModelScope.launch {
//            EXTRACTION_DATA.collect{
//                getCartData(SHOP_ID,it)
//            }
//        }
//
//    }

    fun getCartData(id: String, json: Map<String, Any>) {
        viewModelScope.launch {
            repository.getCart(id, json).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        if(result.data.items.isEmpty()){
                            HomeActivity.showToast(false,"Please add items to your cart before proceeding")
                        }else{
                            Constants.CART_RESPONSE = result.data
                            NAVIGATE_TO_CART.invoke()
                        }
                    }

                    is Resource.Error ->
                        Unit

                    is Resource.Loading ->
                        Unit

                }
            }
        }
    }

    var cartUrl = ""
    var state by mutableStateOf(WebViewState())
    var url by mutableStateOf(selectedUrl)
    var isAnimated by mutableStateOf(false)
    private var isBack = false

    @SuppressLint("StaticFieldLeak")
    val webView = WebView(appContext).apply {
//        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        settings.setSupportZoom(true)
        setLayerType(View.LAYER_TYPE_HARDWARE, null)
        clearCache(true)
//            addJavascriptInterface(WebAppInterface(
//                        event,appContext
//            ), "Android")
        val javaInterface = WebAppInterface()
        addJavascriptInterface(javaInterface, "Android")

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
                if (view?.url == cartUrl)
                state = state.copy(btnText = "Checkout", goToCheckout = true)

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
                e("ayhamCartUrl",cartUrl)
                e("ayhamCartUrl",event.url)
                isBack = false
                state = if (event.url == cartUrl) {
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
                    webView.evaluateJavascript(event.extractionJavaScript){}
//                    { data ->
//                        val gson = Gson()
//                        try {
//                            val jsonArray = gson.fromJson(data, JsonArray::class.java)
//                            val encryptedData =
//                                Encryption.encryptData(data, iv = getUserId(appContext))
//                            val resultMap = mapOf<String, Any>(
//                                "cart" to jsonArray
//                            )
//                            e("ayham", resultMap.toString())
//                            SHOP_ID = event.id
//                            EXTRACTION_DATA = resultMap
//                            event.navigate()
//                        } catch (e: JsonSyntaxException) {
//                            e("ayham","Please wait")
//                            e("ayham",data)
//                        }
//                    }


                } else {

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
     inner class WebAppInterface(
    ) {
        fun printLargeLog(tag: String, message: String) {
            val maxLogSize = 1000
            for (i in 0..message.length step maxLogSize) {
                val end = if (i + maxLogSize > message.length) message.length else i + maxLogSize
                android.util.Log.d(tag, message.substring(i, end))
            }
        }

        @JavascriptInterface
        fun onItemsExtracted(itemsJson: String){

            printLargeLog("ayham", itemsJson)
            val gson = GsonBuilder().serializeNulls().create()
            val jsonArray = gson.fromJson(itemsJson, JsonElement::class.java)
            printLargeLog("ayham", itemsJson)
            val resultMap = mapOf<String, Any>(
                "cart" to jsonArray
            )
            e("ayhamMap", resultMap.toString())
//            EXTRACTION_DATA.update { resultMap }
            getCartData(SHOP_ID,resultMap)
        }
    }
}

