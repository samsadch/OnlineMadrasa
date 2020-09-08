package com.onlinemadrasa.adapter

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
import com.onlinemadrasa.ui.sylabus.SylabusItemActivity
import com.onlinemadrasa.utils.Utils

class SylabusAdapter(
    var context: Context,
    private var list: ArrayList<Int>
) :
    RecyclerView.Adapter<SylabusAdapter.ViewHolder>() {

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
            val item = list[position]

            holder.containerRlay.setOnClickListener {
                val item = list[position]
                try {
                    val intent = Intent(context, SylabusItemActivity::class.java)
                    intent.putExtra("image_id", item)
                    context.startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            when (position) {
                0 -> {
                    Glide.with(context).load(R.drawable.ic_one).into(holder.itemImv)

                }
                1 -> {
                    try {
                        Glide.with(context).load(R.drawable.ic_two).into(holder.itemImv)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                2 -> {
                    try {

                        Glide.with(context).load(R.drawable.ic_three).into(holder.itemImv);
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                3 -> {
                    try {
                        Glide.with(context).load(R.drawable.ic_four).centerInside()
                            .into(holder.itemImv);
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                4 -> {
                    try {
                        Glide.with(context).load(R.drawable.ic_five).into(holder.itemImv)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                5 -> {
                    try {
                        Glide.with(context).load(R.drawable.ic_six).into(holder.itemImv)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                6 -> {
                    try {
                        Glide.with(context).load(R.drawable.ic_seven).into(holder.itemImv);
                        //holder.itemImv.setBackgroundResource(R.drawable.image_six)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                7 -> {
                    try {
                        Glide.with(context).load(R.drawable.ic_eight).into(holder.itemImv)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                8 -> {
                    try {
                        Glide.with(context).load(R.drawable.ic_nine).into(holder.itemImv)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                9 -> {
                    try {
                        Glide.with(context).load(R.drawable.ic_ten).into(holder.itemImv)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                10 -> {
                    try {
                        Glide.with(context).load(R.drawable.ic_eleven).into(holder.itemImv)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                11 -> {
                    try {
                        Glide.with(context).load(R.drawable.ic_tweleve).into(holder.itemImv)
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