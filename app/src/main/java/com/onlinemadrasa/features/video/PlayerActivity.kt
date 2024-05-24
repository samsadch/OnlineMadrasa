package com.onlinemadrasa.features.video

import android.content.res.Configuration
import android.os.Bundle
import androidx.core.app.ShareCompat
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayer.OnFullscreenListener
import com.google.android.youtube.player.YouTubePlayerView
import com.onlinemadrasa.R
import com.onlinemadrasa.databinding.ActivityPlayerBinding

class PlayerActivity : YouTubeFailureRecoveryActivity(), OnFullscreenListener,
    YouTubePlayer.PlaybackEventListener {

    lateinit var binding: ActivityPlayerBinding

    private var fullscreen: Boolean = false
    private var videoID: String? = null
    private var title: String? = null
    private var description: String? = null
    private var player: YouTubePlayer? = null

    var stopCount = 0
    var seekCount = 0
    var pauseCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        videoID = intent.getStringExtra("VIDEO_ID")
        title = intent.getStringExtra("TITLE")
        description = intent.getStringExtra("DESC")

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
        } else {
            // In portrait
            binding.backImv.setOnClickListener {
                finish()
            }
            binding.titleText.text = title
            binding.descText.text = description
            binding.pipImage.setOnClickListener {
                ShareCompat.IntentBuilder.from(this).setType("text/plain")
                    .setChooserTitle("Share Online Madrasa")
                    .setText("http://play.google.com/store/apps/details?" + this.packageName)
                    .startChooser()
            }
        }

        val youTubeView = findViewById<YouTubePlayerView>(R.id.youtube_view)
        youTubeView.initialize(apiKey, this)
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?, player: YouTubePlayer, wasRestored: Boolean
    ) {
        this.player = player
        if (!wasRestored) {
            player.cueVideo(videoID)
        }
        if (wasRestored) {
            player.play()
        }

        this.player!!.setPlaybackEventListener(this)
        this.player!!.setOnFullscreenListener(this)
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
        return findViewById<YouTubePlayerView>(R.id.youtube_view)
    }

    override fun onFullscreen(isFullscreen: Boolean) {
        fullscreen = isFullscreen
    }


    override fun onBuffering(p0: Boolean) {

    }

    override fun onSeekTo(p0: Int) {

    }

    override fun onPlaying() {

    }

    override fun onStopped() {

    }

    override fun onPaused() {
        pauseCount += 1
    }
}