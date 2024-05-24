package com.onlinemadrasa.features.story

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.onlinemadrasa.R
import com.onlinemadrasa.databinding.ActivityStoryBinding
import com.onlinemadrasa.model.StoryItem
import com.onlinemadrasa.model.StoryModel

class StoryActivity : AppCompatActivity() {

    private var storyItmeList = ArrayList<StoryItem>()
    private lateinit var model: StoryModel
    lateinit var storyRcv: RecyclerView
    lateinit var title: String
    val binding: ActivityStoryBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityStoryBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        storyRcv = findViewById(R.id.story_list_rcv)
        model = StoryTitlesAdapter.storyItem!!
        binding.titleText.text = model.storyTitle
        val list = model.storyList
        if (list != null) {
            for (item in list) {
                storyItmeList.add(item)
            }
        }

        storyRcv.adapter = StoryAdapter(this, storyItmeList)
        storyRcv.layoutManager = LinearLayoutManager(this)

        binding.backImage.setOnClickListener {
            finish()
        }
    }
}