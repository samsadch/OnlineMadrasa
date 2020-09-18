package com.onlinemadrasa

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Pair
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.adcolony.sdk.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.material.snackbar.Snackbar
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.Video
import com.onlinemadrasa.adapter.PlaylistCardAdapter
import com.onlinemadrasa.model.OnVideoSelect
import com.onlinemadrasa.model.PlaylistVideos
import com.onlinemadrasa.network.GetTask
import com.onlinemadrasa.utils.OnAlertHide
import com.onlinemadrasa.utils.Utils
import kotlinx.android.synthetic.main.activity_video_listing.*
import java.util.*


class VideoListingActivity : AppCompatActivity(), OnVideoSelect {

    private var mYouTubeDataApi: YouTube? = null
    private val mJsonFactory = GsonFactory()
    private val mTransport = AndroidHttp.newCompatibleTransport()
    private val context: Context = this@VideoListingActivity
    private lateinit var onVideoSelect: OnVideoSelect
    lateinit var listRcv: RecyclerView
    lateinit var youtubePlayListItem: String

    private var mPlaylistTitles: ArrayList<String>? = null
    private var mPlaylistVideos: PlaylistVideos? = null
    private var mPlaylistCardAdapter: PlaylistCardAdapter? = null
    private val mProgressDialog: ProgressDialog? = null
    private var adRlay: RelativeLayout? = null
    private var listener: AdColonyAdViewListener? = null

    private var adView: AdColonyAdView? = null

    private lateinit var mInterstitialAd: InterstitialAd

    private var isFirstTime = 0

    external fun getAPIKey(): String

    companion object {
        init {
            //System.load("keys")
            System.loadLibrary("native-lib")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_listing)
        AdColony.configure(this, "app3cb32b3e892e4f34b2", "vze08bb2c3b37149f1b7")
        listRcv = findViewById(R.id.listRcv)
        adRlay = findViewById(R.id.adRlay)
        youtubePlayListItem = intent.getStringExtra("ITEM")

        MobileAds.initialize(context) {}
        mInterstitialAd = InterstitialAd(context)
        mInterstitialAd.adUnitId = getString(R.string.inters_ad_id)

        mInterstitialAd.loadAd(AdRequest.Builder().build())

        val mAdView: AdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)


        if (adRlay?.childCount!! > 0) {
            adRlay?.removeView(adView)
        }

        listener = object : AdColonyAdViewListener() {
            override fun onRequestFilled(adColonyAdView: AdColonyAdView) {
                Log.d("ADS", "onRequestFilled")
                adRlay?.addView(adColonyAdView)
                adView = adColonyAdView
            }

            override fun onRequestNotFilled(zone: AdColonyZone?) {
                super.onRequestNotFilled(zone)
                Log.d("ADS", "onRequestNotFilled")
            }

            override fun onOpened(ad: AdColonyAdView) {
                super.onOpened(ad)
                Log.d("ADS", "onOpened")
            }

            override fun onClosed(ad: AdColonyAdView) {
                super.onClosed(ad)
                Log.d("ADS", "onClosed")
            }

            override fun onClicked(ad: AdColonyAdView) {
                super.onClicked(ad)
                Log.d("ADS", "onClicked")
            }

            override fun onLeftApplication(ad: AdColonyAdView) {
                super.onLeftApplication(ad)
                Log.d("ADS", "onLeftApplication")
            }
        }
        //AdColony.requestAdView("vze08bb2c3b37149f1b7", listener!!, AdColonyAdSize.BANNER)

        var adOptions = AdColonyAdOptions()
        //Request Ad
        AdColony.requestAdView("vze08bb2c3b37149f1b7", listener!!, AdColonyAdSize.BANNER, adOptions)

        //card
        //listRcv.layoutManager = LinearLayoutManager(context)
        //gid
        listRcv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        mYouTubeDataApi = YouTube.Builder(mTransport, mJsonFactory, null)
            .setApplicationName(resources.getString(R.string.app_name))
            .build()

        onVideoSelect = this

        if (mPlaylistVideos != null) {
            reloadUi(mPlaylistVideos!!, false)
        } else {
            mPlaylistVideos = PlaylistVideos(youtubePlayListItem)
            reloadUi(
                mPlaylistVideos!!, true
            )
        }

        backImv.setOnClickListener {
            finish()
        }

