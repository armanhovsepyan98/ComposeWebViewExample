package com.app.webviewcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.app.webviewcompose.ui.theme.WebViewComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WebViewComposeTheme {
                WebViewPage("https://medium.com/")
            }
        }
    }
}

