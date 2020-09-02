package com.onlinemadrasa

import android.content.res.Configuration
import android.os.Bundle
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayer.OnFullscreenListener
import com.google.android.youtube.player.YouTubePlayerView
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : YouTubeFailureRecoveryActivity(), OnFullscreenListener {

    private var fullscreen: Boolean = false
    private var videoID: String? = null
    private var title: String? = null
    private var description: String? = null
    private var player: YouTubePlayer? = null

    lateinit var mAdView: AdView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        videoID = intent.getStringExtra("VIDEO_ID")
        title = intent.getStringExtra("TITLE")
        description = intent.getStringExtra("DESC")

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
        } else {
            // In portrait
            title_text.text = title
            desc_text.text = description
        }

        val youTubeView = findViewById(R.id.youtube_view) as YouTubePlayerView
        youTubeView.initialize(DeveloperKey.getAPIKey(), this)
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?, player: YouTubePlayer,
        wasRestored: Boolean
    ) {
        this.player = player
        if (!wasRestored) {
            player.cueVideo(videoID)
        }
        if (wasRestored) {
            player.play()
        }
    }

    override fun onBackPressed() {
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            player?.let {
                player?.setFullscreen(false)
            }
        } else {
            super.onBackPressed()
        }
    }


    override fun getYouTubePlayerProvider(): YouTubePlayer.Provider {
        return findViewById(R.id.youtube_view) as YouTubePlayerView
    }

    override fun onFullscreen(isFullscreen: Boolean) {
        fullscreen = isFullscreen
    }
}