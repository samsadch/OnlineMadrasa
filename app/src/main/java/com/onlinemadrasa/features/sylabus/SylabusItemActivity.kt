package com.onlinemadrasa.features.sylabus


import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.onlinemadrasa.R

class SylabusItemActivity : AppCompatActivity() {

    lateinit var sylImage: ImageView
    lateinit var backImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sylabus_item_fragment)
        sylImage = findViewById(R.id.syl_image)
        sylImage.setImageResource(intent.getIntExtra("image_id", R.drawable.syl_ten))
        backImage = findViewById(R.id.back_image)

        backImage.setOnClickListener {
            finish()
        }
    }
}