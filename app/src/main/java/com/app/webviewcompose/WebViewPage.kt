package com.app.webviewcompose

import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebViewPage(url: String) {

    var backEnabled by remember { mutableStateOf(false) }
    var webView: WebView? = null

    val loaderDialogScreen = remember { mutableStateOf(false) }
    val errorDialogScreen = remember { mutableStateOf(false) }

    if (loaderDialogScreen.value) {
        WebViewLoading(loaderDialogScreen)
    }
    if (errorDialogScreen.value) {
        WebViewError(errorDialogScreen)
    }

    AndroidView(factory = {
        WebView(it).apply {

            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            webViewClient = WebViewClient()

            settings.javaScriptEnabled = true
            settings.userAgentString = System.getProperty("http.agent")

            webViewClient = object : WebViewClient() {

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    backEnabled = view!!.canGoBack()
                    loaderDialogScreen.value = true
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    loaderDialogScreen.value = false
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    errorDialogScreen.value = true
                }
            }

            loadUrl(url)
            webView = this
        }
    }, update = {
        webView = it
    })

    BackHandler(enabled = backEnabled) {
        webView?.goBack()
    }

}