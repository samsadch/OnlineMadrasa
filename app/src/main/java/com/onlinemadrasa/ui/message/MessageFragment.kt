package com.onlinemadrasa.ui.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.FirebaseDatabase
import com.onlinemadrasa.R
import com.onlinemadrasa.utils.Utils

class MessageFragment : Fragment() {

    companion object {
        fun newInstance() = MessageFragment()
    }

    var submitText: TextView? = null
    var comments_edit: EditText? = null
    var messageText: String? = null

    private lateinit var viewModel: MessageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.message_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        submitText = view.findViewById(R.id.submit_text)
        comments_edit = view.findViewById(R.id.comments_edit)
        submitText?.setOnClickListener {
            messageText = comments_edit?.text.toString()
            messageText?.let {
                sendMessage(messageText!!)
            }
            findNavController().popBackStack()
            Utils.showToast(requireContext(), getString(R.string.thanks_for_your_feedback))
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MessageViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun sendMessage(message: String) {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")
        myRef.push().setValue(message)
    }

}