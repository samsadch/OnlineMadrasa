package com.onlinemadrasa

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Pair
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.adcolony.sdk.AdColonyAdView
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
import com.onlinemadrasa.adapter.PlaylistCardAdapterNew
import com.onlinemadrasa.model.OnVideoSelect
import com.onlinemadrasa.model.PlaylistVideos
import com.onlinemadrasa.network.GetTask
import com.onlinemadrasa.utils.Utils
import com.onlinemadrasa.utils.loadAdaptiveBanner
import kotlinx.android.synthetic.main.activity_video_listing.*
import java.util.*


class VideoListingActivity : AppCompatActivity(), OnVideoSelect {

    private var mYouTubeDataApi: YouTube? = null
    private val mJsonFactory = GsonFactory()
    private val mTransport = AndroidHttp.newCompatibleTransport()
    private val context: Context = this@VideoListingActivity
    private lateinit var onVideoSelect: OnVideoSelect
    lateinit var listRcv: RecyclerView
    lateinit var tileRcv: RecyclerView
    lateinit var gridRcv: RecyclerView
    lateinit var youtubePlayListItem: String

    private var mPlaylistTitles: ArrayList<String>? = null
    private var mPlaylistVideos: PlaylistVideos? = null
    private var mPlaylistCardAdapter: PlaylistCardAdapterNew? = null
    private var mPlaylistCardAdapterOld: PlaylistCardAdapter? = null
    private lateinit var adRlay: RelativeLayout

    private lateinit var mInterstitialAd: InterstitialAd

    private var isFirstTime = 0

    external fun getAPIKey(): String

    companion object {
        init {
            //System.load("keys")
            System.loadLibrary("native-lib")
        }
    }

    var isFirstTimeList = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_listing)
        listRcv = findViewById(R.id.listRcv)
        gridRcv = findViewById(R.id.gridRcv)
        tileRcv = findViewById(R.id.tileRcv)
        adRlay = findViewById(R.id.adRlay)
        adRlay = findViewById(R.id.adRlay)
        youtubePlayListItem = intent.getStringExtra("ITEM")

        MobileAds.initialize(context) {}
        mInterstitialAd = InterstitialAd(context)
        mInterstitialAd.adUnitId = getString(R.string.inters_ad_id)

        mInterstitialAd.loadAd(AdRequest.Builder().build())

        loadAdaptiveBanner(this, adRlay)

        //card
        //listRcv.layoutManager = LinearLayoutManager(context)
        //gid
        listRcv.layoutManager = LinearLayoutManager(context)
        gridRcv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        tileRcv.layoutManager = LinearLayoutManager(context)

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
            gridRcv.visibility = View.GONE
            tileRcv.visibility = View.VISIBLE
            listRcv.visibility = View.GONE
            isFirstTimeList = false
            reloadUi(mPlaylistVideos!!, false)
            mInterstitialAd.loadAd(AdRequest.Builder().build())
            if (mInterstitialAd.isLoaded) {
                mInterstitialAd.show()
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.")
            }
        }

        gridImv.setOnClickListener {
            isFirstTimeList = false
            reloadUi(mPlaylistVideos!!, false)
            gridRcv.visibility = View.VISIBLE
            tileRcv.visibility = View.GONE
            listRcv.visibility = View.GONE

            mInterstitialAd.loadAd(AdRequest.Builder().build())
            if (mInterstitialAd.isLoaded) {
                mInterstitialAd.show()
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.")
            }
        }

        listImv.setOnClickListener {
            gridRcv.visibility = View.GONE
            tileRcv.visibility = View.GONE
            listRcv.visibility = View.VISIBLE
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
                                        if (result != null) {
                                            handleGetPlaylistResult(playlistVideos, result)
                                            Utils.hideProgress()
                                        } else {
                                            val intent = Intent(Intent.ACTION_VIEW)
                                            intent.data =
                                                Uri.parse(getString(R.string.youtube_playlist_append) + youtubePlayListItem)
                                            intent.setPackage("com.google.android.youtube")
                                            startActivity(intent)
                                        }

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
        if (isFirstTimeList) {
            mPlaylistCardAdapter = PlaylistCardAdapterNew(
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
                                            val intent = Intent(Intent.ACTION_VIEW)
                                            intent.data =
                                                Uri.parse(context.getString(R.string.youtube_playlist_append) + youtubePlayListItem)
                                            intent.setPackage("com.google.android.youtube")
                                            context.startActivity(intent)
                                        }
                                    }
                                }.execute(playlistVideos.playlistId, playlistVideos.nextPageToken)
                            }
                        }
                    }
                }, onVideoSelect
            )
        } else {
            mPlaylistCardAdapterOld = PlaylistCardAdapter(
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
                                            val intent = Intent(Intent.ACTION_VIEW)
                                            intent.data =
                                                Uri.parse(context.getString(R.string.youtube_playlist_append) + youtubePlayListItem)
                                            intent.setPackage("com.google.android.youtube")
                                            context.startActivity(intent)
                                        }
                                    }
                                }.execute(playlistVideos.playlistId, playlistVideos.nextPageToken)
                            }
                        }
                    }
                }, onVideoSelect
            )
        }
        listRcv.adapter = mPlaylistCardAdapter
        gridRcv.adapter = mPlaylistCardAdapterOld
        tileRcv.adapter = mPlaylistCardAdapterOld
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
        mPlaylistCardAdapterOld?.notifyItemRangeInserted(positionStart, result.second.size)

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