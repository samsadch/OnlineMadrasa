package com.onlinemadrasa.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.youtube.YouTube
import com.onlinemadrasa.R
import com.onlinemadrasa.adapter.MainAdapter
import com.onlinemadrasa.utils.OnAlertShow
import com.onlinemadrasa.utils.Utils
import com.onlinemadrasa.utils.loadAdaptiveBanner

class HomeFragment : Fragment(), OnAlertShow {

    private lateinit var mainRcv: RecyclerView

    private var mYoutubeDataApi: YouTube? = null
    private val mJsonFactory = GsonFactory()
    private val mTransport = AndroidHttp.newCompatibleTransport()
    private lateinit var ad_rlay: RelativeLayout
    private var onAlertShow: OnAlertShow = this


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
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        ad_rlay = root.findViewById(R.id.ad_rlay)
        loadAdaptiveBanner(requireContext(), ad_rlay)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainRcv = view.findViewById(R.id.mainRcv)
        mainRcv.layoutManager = GridLayoutManager(context, 2)

        var list: ArrayList<String> = ArrayList()
        var arrayString = getArrayIDS()
        var splitValues = arrayString.split(",")
        for (value in splitValues) {
            list.add(value)
        }
        mainRcv.adapter = context?.let { MainAdapter(it, list, onAlertShow) }


        mYoutubeDataApi = YouTube.Builder(mTransport, mJsonFactory, null)
            .setApplicationName(resources.getString(R.string.app_name))
            .build()
    }

    override fun onAlertShow() {
        Utils.showToast(context, getString(R.string.no_internet))
    }

}