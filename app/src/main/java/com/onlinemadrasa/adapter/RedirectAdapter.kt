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
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.onlinemadrasa.PdfViewActivity
import com.onlinemadrasa.R
import com.onlinemadrasa.utils.OnAlertShow


class RedirectAdapter(var context: Context, private var list: ArrayList<String>, var onAlertShow: OnAlertShow) :
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
        holder.itemTxv.text = "Class ${clas - 1}"

        holder.containerRlay.setOnClickListener {
            if (position==0) {
                val browserIntent =
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(context.getString(R.string.attendance_url))
                    )
                context!!.startActivity(browserIntent)
            } else if (position in 1..12) {
                try {
                    if (!isOnline(context)) {
                        isOnline = false
                        onAlertShow.onAlertShow()
                    }else {
                        /*isOnline = true
                        val intent = Intent(context, VideoListingActivity::class.java)
                        intent.putExtra("ITEM", item)
                        context.startActivity(intent)*/
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(context.getString(R.string.youtube_playlist_append) + item)
                        intent.setPackage("com.google.android.youtube")
                        context.startActivity(intent)
                    }
                }catch (e:Exception){
                    val browserIntent =Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(context.getString(R.string.youtube_playlist_append) + item)
                    )
                    context!!.startActivity(browserIntent)
                    Toast.makeText(context!!,"Please install Youtube for better Viewing",Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
            } else if(position==13){
                context!!.startActivity(Intent(context, PdfViewActivity::class.java))
            }
        }
        when (position) {
            0 -> {
                holder.itemImv.setBackgroundResource(R.drawable.ic_attendance)
                holder.itemTxv.text = context.getString(R.string.submit_attendance)
            }
            1 -> {
                try {
                    holder.itemImv.setBackgroundResource(R.drawable.ic_one)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            2 -> {
                try {
                    holder.itemImv.setBackgroundResource(R.drawable.ic_two)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            3 -> {
                try {
                    holder.itemImv.setBackgroundResource(R.drawable.ic_three)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            4 -> {
                try {
                    holder.itemImv.setBackgroundResource(R.drawable.ic_four)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            5 -> {
                try {
                    holder.itemImv.setBackgroundResource(R.drawable.ic_five)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            6 -> {
                try {
                    holder.itemImv.setBackgroundResource(R.drawable.ic_six)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            7 -> {
                try {
                    holder.itemImv.setBackgroundResource(R.drawable.ic_seven)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            8 -> {
                try {
                    holder.itemImv.setBackgroundResource(R.drawable.ic_eight)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            9 -> {
                try {
                    holder.itemImv.setBackgroundResource(R.drawable.ic_nine)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            10 -> {
                try {
                    holder.itemImv.setBackgroundResource(R.drawable.ic_ten)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            11 -> {
                try {
                    holder.itemImv.setBackgroundResource(R.drawable.ic_eleven)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            12 -> {
                try {
                    holder.itemImv.setBackgroundResource(R.drawable.ic_tweleve)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            13 -> {
                try {
                    holder.itemImv.setBackgroundResource(R.drawable.ic_announcment)
                }catch (e:Exception){
                    e.printStackTrace()
                }
                holder.itemTxv.text = "Circular"
            }
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