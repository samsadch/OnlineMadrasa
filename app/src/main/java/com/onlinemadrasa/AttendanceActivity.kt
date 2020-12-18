package com.onlinemadrasa

import android.content.Context
import android.content.DialogInterface
import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.onlinemadrasa.utils.loadAdaptiveBanner
import kotlinx.android.synthetic.main.activity_attendance.*

class AttendanceActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    val mContext: Context = this@AttendanceActivity
    lateinit var progress: ProgressBar
    lateinit var ad_rlay: RelativeLayout

    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance)
        webView = findViewById(R.id.web_view)
        progress = findViewById(R.id.progress)
        MobileAds.initialize(mContext) {}
        mInterstitialAd = InterstitialAd(mContext)
        mInterstitialAd.adUnitId = getString(R.string.inters_ad_id)
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        startWebView(getString(R.string.attendance_url))
        ad_rlay = findViewById(R.id.ad_rlay)

        backImv.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        loadAdaptiveBanner(this, ad_rlay)
    }

    private fun startWebView(url: String) {

        //Create new webview Client to show progress dialog
        //When opening a url or click on link
        webView.webViewClient = object : WebViewClient() {
            //If you will not use this method url links are opeen in new brower not in webview
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                progress.visibility = View.VISIBLE
                return true
            }

            //Show loader on url load
            override fun onLoadResource(view: WebView, url: String) {
                progress.visibility = View.VISIBLE
            }

            override fun onReceivedSslError(
                view: WebView,
                handler: SslErrorHandler,
                error: SslError
            ) {
                val builder =
                    AlertDialog.Builder(mContext)
                builder.setMessage("Webview ssl error")
                builder.setPositiveButton(
                    "continue"
                ) { dialog, which -> handler.proceed() }
                builder.setNegativeButton(
                    "cancel"
                ) { dialog, which -> handler.cancel() }
                val dialog = builder.create()
                dialog.show()
            }

            override fun onPageFinished(view: WebView, url: String) {
                try {
                    progress.visibility = View.GONE
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
            }
        }

        // Javascript inabled on webview
        webView.settings.javaScriptEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true

        //Load url in webview
        webView.webViewClient = SSLTolerentWebViewClient(mContext)
        webView.loadUrl(url)

        mInterstitialAd.loadAd(AdRequest.Builder().build())
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.")
        }
    }


    private class SSLTolerentWebViewClient(var context: Context) : WebViewClient() {
        override fun onReceivedSslError(
            view: WebView?,
            handler: SslErrorHandler?,
            error: SslError?
        ) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            val alertDialog: AlertDialog = builder.create()
            var message = "SSL Certificate error."
            when (error?.primaryError) {
                SslError.SSL_UNTRUSTED -> message = "The certificate authority is not trusted."
                SslError.SSL_EXPIRED -> message = "The certificate has expired."
                SslError.SSL_IDMISMATCH -> message = "The certificate Hostname mismatch."
                SslError.SSL_NOTYETVALID -> message = "The certificate is not yet valid."
            }
            message += " Do you want to continue anyway?"
            alertDialog.setTitle("SSL Certificate Error")
            alertDialog.setMessage(message)
            alertDialog.setButton(
                DialogInterface.BUTTON_POSITIVE,
                "OK"
            ) { dialog, which -> // Ignore SSL certificate errors
                handler?.proceed()
            }
            alertDialog.setButton(
                DialogInterface.BUTTON_NEGATIVE,
                "Cancel"
            ) { dialog, which -> handler?.cancel() }
            alertDialog.show()
        }
    }

    override fun onPause() {
        super.onPause()
        webView.onPause()
    }

}