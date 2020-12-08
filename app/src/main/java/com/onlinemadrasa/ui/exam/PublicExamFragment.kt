package com.onlinemadrasa.ui.exam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.onlinemadrasa.R
import com.onlinemadrasa.adapter.PublicExamAdapter
import com.onlinemadrasa.utils.OnAlertShow
import com.onlinemadrasa.utils.Utils
import com.onlinemadrasa.utils.loadAdaptiveBanner

class PublicExamFragment : Fragment(), OnAlertShow {
    lateinit var examRcv: RecyclerView
    var list = ArrayList<String>()
    lateinit var adapter: PublicExamAdapter
    lateinit var onAlertShow: OnAlertShow
    private lateinit var second_rlay: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_public_exam, container, false)
        examRcv = view.findViewById(R.id.examRcv)
        second_rlay = view.findViewById(R.id.second_rlay)
        loadAdaptiveBanner(requireContext(), second_rlay)
        onAlertShow = this
        examRcv.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = PublicExamAdapter(requireContext(), list, onAlertShow)
        examRcv.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adRequest = AdRequest.Builder().build()

        list.add("PLEvR8ubmDvQ2q_LkLKG3l6WpAhvrmrOZn")
        list.add("PLEvR8ubmDvQ3vgBcjEuTS0J1U89xWuvGm")
        list.add("PLEvR8ubmDvQ0GMqpptdtAS9N0zsU-T-Wn")
        list.add("PLEvR8ubmDvQ0aoybEKEusGTVodrfraHa7")
        adapter.notifyDataSetChanged()
    }

    override fun onAlertShow() {
        Utils.showToast(context, getString(R.string.no_internet))
    }

}