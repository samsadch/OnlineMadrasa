package com.onlinemadrasa.network

import android.content.Context
import android.os.AsyncTask
import android.text.TextUtils
import android.util.Pair
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.PlaylistItemListResponse
import com.google.api.services.youtube.model.Video
import com.google.api.services.youtube.model.VideoListResponse
import com.onlinemadrasa.utils.Utils
import java.io.IOException

abstract class GetTask(
    private val context: Context,
    val mYouTubeDataApi: YouTube?
) :
    AsyncTask<String?, Void?, Pair<String, List<Video>>>() {

    private val TAG = "GetPlaylistAsyncTask"
    private val YOUTUBE_PLAYLIST_MAX_RESULTS = 50L

    private val YOUTUBE_PLAYLIST_PART = "snippet"
    private val YOUTUBE_PLAYLIST_FIELDS =
        "pageInfo,nextPageToken,items(id,snippet(resourceId/videoId))"

    private val YOUTUBE_VIDEOS_PART =
        "snippet,contentDetails,statistics"
    private val YOUTUBE_VIDEOS_FIELDS =
        "items(id,snippet(title,description,thumbnails/high),contentDetails/duration,statistics)"

    external fun getAPIKey(): String

    companion object {
        init {
            //System.load("keys")
            System.loadLibrary("native-lib")
        }
    }


    override fun onPreExecute() {
        super.onPreExecute()
        Utils.setLoading(context)
        Utils.showProgress()
    }

    override fun doInBackground(vararg params: String?): Pair<String, List<Video>>? {


        val playlistId: String = params[0].toString()

        val nextPageToken: String? = if (params.size == 2) {
            params[1]
        } else {
            null
        }

        val playlistItemListResponse: PlaylistItemListResponse?
        playlistItemListResponse = try {
            mYouTubeDataApi!!.playlistItems()
                .list(YOUTUBE_PLAYLIST_PART)
                .setPlaylistId(playlistId)
                .setPageToken(nextPageToken)
                .setFields(YOUTUBE_PLAYLIST_FIELDS)
                .setMaxResults(YOUTUBE_PLAYLIST_MAX_RESULTS)
                .setKey(getAPIKey())
                .execute()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

        if (playlistItemListResponse == null) {
            return null
        }

        //List<String> videoIds = new ArrayList();

        val videoIds = ArrayList<String>()

        /* val videoIds: MutableList<String?> =
             ArrayList<Any?>()*/

        // pull out the video id's from the playlist page

        // pull out the video id's from the playlist page
        for (item in playlistItemListResponse.items) {
            videoIds.add(item.snippet.resourceId.videoId)
        }

        var videoListResponse: VideoListResponse? = null
        try {
            videoListResponse = mYouTubeDataApi.videos()
                .list(YOUTUBE_VIDEOS_PART)
                .setFields(YOUTUBE_VIDEOS_FIELDS)
                .setKey(getAPIKey())
                .setId(TextUtils.join(",", videoIds)).execute()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return Pair<String, List<Video>>(
            playlistItemListResponse.nextPageToken,
            videoListResponse?.items
        )

    }
}