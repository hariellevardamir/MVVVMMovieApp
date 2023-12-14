package com.example.mvvmmovieapp.ui.activities

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.mvvmmovieapp.R
import com.example.mvvmmovieapp.databinding.ActivityMovieHomePageBinding

class MovieHomePageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieHomePageBinding
    private var homePageUrl: String? = null
    private lateinit var webView: WebView
    private lateinit var settings: WebSettings
    private var TAG = "MovieHomePage"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieHomePageBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_MVVMMovieApp)
        setContentView(binding.root)
        window.navigationBarColor = getColor(R.color.black)

        getInformationFromIntent()
        setUpWebView()
    }

    private fun getInformationFromIntent() {
        val intent = intent
        homePageUrl = intent.getStringExtra(MovieDetailsActivity.MOVIE_HOMEPAGE_URL)
        Log.d(TAG, "$homePageUrl")
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebView() {
        webView = binding.webView
        settings = webView.settings
        settings.javaScriptEnabled = true
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(homePageUrl.toString())
        }
    }
}