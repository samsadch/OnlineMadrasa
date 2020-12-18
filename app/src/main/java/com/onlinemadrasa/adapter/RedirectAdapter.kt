package com.onlinemadrasa.adapter

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
import com.onlinemadrasa.AttendanceActivity
import com.onlinemadrasa.R
import com.onlinemadrasa.utils.OnAlertShow
import com.onlinemadrasa.utils.loadFromGlide
import com.onlinemadrasa.utils.loadImage


class RedirectAdapter(
    var context: Context,
    private var list: ArrayList<String>,
    var onAlertShow: OnAlertShow
) :
    RecyclerView.Adapter<RedirectAdapter.ViewHolder>() {

    private var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.list_item_cards, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    var isOnline = false

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        val clas = position + 1
        holder.itemTxv.text = "Class ${clas - 4}"

        holder.containerRlay.setOnClickListener {
            if (position == 0) {
                try {
                    context.startActivity(Intent(context, AttendanceActivity::class.java))
                } catch (e: Exception) {
                    e.printStackTrace()
                    val browserIntent =
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(context.getString(R.string.attendance_url))
                        )
                    context.startActivity(browserIntent)
                }

            } else /*if (position in 1..12)*/ {
                try {
                    if (!isOnline(context)) {
                        isOnline = false
                        onAlertShow.onAlertShow()
                    } else {
                        /*isOnline = true
                        val intent = Intent(context, VideoListingActivity::class.java)
                        intent.putExtra("ITEM", item)
                        context.startActivity(intent)*/
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data =
                            Uri.parse(context.getString(R.string.youtube_playlist_append) + item)
                        intent.setPackage("com.google.android.youtube")
                        context.startActivity(intent)
                    }
                } catch (e: Exception) {
                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(context.getString(R.string.youtube_playlist_append) + item)
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
                Glide.with(context).load(R.drawable.image_ten).centerInside().into(holder.itemImv);
                holder.itemTxv.text = context.getString(R.string.submit_attendance)
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
                holder.itemImv.loadFromGlide(context, R.drawable.image_six)
            }
            17 -> {
                holder.itemTxv.text = context.getString(R.string.hanafi_fiqh)
                holder.itemImv.loadFromGlide(context, R.drawable.image_one)
            }

            18 -> {
                holder.itemTxv.text = context.getString(R.string.announcement)
                holder.itemImv.loadFromGlide(context, R.drawable.image_four)
            }
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
