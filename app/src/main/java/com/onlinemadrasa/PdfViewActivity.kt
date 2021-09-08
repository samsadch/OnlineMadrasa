package com.onlinemadrasa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.PDFView


class PdfViewActivity : AppCompatActivity() {

    var furl: String? = null
    lateinit var pdfView: PDFView
    var pagenm = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(R.layout.activity_pdf_view)
            val stringBuilder = StringBuilder()
            stringBuilder.append("pdf/quran_ar_full.pdf")
            this.pdfView = findViewById(R.id.pdfview)
            this.pdfView.fromAsset(stringBuilder.toString()).defaultPage(0).load()
        } catch (e: Exception) {
            finish()
            e.printStackTrace()
        }
    }
}
