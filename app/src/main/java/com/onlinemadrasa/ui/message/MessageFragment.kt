package com.onlinemadrasa.ui.message

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.*
import com.google.firebase.database.FirebaseDatabase
import com.onlinemadrasa.R
import com.onlinemadrasa.model.PostComment
import com.onlinemadrasa.utils.Utils

class MessageFragment : Fragment() {

    companion object {
        fun newInstance() = MessageFragment()
    }

    var submitText: TextView? = null
    var comments_edit: EditText? = null
    var name_edit: EditText? = null
    var messageText: String? = null
    var nameText: String? = null

    private lateinit var viewModel: MessageViewModel

    private lateinit var mInterstitialAd: InterstitialAd

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
        comments_edit = view.findViewById(R.id.comments_edit)
        name_edit = view.findViewById(R.id.name_edit)

        MobileAds.initialize(requireContext()) {}
        mInterstitialAd = InterstitialAd(requireContext())
        mInterstitialAd.adUnitId = "ca-app-pub-3884484176623181/4695991624"

        mInterstitialAd.loadAd(AdRequest.Builder().build())

        submitText?.setOnClickListener {
            clicked = true
            messageText = comments_edit?.text.toString()
            nameText = name_edit?.text.toString()
            messageText?.let {
                nameText?.let {
                    sendMessage(messageText!!, nameText!!)
                }
            }
            //findNavController().popBackStack()

            if (mInterstitialAd.isLoaded) {
                mInterstitialAd.show()
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.")
            }

        }

        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                if (clicked) {
                    findNavController().popBackStack()
                }
            }

            override fun onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                findNavController().popBackStack()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MessageViewModel::class.java)
    }

    fun sendMessage(message: String, nameText: String) {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")
        var postComment = PostComment(nameText, message)
        myRef.push().setValue(postComment)
        Utils.showToast(requireContext(), getString(R.string.thanks_for_your_feedback))
        comments_edit?.setText("")
        name_edit?.setText("")
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.")
        }
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