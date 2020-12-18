package com.onlinemadrasa.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.onlinemadrasa.R

internal inline fun loadImage(context: Context, position: Int, imageView: ImageView) {
    try {
        Glide.with(context).load(getImageArray()[position]).centerInside()
            .into(imageView);
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

private fun getImageArray(): Array<Int> {
    return arrayOf(
        0, 0, 0, 0,
        R.drawable.image_three,
        R.drawable.image_four,
        R.drawable.image_five,
        R.drawable.image_six,
        R.drawable.image_seven,
        R.drawable.image_eight,
        R.drawable.image_nine,
        R.drawable.image_ten,
        R.drawable.image_one,
        R.drawable.image_two,
        R.drawable.image_three,
        R.drawable.image_five
    )
}

internal fun ImageView.loadFromGlide(context: Context, resourceId: Int) {
    try {
        Glide.with(context).load(resourceId).centerInside()
            .into(this)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}