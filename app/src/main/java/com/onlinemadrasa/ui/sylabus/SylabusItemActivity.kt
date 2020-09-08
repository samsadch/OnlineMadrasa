package com.onlinemadrasa.ui.sylabus


import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.onlinemadrasa.R

class SylabusItemActivity : AppCompatActivity() {

    lateinit var syl_image: ImageView
    lateinit var mAdView: AdView
    lateinit var back_image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sylabus_item_fragment)
        syl_image = findViewById(R.id.syl_image)
        syl_image.setImageResource(intent.getIntExtra("image_id", R.drawable.syl_ten))
        back_image = findViewById(R.id.back_image)

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        back_image.setOnClickListener {
            finish()
        }
    }
}