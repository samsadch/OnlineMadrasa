package com.onlinemadrasa.ui.about

import android.content.Context
import android.content.DialogInterface
import android.net.http.SslError
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.onlinemadrasa.R


class AboutFragment : Fragment() {

    private lateinit var webView: WebView
    lateinit var mContext: Context
    private lateinit var viewModel: AboutViewModel
    lateinit var progress: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.about_fragment, container, false)
        webView = view.findViewById(R.id.web_view);
        progress = view.findViewById(R.id.progress)
        mContext = requireContext()
        startWebView("https://online-madrasa-118e7.web.app/");
        return view
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
                    AlertDialog.Builder(requireContext())
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
        webView.webViewClient = SSLTolerentWebViewClient(requireContext())
        webView.loadUrl(url)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AboutViewModel::class.java)
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
                "OK",
                DialogInterface.OnClickListener { dialog, which -> // Ignore SSL certificate errors
                    handler?.proceed()
                })
            alertDialog.setButton(
                DialogInterface.BUTTON_NEGATIVE,
                "Cancel",
                DialogInterface.OnClickListener { dialog, which -> handler?.cancel() })
            alertDialog.show()
        }
    }

    override fun onPause() {
        super.onPause()
        webView?.onPause()
    }

}
