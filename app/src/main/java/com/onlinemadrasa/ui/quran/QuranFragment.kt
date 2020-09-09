package com.onlinemadrasa.ui.exam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.barteksc.pdfviewer.PDFView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.onlinemadrasa.R
import com.onlinemadrasa.utils.PrefManager

class QuranFragment : Fragment() {

    lateinit var pdfView: PDFView
    lateinit var prefM: PrefManager

    var pagenm = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefM = PrefManager(requireContext())
        pagenm = savedInstanceState?.getInt("PAGE") ?: 0
        if (pagenm > 0) {
            prefM.savePage(pagenm)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.exam_updates_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            val stringBuilder = StringBuilder()
            stringBuilder.append("pdf/quran_ar_full.pdf")
            prefM = PrefManager(requireContext())
            pagenm = prefM.getPage()
            this.pdfView = view.findViewById(R.id.pdfview)
            this.pdfView.fromAsset(stringBuilder.toString()).defaultPage(pagenm).load()


            val mAdView: AdView = view.findViewById(R.id.adView)
            val adRequest = AdRequest.Builder().build()
            mAdView.loadAd(adRequest)


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt("PAGE", pagenm)
    }

    override fun onStop() {
        val bundle = Bundle()
        bundle.putInt("PAGE", pagenm)
        onSaveInstanceState(bundle)
        super.onStop()
    }

}