package com.onlinemadrasa.ui.sylabus

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
import com.onlinemadrasa.adapter.SylabusAdapter
import com.onlinemadrasa.utils.loadAdaptiveBanner

class SylabusFragment : Fragment() {

    private var list = ArrayList<Int>()

    private lateinit var sylRcv: RecyclerView
    private lateinit var adapter: SylabusAdapter
    lateinit var adRlay: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.sylabus_fragment, container, false)
        sylRcv = view.findViewById(R.id.sylRcv)
        adRlay = view.findViewById(R.id.second_rlay)
        sylRcv.layoutManager = GridLayoutManager(context, 2)
        adapter = SylabusAdapter(requireContext(), list)
        sylRcv.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.add(R.drawable.syl_one)
        list.add(R.drawable.syl_two)
        list.add(R.drawable.syl_three)
        list.add(R.drawable.syl_four)
        list.add(R.drawable.syl_five)
        list.add(R.drawable.syl_six)
        list.add(R.drawable.syl_seven)
        list.add(R.drawable.syl_eight)
        list.add(R.drawable.syl_nine)
        list.add(R.drawable.syl_ten)
        list.add(R.drawable.syl_plus_one)
        list.add(R.drawable.syl_plus_two)
        adapter.notifyDataSetChanged()

        loadAdaptiveBanner(requireContext(), adRlay)
    }

}