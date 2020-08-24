package com.onlinemadrasa

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Pair
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.flipkart.youtubeview.activity.YouTubeActivity
import com.google.android.material.snackbar.Snackbar
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.Video
import com.onlinemadrasa.adapter.PlaylistCardAdapter
import com.onlinemadrasa.model.OnVideoSelect
import com.onlinemadrasa.model.PlaylistVideos
import com.onlinemadrasa.network.GetTask
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_listing)
        listRcv = findViewById(R.id.listRcv)
        youtubePlayListItem = intent.getStringExtra("ITEM")

        //card
        //listRcv.layoutManager = LinearLayoutManager(context)
        //gid
        listRcv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        mYouTubeDataApi = YouTube.Builder(mTransport, mJsonFactory, null)
            .setApplicationName(resources.getString(R.string.app_name))
            .build()

        onVideoSelect = this

        if (mPlaylistVideos != null) {
            reloadUi(mPlaylistVideos!!, false)
        } else {
            mPlaylistVideos = PlaylistVideos(youtubePlayListItem)
            reloadUi(mPlaylistVideos!!, true
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
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
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
                this.context?.let {
                    if (playlistVideos.playlistId != null) {
                        object : GetTask(context, mYouTubeDataApi) {
                            override fun onPostExecute(result: Pair<String, List<Video>>) {
                                handleGetPlaylistResult(playlistVideos, result)
                                Utils.hideProgress()
                            }
                        }.execute(playlistVideos.playlistId, playlistVideos.nextPageToken)
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
                        context?.let {
                            object : GetTask(context, mYouTubeDataApi) {
                                override fun onPostExecute(result: Pair<String, List<Video>>) {
                                    Utils.hideProgress()
                                    handleGetPlaylistResult(playlistVideos, result)
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
        playlistVideos.addAll(result.second)
        mPlaylistCardAdapter!!.notifyItemRangeInserted(positionStart, result.second.size)
    }

    interface LastItemReachedListener {
        fun onLastItem(position: Int, nextPageToken: String?)
    }

    var isOnline = false

    override fun onVideoSelect(url: String) {
        if (!isOnline(context!!)) {
            isOnline = false
            showSnackBar(getString(R.string.no_internet))
        } else {
            isOnline = true
            val intent = Intent(context, YouTubeActivity::class.java)
            intent.putExtra("apiKey", getString(R.string.api_key))
            intent.putExtra("videoId", url)
            startActivity(intent)
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
            ?.let { snackbarView.setBackgroundColor(it) }
        val textView = snackbarView.findViewById(R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        sb.setAction("") {
            sb.dismiss()
        }
        sb.show()
    }
}