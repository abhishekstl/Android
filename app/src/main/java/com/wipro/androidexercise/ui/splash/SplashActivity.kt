package com.wipro.androidexercise.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.wipro.androidexercise.R
import com.wipro.androidexercise.ui.feeds.FeedsListActivity

class SplashActivity : AppCompatActivity() {


    private val SPLASHTIMEOUT : Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val i = Intent(this@SplashActivity, FeedsListActivity::class.java)
            startActivity(i)
            finish()
        }, SPLASHTIMEOUT)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }

    private fun hideSystemUI() {
        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

}