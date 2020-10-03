package com.onlinemadrasa.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.onlinemadrasa.R
import com.onlinemadrasa.model.StoryModel
import com.onlinemadrasa.ui.story.StoryActivity

class StoryTitlesAdapter(var context: Context, var list: ArrayList<StoryModel>) :
    RecyclerView.Adapter<StoryTitlesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sroryRlay: RelativeLayout = itemView.findViewById(R.id.srory_rlay)
        val titleText: TextView = itemView.findViewById(R.id.title_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item_title, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.titleText.text = item.storyTitle
        holder.sroryRlay.setOnClickListener {
            val intent = Intent(context, StoryActivity::class.java)
            intent.putExtra("ITEM",item)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}