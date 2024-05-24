package com.onlinemadrasa.features.home

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
import com.onlinemadrasa.R
import com.onlinemadrasa.features.AttendanceActivity
import com.onlinemadrasa.utils.OnAlertShow
import com.onlinemadrasa.utils.Utils
import com.onlinemadrasa.utils.loadFromGlide
import com.onlinemadrasa.utils.loadImage

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
            holder.itemTxv.text = "Class ${clas - 4}"

            holder.containerRlay.setOnClickListener {
                val item = list[position]
                if (position == 0) {
                    try {
                        context.startActivity(Intent(context, AttendanceActivity::class.java))
                    } catch (e: Exception) {
                        val browserIntent =
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(context.getString(R.string.attendance_url))
                            )
                        context.startActivity(browserIntent)
                        e.printStackTrace()
                    }
                }
                /*else if (position in 1..13)*/ else {
                    try {
                        if (!isOnline(context)) {
                            isOnline = false
                            onAlertShow.onAlertShow()
                        } else {
                            isOnline = true
                            //TODO
                            /*val intent = Intent(context, VideoListingActivity::class.java)
                            intent.putExtra("ITEM", item)
                            context.startActivity(intent)*/
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
                }
            }
            when (position) {
                0 -> {
                    holder.itemTxv.text = context.getString(R.string.submit_attendance)
                    holder.itemImv.loadFromGlide(context, R.drawable.image_ten)
                }
                1 -> {
                    holder.itemTxv.text = context.getString(R.string.thilava)
                    holder.itemImv.loadFromGlide(context, R.drawable.thilava)
                }
                2 -> {
                    holder.itemTxv.text = context.getString(R.string.diffrently_abled)
                    holder.itemImv.loadFromGlide(context, R.drawable.image_one)
                }

                3 -> {
                    holder.itemTxv.text = context.getString(R.string.general_programs)
                    holder.itemImv.loadFromGlide(context, R.drawable.image_seven)
                }

                in 4..15 -> {
                    loadImage(context, position, holder.itemImv)
                }

                16 -> {
                    holder.itemTxv.text = context.getString(R.string.urdu)
                    holder.itemImv.loadFromGlide(context, R.drawable.image_three)
                }

                17 -> {
                    holder.itemTxv.text = context.getString(R.string.hanafi_fiqh)
                    holder.itemImv.loadFromGlide(context, R.drawable.image_one)
                }

                18 -> {
                    holder.itemTxv.text = context.getString(R.string.announcement)
                    holder.itemImv.loadFromGlide(context, R.drawable.image_six)
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

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}