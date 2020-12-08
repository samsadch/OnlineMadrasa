package com.onlinemadrasa.ui.share

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import com.onlinemadrasa.R
import com.onlinemadrasa.utils.loadAdaptiveBanner


class ShareFragment : Fragment() {

    private var shareButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_share, container, false)
        val relativeLayout = root.findViewById<RelativeLayout>(R.id.relativeLayout)
        loadAdaptiveBanner(requireContext(), relativeLayout)
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