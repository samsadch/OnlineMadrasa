package com.onlinemadrasa

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.core.app.ShareCompat
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayer.OnFullscreenListener
import com.google.android.youtube.player.YouTubePlayerView
import com.onlinemadrasa.utils.Utils
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : YouTubeFailureRecoveryActivity(), OnFullscreenListener,
    YouTubePlayer.PlaybackEventListener {

    private var fullscreen: Boolean = false
    private var videoID: String? = null
    private var title: String? = null
    private var description: String? = null
    private var player: YouTubePlayer? = null
    lateinit var mAdView: AdView

    private lateinit var mInterstitialAd: InterstitialAd

    var stopCount = 0
    var seekCount = 0
    var pauseCount = 0

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
            pip_image.setOnClickListener {
                ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain")
                    .setChooserTitle("Share Online Madrasa")
                    .setText("http://play.google.com/store/apps/details?" + this.packageName)
                    .startChooser()
            }
        }


        val youTubeView = findViewById(R.id.youtube_view) as YouTubePlayerView
        youTubeView.initialize(apiKey, this)

        MobileAds.initialize(this) {}
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = getString(R.string.inters_ad_id)
        mInterstitialAd.loadAd(AdRequest.Builder().build())
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
        return findViewById(R.id.youtube_view) as YouTubePlayerView
    }

    override fun onFullscreen(isFullscreen: Boolean) {
        fullscreen = isFullscreen
    }

    override fun onSeekTo(p0: Int) {
        seekCount+=1
        if(seekCount==2||seekCount==5||seekCount==8){
            showIntesAd()
        }
    }

    override fun onBuffering(p0: Boolean) {

    }

    override fun onPlaying() {

    }

    override fun onStopped() {
        stopCount += 1
        if (stopCount==1 || stopCount==3) {
            showIntesAd()
        }
    }

    override fun onPaused() {
        pauseCount += 1
    }


    private fun showIntesAd() {
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.")
        }
    }

    fun isDevidedBy2(value :Int):Boolean{
        return value % 2 ==0 && value!=0
    }
}