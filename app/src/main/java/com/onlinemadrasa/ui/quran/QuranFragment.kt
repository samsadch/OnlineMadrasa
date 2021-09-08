package com.onlinemadrasa.ui.quran

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.github.barteksc.pdfviewer.PDFView
import com.onlinemadrasa.R
import com.onlinemadrasa.adapter.SurahAdapter
import com.onlinemadrasa.utils.PrefManager


class QuranFragment : Fragment() {

    private lateinit var stringBuilder: StringBuilder
    lateinit var pdfView: PDFView
    lateinit var prefM: PrefManager
    lateinit var juzSpinner: Spinner

    val pagenum = intArrayOf(
        0, 1, 44, 68, 94, 114, 135, 159, 168, 186,
        198, 211, 224, 230, 236, 241, 254, 265, 276, 283,
        293, 301, 310, 318, 328, 334, 344, 353, 363, 370,
        376, 380, 382, 392, 398, 403, 309, 416, 421, 430,
        438, 444, 450, 456, 459, 463, 467, 471, 476, 478,
        481, 484, 487, 489, 492, 495, 498, 503, 506, 509,
        512, 514, 515, 517, 519, 521, 523, 525, 528, 530,
        532, 533, 536, 537, 539, 541, 543, 544, 546, 547,
        549, 550, 551, 552, 553, 554, 555, 556, 556, 558,
        558, 559, 560, 560, 561, 561, 562, 562, 563, 563,
        564, 564, 565, 565, 565, 566, 566, 566, 567, 567,
        567, 568, 568, 568
    )

    val names = listOf(
        "Al Fathiha",
        "Al Baqara",
        "Aalu Imran",
        "An Nisa",
        "Al Ma'ida",
        "Al An'am",
        "Al A'raf",
        "Al Anfal",
        "Al Thawba",
        "Yunus",
        "Hud",
        "Yusuf",
        "Ar Ra'd",
        "Ibrahim",
        "Al Hijr",
        "An Nahl",
        "Bani Isra'il",
        "Al Kahf",
        "Maryam",
        "Thwaha",
        "Al Anbiya",
        "Al Hajj",
        "Al Mu'minun",
        "An Nur",
        "Al-Furqan",
        "Ash Shu'ara",
        "An Naml",
        "Al Qasas",
        "Al Ankabut",
        "Ar Rum",
        "Luqman",
        "As Sajdah",
        "Al Ahzab",
        "Al Saba",
        "Al Fatir ",
        "Yaseen",
        "As Saffat",
        "Swad",
        "Az Zumar",
        "Gafir",
        "Fussila",
        "Ash Shura",
        "Az Zukhruf",
        "Ad Dukhan ",
        "Al Jathiyah",
        "Al Ahqaf",
        "Muhammad",
        "Al Fath",
        "Al Hujurat",
        "Qaf",
        "Ad Dhariyath",
        "At Tur",
        "An Najm",
        "Al Qamar",
        "Ar Rahman",
        "Al Waqi'ah",
        "Al Hadid",
        "Al Mujadilah",
        "Al Hashr",
        "Al Mumtahanah",
        "As Saff",
        "Al Jumu'ah",
        "Al Munafiqun",
        "At Taghabun",
        "At Talaq",
        "At Tahrim",
        "Al Mulk",
        "Al Qalam",
        "Al Haqqah ",
        "Al Ma'arij",
        "Nuh",
        "Al Jinn ",
        "Al Muzzammil",
        "Al Muddaththir",
        "Al Qiyamah",
        "Al Insan ",
        "Al Mursalat ",
        "An Naba",
        "An Nazi'at",
        "Abasa",
        "At Takwir",
        "Al Infitar",
        "At Tatfif",
        "Al Inshiqaq",
        "Al Buruj",
        "At Tariq ",
        "Al A'la",
        "Al Ghashiyah",
        "Al Fajr ",
        "Al Balad ",
        "Ash Shams ",
        "Al Lail ",
        "Ad Duha ",
        "Al Inshirah ",
        "At Tin ",
        "Al Alaq ",
        "Al Qadr",
        "Al Bayyinah ",
        "Al Zilzal ",
        "Al Adiyat",
        "Al Qari'ah ",
        "At Takathur",
        "Al Asr ",
        "Al Humazah",
        "Al Fil ",
        "Al Quraish ",
        "Al Ma'un",
        "Al Kauthar",
        "Al Kafirun",
        "An Nasr",
        "Al Lahab ",
        "Al Ikhlas",
        "Al Falaq",
        "An Nas"
    )


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
        val view = inflater.inflate(R.layout.exam_updates_fragment, container, false)
        juzSpinner = view.findViewById(R.id.juz_spinner)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            stringBuilder = StringBuilder()
            stringBuilder.append("pdf/quran_ar_full.pdf")
            prefM = PrefManager(requireContext())
            pagenm = prefM.getPage()
            this.pdfView = view.findViewById(R.id.pdfview)
            this.pdfView.fromAsset(stringBuilder.toString()).defaultPage(pagenm).load()

            var list = ArrayList<Int>()
            for (i in 1..30) {
                list.add(i)
            }

            val adapter =
                SurahAdapter(requireContext(), names)
            juzSpinner.adapter = adapter


            // Class Spinner implementing onItemSelectedListener

            // Class Spinner implementing onItemSelectedListener
            juzSpinner.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    goToPage(pagenum[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // can leave this empty
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun goToPage(page: Int) {
        pdfView.fromAsset(stringBuilder.toString()).defaultPage(page).load()
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