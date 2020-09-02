package com.onlinemadrasa

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.iid.FirebaseInstanceId
import com.onlinemadrasa.utils.Utils
import java.util.*


class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    var title: String? = null
    var body: String? = null
    val context: Context = this@HomeActivity
    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        MobileAds.initialize(
            this
        ) {

        }

        val testDeviceIds = Arrays.asList("37646F557B07826A794A8F1B5552F9A6")
        val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
        MobileAds.setRequestConfiguration(configuration)


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        if (intent.extras != null) {
            title = intent.getStringExtra("TITLE")
            body = intent.getStringExtra("MESSAGE")
            body?.let {
                if (!title.isNullOrEmpty()) {
                    Utils.showIosDialog(context, title, body)
                } else {
                    Utils.showIosDialog(context, getString(R.string.app_name), body)
                }
            }
        }

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_message, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        MobileAds.initialize(this@HomeActivity) {}
        mInterstitialAd = InterstitialAd(this@HomeActivity)
        mInterstitialAd.adUnitId = "ca-app-pub-3884484176623181/4695991624"
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        initFirebase()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_rate -> {
                shareApp()
                //launchMarket()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
        //return super.onOptionsItemSelected(item)
    }

    private fun shareApp() {
        try {
            if (mInterstitialAd.isLoaded) {
                mInterstitialAd.show()
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.")
            }

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(
                "https://play.google.com/store/apps/details?id=$packageName"
            )
            intent.setPackage("com.android.vending")
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Couldn't launch the Play Store", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    private fun launchMarket() {
        val uri: Uri = Uri.parse("market://details?id=$packageName")
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        try {
            startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Couldn't launch the Play Store", Toast.LENGTH_LONG).show()
        }
    }

    private fun initFirebase() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }
                // Get new Instance ID token
                val token = task.result?.token
                Log.d("TOKEN", token)
            })
    }

}