package com.onlinemadrasa.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

inline fun openWebPage(context: Context, url: String) {
    var webpage = Uri.parse(url)
    if (!url.startsWith("http://") && !url.startsWith("https://")) {
        webpage = Uri.parse("http://$url")
    }
    val intent = Intent(Intent.ACTION_VIEW, webpage)
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    }
}