        cardImv.setOnClickListener {
            listRcv.layoutManager = LinearLayoutManager(context)
        }

        gridImv.setOnClickListener {
            listRcv.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }

        listImv.setOnClickListener {

        }

    }


    private fun reloadUi(
        playlistVideos: PlaylistVideos,
        fetchPlaylist: Boolean
    ) {
        initCardAdapter(playlistVideos)
        if (fetchPlaylist) {
            mYouTubeDataApi?.let {
                this.context.let {
                    if (playlistVideos.playlistId != null) {
                        if (context != null) {
                            if (mYouTubeDataApi != null) {
                                object : GetTask(context, mYouTubeDataApi) {
                                    override fun onPostExecute(result: Pair<String, List<Video>>?) {
                                        handleGetPlaylistResult(playlistVideos, result)
                                        Utils.hideProgress()
                                    }
                                }.execute(playlistVideos.playlistId, playlistVideos.nextPageToken)
                            }
                        }
                    }
                }

            }
        }
    }

    private fun initCardAdapter(playlistVideos: PlaylistVideos) {
        mPlaylistCardAdapter = PlaylistCardAdapter(
            playlistVideos,
            object : LastItemReachedListener {
                override fun onLastItem(position: Int, nextPageToken: String?) {
                    mYouTubeDataApi?.let {
                        context.let {
                            object : GetTask(context, mYouTubeDataApi) {
                                override fun onPostExecute(result: Pair<String, List<Video>>?) {
                                    Utils.hideProgress()
                                    if (result != null) {
                                        handleGetPlaylistResult(playlistVideos, result)
                                    } else {
                                        var onAlertHide = OnAlertHide {
                                            val intent = Intent(Intent.ACTION_VIEW)
                                            intent.data =
                                                Uri.parse(context.getString(R.string.youtube_playlist_append) + youtubePlayListItem)
                                            intent.setPackage("com.google.android.youtube")
                                            context.startActivity(intent)
                                        }
                                        Utils.showIosDialog(
                                            context,
                                            "Alert",
                                            "Updating Latest Videos. Do you want to watch in Youtube?",
                                            onAlertHide
                                        )
                                    }
                                }
                            }.execute(playlistVideos.playlistId, playlistVideos.nextPageToken)
                        }
                    }
                }
            }, onVideoSelect
        )
        listRcv.adapter = mPlaylistCardAdapter
    }

    private fun handleGetPlaylistResult(
        playlistVideos: PlaylistVideos,
        result: Pair<String, List<Video>>?
    ) {
        if (result == null) return
        val positionStart: Int = playlistVideos.size
        playlistVideos.nextPageToken = result.first
        result?.let {
            playlistVideos?.let { it2 ->
                if (it.second != null) {
                    it2.addAll(it.second)
                }
            }
        }
        mPlaylistCardAdapter?.notifyItemRangeInserted(positionStart, result.second.size)

        if (isFirstTime == 1 || isFirstTime == 7) {
            isFirstTime += 1
            if (mInterstitialAd.isLoaded) {
                mInterstitialAd.show()
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.")
            }
        } else {
            isFirstTime += 1
        }
    }

    interface LastItemReachedListener {
        fun onLastItem(position: Int, nextPageToken: String?)
    }

    var isOnline = false

    override fun onVideoSelect(url: String, title: String, description: String) {
        if (!isOnline(context)) {
            isOnline = false
            showSnackBar(getString(R.string.no_internet))
        } else {
            isOnline = true
            //VIDEO_ID
            val intent = Intent(context, PlayerActivity::class.java)
            intent.putExtra("apiKey", getAPIKey())
            intent.putExtra("VIDEO_ID", url)
            intent.putExtra("TITLE", title)
            intent.putExtra("DESC", description)
            startActivity(intent)
            /*val intent = Intent(context, YouTubeActivity::class.java)
            intent.putExtra("apiKey", getAPIKey())
            intent.putExtra("videoId", url)
            startActivity(intent)*/
        }
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    private fun showSnackBar(message: String) {
        val sb = Snackbar.make(
            findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_INDEFINITE
        )

        val snackbarView = sb.view
        context.resources.getColor(R.color.color_button)
            .let { snackbarView.setBackgroundColor(it) }
        val textView = snackbarView.findViewById(R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        sb.setAction("") {
            sb.dismiss()
        }
        sb.show()
    }
}