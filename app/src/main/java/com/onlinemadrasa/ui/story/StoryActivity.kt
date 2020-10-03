package com.onlinemadrasa.ui.story

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.onlinemadrasa.R
import com.onlinemadrasa.adapter.StoryAdapter
import com.onlinemadrasa.model.StoryItem
import com.onlinemadrasa.model.StoryModel
import kotlinx.android.synthetic.main.activity_story.*

class StoryActivity : AppCompatActivity() {

    private var storyItmeList = ArrayList<StoryItem>()
    private lateinit var model: StoryModel
    lateinit var mAdView: AdView
    lateinit var storyRcv: RecyclerView
    lateinit var title: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)
        mAdView = findViewById(R.id.adView)
        storyRcv = findViewById(R.id.story_list_rcv)
        model = intent.getParcelableExtra("ITEM")
        if (model != null) {
            title_text.text = model.storyTitle
            var list = model.storyList
            if (list != null) {
                for (item in list) {
                    storyItmeList.add(item)
                }
            }
        }

        storyRcv.adapter = StoryAdapter(this, storyItmeList)
        storyRcv.layoutManager = LinearLayoutManager(this)

        back_image.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }
}