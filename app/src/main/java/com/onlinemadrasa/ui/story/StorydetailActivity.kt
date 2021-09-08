package com.onlinemadrasa.ui.story

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.onlinemadrasa.BuildConfig
import com.onlinemadrasa.R
import com.onlinemadrasa.model.StoryItem
import kotlinx.android.synthetic.main.activity_storydetail.*

class StorydetailActivity : AppCompatActivity() {
    private var storyItem: StoryItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storydetail)
        storyItem = intent.getParcelableExtra("ITEM")
        if (storyItem != null) {
            content_text.text = storyItem?.content
            title_text.text = storyItem?.title
        }

        back_image.setOnClickListener {
            finish()
        }

        action_float.setOnClickListener {

            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                storyItem?.title + " I found this Amazing story  on Online Madrasa Application " + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n" + storyItem?.content
            )
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }
    }
}