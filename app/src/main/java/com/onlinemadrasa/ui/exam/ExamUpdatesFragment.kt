package com.onlinemadrasa.ui.exam

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.onlinemadrasa.R

class ExamUpdatesFragment : Fragment() {

    companion object {
        fun newInstance() = ExamUpdatesFragment()
    }

    private lateinit var viewModel: ExamUpdatesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.exam_updates_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ExamUpdatesViewModel::class.java)
    }

}