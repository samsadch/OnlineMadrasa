package com.onlinemadrasa.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.onlinemadrasa.R

class SurahAdapter(val context: Context, private var listItemsTxt: List<String>) : BaseAdapter() {


    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: DropRowHolder
        if (convertView == null) {
            view = mInflater.inflate(R.layout.drop_down_menu, parent, false)
            vh = DropRowHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as DropRowHolder
        }

        val params = view.layoutParams
        view.layoutParams = params

        vh.label.text = listItemsTxt[position]

        return view
    }

    private class DropRowHolder(row: View?) {

        val label: TextView = row?.findViewById(R.id.txtDropDownLabel) as TextView

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemRowHolder
        if (convertView == null) {
            view = mInflater.inflate(R.layout.dropdown, parent, false)
            vh = ItemRowHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemRowHolder
        }

        val params = view.layoutParams
        view.layoutParams = params

        vh.label.text = listItemsTxt[position]

        return view
    }

    override fun getItem(position: Int): Any? {

        return null

    }

    override fun getItemId(position: Int): Long {

        return 0

    }

    override fun getCount(): Int {
        return listItemsTxt.size
    }

    private class ItemRowHolder(row: View?) {

        val label: TextView = row?.findViewById(R.id.txtDropDownLabel) as TextView

    }
}