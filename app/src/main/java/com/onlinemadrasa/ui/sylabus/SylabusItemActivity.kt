package com.onlinemadrasa.ui.sylabus


import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.onlinemadrasa.R
import com.onlinemadrasa.utils.loadAdaptiveBanner
import kotlinx.android.synthetic.main.sylabus_item_fragment.*

class SylabusItemActivity : AppCompatActivity() {

    lateinit var syl_image: ImageView
    lateinit var back_image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sylabus_item_fragment)
        syl_image = findViewById(R.id.syl_image)
        syl_image.setImageResource(intent.getIntExtra("image_id", R.drawable.syl_ten))
        back_image = findViewById(R.id.back_image)

        back_image.setOnClickListener {
            finish()
        }

        loadAdaptiveBanner(this, ad_rlay)
    }
}