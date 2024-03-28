package com.vs.simma.ui.webview

sealed class WebViewEvent() {
    data class UpdateUrl(val url : String): WebViewEvent()
    data class OnClick (val cartUrl : String,val extractionJavaScript : String): WebViewEvent()
}