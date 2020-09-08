package com.onlinemadrasa.ui.share

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.onlinemadrasa.R


class ShareFragment : Fragment() {

    private var shareButton: Button? = null

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
            /*val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://play.google.com/store/apps/details?id=com.onlinemadrasa")
            startActivity(intent)*/
        }

    }
}