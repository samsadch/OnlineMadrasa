package com.onlinemadrasa.ui.share

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.onlinemadrasa.R
import com.onlinemadrasa.utils.openWebPage


class ShareFragment : Fragment() {

    private var shareButton: Button? = null
    private lateinit var bottom_relativeLayout:RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_share, container, false)
        val mAdView: AdView = root.findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        val mAdView2: AdView = root.findViewById(R.id.adView2)
        val adRequest2 = AdRequest.Builder().build()
        mAdView2.loadAd(adRequest2)

        bottom_relativeLayout = root.findViewById(R.id.bottom_relativeLayout)

        bottom_relativeLayout.setOnClickListener {
           openWebPage(requireContext(),getString(R.string.smartschool_link))
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shareButton = view.findViewById(R.id.shareButton)
        shareButton!!.setOnClickListener {

            activity?.let { it1 ->
                ShareCompat.IntentBuilder.from(it1)
                    .setType("text/plain")
                    .setChooserTitle("Share Online Madrasa")
                    .setText("http://play.google.com/store/apps/details?id=" + requireActivity().packageName)
                    .startChooser()
            }
        }

    }



}