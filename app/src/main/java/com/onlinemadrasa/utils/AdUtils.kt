package com.onlinemadrasa.utils

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.google.android.gms.ads.*
import com.onlinemadrasa.R
import java.util.*


internal inline fun getAdSize(activity: Activity, container: View): AdSize {
    val display = activity.windowManager.defaultDisplay
    val outMetrics = DisplayMetrics()
    display.getMetrics(outMetrics)

    val density = outMetrics.density

    var adWidthPixels = container.width.toFloat()
    if (adWidthPixels == 0f) {
        adWidthPixels = outMetrics.widthPixels.toFloat()
    }

    val adWidth = (adWidthPixels / density).toInt()
    return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
        activity,
        adWidth
    )
}

internal inline fun loadAdaptiveBanner(
    context: Context,
    container: RelativeLayout
) {
    container.visibility = View.GONE
    val adView = AdView(context)
    container.addView(adView)
    adView.adUnitId = context.getString(R.string.banner_unit_id)
    adView.adSize = getAdSize(context as Activity, container)
    val adRequest = AdRequest
        .Builder().build()
    adView.loadAd(adRequest)

    adView.adListener = object : AdListener() {
        override fun onAdLoaded() {
            super.onAdLoaded()
            container.visibility = View.VISIBLE
        }

        override fun onAdFailedToLoad(p0: LoadAdError?) {
            super.onAdFailedToLoad(p0)
            Log.d("ADS", p0?.message)
        }
    }

}

internal inline fun loadAdaptiveBanner(
    context: Context,
    container: FrameLayout
) {

    val adView = AdView(context)
    container.addView(adView)
    adView.adUnitId = context.getString(R.string.banner_unit_id)
    adView.adSize = getAdSize(context as Activity, container)

    val adRequest = AdRequest
        .Builder().build()
    adView.loadAd(adRequest)
}

internal inline fun loadAdaptiveBanner(
    context: Context,
    container: LinearLayout,
    adSize: AdSize
) {
    val adView = AdView(context)
    container.addView(adView)
    adView.adUnitId = context.getString(R.string.banner_unit_id)
    adView.adSize = adSize
    val adRequest = AdRequest
        .Builder().build()
    adView.loadAd(adRequest)
}