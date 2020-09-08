package com.onlinemadrasa.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.onlinemadrasa.R
import com.onlinemadrasa.VideoListingActivity
import com.onlinemadrasa.utils.OnAlertShow
import com.onlinemadrasa.utils.Utils

class MainAdapter(
    var context: Context,
    private var list: ArrayList<String>,
    var onAlertShow: OnAlertShow
) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.list_item_cards, parent, false)
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
            val clas = position + 1
            holder.itemTxv.text = "Class ${clas - 2}"

            holder.containerRlay.setOnClickListener {
                val item = list[position]
                if (position == 0) {
                    try {
                        val browserIntent =
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(context.getString(R.string.attendance_url))
                            )
                        context.startActivity(browserIntent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } /*else if (position in 1..13)*/ else {
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
                        val browserIntent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(item)
                        )
                        context.startActivity(browserIntent)
                        Toast.makeText(
                            context,
                            "Please install Youtube for better Viewing",
                            Toast.LENGTH_LONG
                        ).show()
                        e.printStackTrace()
                    }
                } /*else if (position == 13) {
                    context.startActivity(Intent(context, PdfViewActivity::class.java))
                }*/
            }
            when (position) {
                0 -> {
                    Glide.with(context).load(R.drawable.image_ten).into(holder.itemImv);
                    //holder.itemImv.setBackgroundResource(R.drawable.image_ten)
                    holder.itemTxv.text = context.getString(R.string.submit_attendance)
                }
                1 -> {
                    try {
                        holder.itemTxv.text = context.getString(R.string.diffrently_abled)
                        Glide.with(context).load(R.drawable.image_one).into(holder.itemImv);
                        //holder.itemImv.setBackgroundResource(R.drawable.image_one)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                2 -> {
                    try {

                        Glide.with(context).load(R.drawable.image_two).into(holder.itemImv);
                        //holder.itemImv.setBackgroundResource(R.drawable.image_two)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                3 -> {
                    try {
                        Glide.with(context).load(R.drawable.image_three).centerInside()
                            .into(holder.itemImv);
                        //holder.itemImv.setBackgroundResource(R.drawable.image_three)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                4 -> {
                    try {
                        Glide.with(context).load(R.drawable.image_four).into(holder.itemImv);
                        //holder.itemImv.setBackgroundResource(R.drawable.image_four)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                5 -> {
                    try {
                        Glide.with(context).load(R.drawable.image_five).into(holder.itemImv);
                        //holder.itemImv.setBackgroundResource(R.drawable.image_five)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                6 -> {
                    try {
                        Glide.with(context).load(R.drawable.image_six).into(holder.itemImv);
                        //holder.itemImv.setBackgroundResource(R.drawable.image_six)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                7 -> {
                    try {
                        Glide.with(context).load(R.drawable.image_seven).into(holder.itemImv);
                        //holder.itemImv.setBackgroundResource(R.drawable.image_seven)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                8 -> {
                    try {
                        Glide.with(context).load(R.drawable.image_eight).into(holder.itemImv);
                        //holder.itemImv.setBackgroundResource(R.drawable.image_eight)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                9 -> {
                    try {
                        Glide.with(context).load(R.drawable.image_three).into(holder.itemImv);
                        //holder.itemImv.setBackgroundResource(R.drawable.image_three)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                10 -> {
                    try {
                        Glide.with(context).load(R.drawable.image_nine).into(holder.itemImv);
                        //holder.itemImv.setBackgroundResource(R.drawable.image_nine)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                11 -> {
                    try {
                        Glide.with(context).load(R.drawable.image_ten).into(holder.itemImv);
                        //holder.itemImv.setBackgroundResource(R.drawable.image_ten)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                12 -> {
                    try {
                        Glide.with(context).load(R.drawable.image_one).into(holder.itemImv);
                        //holder.itemImv.setBackgroundResource(R.drawable.image_one)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                13 -> {
                    try {
                        Glide.with(context).load(R.drawable.image_two).into(holder.itemImv)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                14 -> {
                    try {
                        Glide.with(context).load(R.drawable.image_nine).into(holder.itemImv)
                        holder.itemTxv.text = context.getString(R.string.announcement)
                        //holder.itemImv.setBackgroundResource(R.drawable.image_nine)
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
        val itemTxv: TextView = itemView.findViewById(R.id.itemTxv)
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