package com.onlinemadrasa.ui.story

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.firebase.database.*
import com.onlinemadrasa.R
import com.onlinemadrasa.adapter.StoryTitlesAdapter
import com.onlinemadrasa.model.StoryModel
import com.onlinemadrasa.utils.Utils.showToast
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class StoryFragment : Fragment() {

    private var mDatabase: DatabaseReference? = null
    private lateinit var storyTitleAdapter: StoryTitlesAdapter
    private lateinit var story_rcv: RecyclerView
    val storyList = ArrayList<StoryModel>()
    private lateinit var progress: ProgressBar
    lateinit var mAdView: AdView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.story_fragment, container, false)
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        mAdView = view.findViewById(R.id.adView)
        story_rcv = view.findViewById(R.id.story_rcv)
        progress = view.findViewById(R.id.progress)
        storyTitleAdapter = StoryTitlesAdapter(requireContext(), storyList)
        story_rcv.layoutManager = LinearLayoutManager(requireContext())
        story_rcv.adapter = storyTitleAdapter
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("stories")
        mDatabase = FirebaseDatabase.getInstance().reference.child("stories")
        showProgress()

        mDatabase!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                storyList.clear()
                Log.d("SNAPSHOT", dataSnapshot.children.toString())
                for (postSnapshot in dataSnapshot.children) {
                    val user: StoryModel = postSnapshot.getValue(StoryModel::class.java)!!
                    storyList.add(user)
                }
                storyTitleAdapter.notifyDataSetChanged()
                //adapter.notifyDataSetChanged()
                hideProgress()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                showToast(requireContext(), databaseError.message)
                hideProgress()
            }
        })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

    fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    fun hideProgress() {
        progress.visibility = View.GONE
    }

}