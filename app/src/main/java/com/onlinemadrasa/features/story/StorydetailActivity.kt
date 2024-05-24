package com.onlinemadrasa.features.story

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.onlinemadrasa.databinding.ActivityStorydetailBinding
import com.onlinemadrasa.model.StoryItem

class StorydetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStorydetailBinding
    private var storyItem: StoryItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStorydetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        storyItem = intent.getParcelableExtra("ITEM")
        if (storyItem != null) {
            binding.contentText.text = storyItem?.content
            binding.titleText.text = storyItem?.title
        }

        binding.backImage.setOnClickListener {
            finish()
        }

        binding.actionFloat.setOnClickListener {

            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                storyItem?.title + " I found this Amazing story  on Online Madrasa Application " + "https://play.google.com/store/apps/details?id=com.onlinemadrasa" + "\n" + storyItem?.content
            )
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }
    }
}