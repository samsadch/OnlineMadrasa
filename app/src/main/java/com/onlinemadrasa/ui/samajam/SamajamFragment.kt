package com.onlinemadrasa.ui.samajam

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.onlinemadrasa.R
import com.onlinemadrasa.utils.loadAdaptiveBanner

class SamajamFragment : Fragment() {

    lateinit var adRlay: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.samajam_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adRlay = view.findViewById(R.id.ad_rlay)

    }

    override fun onResume() {
        super.onResume()
        loadAdaptiveBanner(requireContext(), adRlay)
    }

}