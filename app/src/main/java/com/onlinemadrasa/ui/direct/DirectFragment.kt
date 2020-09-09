package com.onlinemadrasa.ui.direct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.youtube.YouTube
import com.onlinemadrasa.R
import com.onlinemadrasa.adapter.RedirectAdapter
import com.onlinemadrasa.utils.OnAlertShow
import com.onlinemadrasa.utils.Utils

class DirectFragment : Fragment(), OnAlertShow {

    private lateinit var mainRcv: RecyclerView

    private var mYoutubeDataApi: YouTube? = null
    private val mJsonFactory = GsonFactory()
    private val mTransport = AndroidHttp.newCompatibleTransport()

    private var onAlertShow: OnAlertShow = this

    lateinit var mAdView: AdView

    external fun getArrayIDS(): String

    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_gallery, container, false)
        mAdView = root.findViewById(R.id.adView)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainRcv = view.findViewById(R.id.mainRcv)
        mainRcv.layoutManager = GridLayoutManager(context, 2)

        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        var list: ArrayList<String> = ArrayList()
        val array = getArrayIDS()
        val splitValues = array.split(",")
        for (value in splitValues) {
            list.add(value)
        }
        mainRcv.adapter = context?.let { RedirectAdapter(it, list, onAlertShow) }

        mYoutubeDataApi = YouTube.Builder(mTransport, mJsonFactory, null)
            .setApplicationName(resources.getString(R.string.app_name))
            .build()
    }

    override fun onAlertShow() {
        Utils.showToast(context, getString(R.string.no_internet))
    }
}