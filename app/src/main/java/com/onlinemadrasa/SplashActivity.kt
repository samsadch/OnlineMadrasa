package com.onlinemadrasa

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private fun getSplashScreenDuration() = 2000L

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        try {
            setContentView(R.layout.activity_splash)
            MobileAds.initialize(this) {}
            scheduleSplashScreen()
            firebaseAnalytics = Firebase.analytics
        } catch (e: Exception) {
            splashImv.setImageResource(R.mipmap.ic_quran)
            scheduleSplashScreen()
            e.printStackTrace()
        }
    }

    private fun scheduleSplashScreen() {
        val splashScreenDuration = getSplashScreenDuration()
        Handler().postDelayed(
            {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            },
            splashScreenDuration
        )
    }

}