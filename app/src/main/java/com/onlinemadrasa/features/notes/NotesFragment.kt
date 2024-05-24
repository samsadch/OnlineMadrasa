package com.onlinemadrasa.features.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.onlinemadrasa.R
import com.onlinemadrasa.features.notes.NotesAdapter
import com.onlinemadrasa.utils.OnAlertShow
import com.onlinemadrasa.utils.Utils

class NotesFragment : Fragment(), OnAlertShow {

    lateinit var notesRcv: RecyclerView
    lateinit var notesAdapter: NotesAdapter
    lateinit var onAlertShow: OnAlertShow
    var list = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.notes_fragment, container, false)
        notesRcv = view.findViewById(R.id.notesRcv)
        onAlertShow = this
        notesRcv.layoutManager = GridLayoutManager(requireContext(), 2)
        notesAdapter = NotesAdapter(requireContext(), list, onAlertShow)
        notesRcv.adapter = notesAdapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.add("PL5kzoTlCQzkGLCKMpbIDddYeT03OrCPEY")
        list.add("PL5kzoTlCQzkEE52V2pMOvwJ6YjOuTPxwO")
        list.add("PL5kzoTlCQzkGwRP75MqEi28cI841LXo3P")
        notesAdapter.notifyDataSetChanged()
    }

    override fun onAlertShow() {
        Utils.showToast(context, getString(R.string.no_internet))
    }
}