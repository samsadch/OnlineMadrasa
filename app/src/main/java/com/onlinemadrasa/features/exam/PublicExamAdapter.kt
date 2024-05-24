package com.onlinemadrasa.features.exam

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.onlinemadrasa.R
import com.onlinemadrasa.features.video.VideoListingActivity
import com.onlinemadrasa.utils.OnAlertShow
import com.onlinemadrasa.utils.Utils

class PublicExamAdapter(
    var context: Context,
    private var list: ArrayList<String>,
    var onAlertShow: OnAlertShow
) :
    RecyclerView.Adapter<PublicExamAdapter.ViewHolder>() {

    private var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.list_item_sylabus, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    var isOnline = false

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            holder.containerRlay.setOnClickListener {
                val item = list[position]
                try {
                    if (!isOnline(context)) {
                        isOnline = false
                        onAlertShow.onAlertShow()
                    } else {
                        isOnline = true
                        val intent = Intent(context, VideoListingActivity::class.java)
                        intent.putExtra("ITEM", item)
                        context.startActivity(intent)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            when (position) {
                0 -> {
                    Glide.with(context).load(R.drawable.ic_five).into(holder.itemImv)
                }
                1 -> {
                    try {
                        Glide.with(context).load(R.drawable.ic_seven).into(holder.itemImv)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                2 -> {
                    try {
                        Glide.with(context).load(R.drawable.ic_ten).into(holder.itemImv);
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                3 -> {
                    try {
                        Glide.with(context).load(R.drawable.ic_tweleve).into(holder.itemImv);
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

            }
        } catch (e: Exception) {
            Utils.showIosDialog(context, e.message)
            e.printStackTrace()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImv: ImageView = itemView.findViewById(R.id.itemImv)
        val containerRlay: RelativeLayout = itemView.findViewById(R.id.containerRlay)
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}