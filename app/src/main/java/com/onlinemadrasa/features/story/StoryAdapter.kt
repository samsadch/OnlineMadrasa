package com.onlinemadrasa.features.story

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.onlinemadrasa.R
import com.onlinemadrasa.model.StoryItem

class StoryAdapter(var context: Context, var list: ArrayList<StoryItem>) :
    RecyclerView.Adapter<StoryAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sroryRlay: RelativeLayout = itemView.findViewById(R.id.list_rlay)
        val titleText: TextView = itemView.findViewById(R.id.program_name_text)
        val contentText: TextView = itemView.findViewById(R.id.time_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item_story_title, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.titleText.text = item.partTitle
        holder.contentText.text = item.content
        holder.sroryRlay.setOnClickListener {
            val intent = Intent(context, StorydetailActivity::class.java)
            intent.putExtra("ITEM", item)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}