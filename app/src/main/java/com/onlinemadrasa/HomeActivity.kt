package com.onlinemadrasa

import android.app.Activity
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
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.onlinemadrasa.utils.Utils


class HomeActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    var title: String? = null
    var body: String? = null
    val context: Context = this@HomeActivity

    /*override fun getTheme(): Resources.Theme {
        val theme = super.getTheme()
        if (true) {
            theme.applyStyle(R.style.AppThemeGreen, true)
        }
        // you could also use a switch if you have many themes that could apply
        // you could also use a switch if you have many themes that could apply
        return theme
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        val testDeviceIds = listOf("E2E9731DE05D993168487E339C06DF13")


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
        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_gallery,
                R.id.nav_quran,
                R.id.nav_story,
                R.id.nav_notes, R.id.nav_pub,
                R.id.nav_syl,
                R.id.nav_samajam,
                R.id.nav_message,
                R.id.nav_slideshow,
                R.id.nav_about
            ), drawerLayout
        )
        //setupActionBarWithNavController(navController, appBarConfiguration)
        //navView.setupWithNavController(navController)

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(navView, navController)
        initFirebase()
    }

    override fun onNavigateUpFromChild(child: Activity?): Boolean {
        //return super.onNavigateUpFromChild(child)
        NavigationUI.navigateUp(navController, drawerLayout)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home, menu)
        if (null == getShareIntent().resolveActivity(this!!.packageManager)) {
            //hide menu item if not resolves
            menu?.findItem(R.id.action_rate)?.isVisible = false

        }
        return true
    }

    fun getShareIntent(): Intent {
        try {
            val uri: Uri = Uri.parse("http://play.google.com/store/apps/details?id=$packageName")
            val goToMarket = Intent(Intent.ACTION_VIEW, uri)
            goToMarket.addFlags(
                Intent.FLAG_ACTIVITY_NO_HISTORY or
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK
            )

            return goToMarket
        } catch (e: ActivityNotFoundException) {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://play.google.com/store/apps/details?id=$packageName")
            )
            return intent
        }


    }

    fun share() {
        startActivity(getShareIntent())
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_rate -> {
                share()
                //launchMarket()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
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
                Log.d("TOKEN", token.toString())
            })
    }

}