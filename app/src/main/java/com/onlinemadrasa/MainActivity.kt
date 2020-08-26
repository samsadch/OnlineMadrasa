package com.onlinemadrasa

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.snackbar.Snackbar
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.youtube.YouTube
import com.onlinemadrasa.adapter.MainAdapter
import com.onlinemadrasa.utils.OnAlertShow

class MainActivity : AppCompatActivity(),OnAlertShow{

    private lateinit var mainRcv: RecyclerView
    var context = this@MainActivity

    private var mYoutubeDataApi: YouTube? = null
    private val mJsonFactory = GsonFactory()
    private val mTransport = AndroidHttp.newCompatibleTransport()

    private var onAlertShow:OnAlertShow = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainRcv = findViewById(R.id.mainRcv)
        MobileAds.initialize(
            this
        ) {

        }
        mainRcv.layoutManager = GridLayoutManager(context, 2)
        var list: ArrayList<String> = ArrayList()
        val array = arrayListOf("test","test")
        for (value in array) {
            list.add(value)
        }
        mainRcv.adapter = MainAdapter(context, list,onAlertShow)


        mYoutubeDataApi = YouTube.Builder(mTransport, mJsonFactory, null)
            .setApplicationName(resources.getString(R.string.app_name))
            .build()
    }

    fun isConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    private fun showSnackBar(message: String) {
        val sb = Snackbar.make(findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_INDEFINITE
        )

        val snackbarView = sb.view
        snackbarView.setBackgroundColor(context.resources.getColor(R.color.color_button))
        val textView = snackbarView.findViewById(R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        sb.setAction("") {
            sb.dismiss()
        }
        sb.show()
    }

    override fun onAlertShow() {
        showSnackBar(getString(R.string.no_internet))
    }
}