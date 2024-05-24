package com.onlinemadrasa.features.message

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase
import com.onlinemadrasa.R
import com.onlinemadrasa.model.PostComment
import com.onlinemadrasa.utils.Utils

class MessageFragment : Fragment() {

    private var submitText: TextView? = null
    private var commentsEdit: EditText? = null
    private var nameEdit: EditText? = null
    private var contactEdit: EditText? = null
    private var messageText: String? = null
    private var nameText: String? = null
    private var contactText: String? = null

    private var clicked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.message_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        submitText = view.findViewById(R.id.submit_text)
        commentsEdit = view.findViewById(R.id.comments_edit)
        nameEdit = view.findViewById(R.id.name_edit)
        contactEdit = view.findViewById(R.id.contact_edit)

        submitText?.setOnClickListener {
            clicked = true
            messageText = commentsEdit?.text.toString()
            contactText = contactEdit?.text.toString()
            nameText = nameEdit?.text.toString()
            if (!messageText.isNullOrEmpty()) {
                messageText?.let {
                    nameText?.let {
                        sendMessage(messageText!!, nameText!!, contactText!!)
                    }
                }
            } else {
                Utils.showIosDialog(requireContext(), "Please enter your feedback")
            }
            //findNavController().popBackStack()
        }
    }

    private fun sendMessage(message: String, nameText: String, contact: String) {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")
        val postComment = PostComment(nameText, message, contact)
        myRef.push().setValue(postComment)
        Utils.showToast(requireContext(), getString(R.string.thanks_for_your_feedback))
        commentsEdit?.setText("")
        nameEdit?.setText("")
        contactEdit?.setText("")
    }

    override fun onPause() {
        closeKeyboard()
        super.onPause()
    }

    private fun closeKeyboard() {
        val view = requireActivity().currentFocus
        if (view != null) {
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}