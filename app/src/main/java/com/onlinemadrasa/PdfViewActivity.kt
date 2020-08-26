package com.onlinemadrasa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.PDFView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView


class PdfViewActivity : AppCompatActivity() {

    var furl: String? = null
    lateinit var pdfView: PDFView
    var pagenm = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(R.layout.activity_pdf_view)
            val stringBuilder = StringBuilder()
            stringBuilder.append("pdf/circular.pdf")
            this.pdfView = findViewById(R.id.pdfview)
            this.pdfView.fromAsset(stringBuilder.toString()).defaultPage(0).load()

            val mAdView: AdView = findViewById(R.id.adView)
            val adRequest = AdRequest.Builder().build()
            mAdView.loadAd(adRequest)


            val mAdView2: AdView = findViewById(R.id.adView2)
            val adRequest2 = AdRequest.Builder().build()
            mAdView2.loadAd(adRequest2)


        }catch (e:Exception){
            finish()
            e.printStackTrace()
        }

    }
}
