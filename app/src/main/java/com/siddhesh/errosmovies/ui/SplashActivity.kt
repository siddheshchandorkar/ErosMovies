package com.siddhesh.errosmovies.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.example.erostest.MainActivity
import com.siddhesh.errosmovies.R
import com.squareup.picasso.Picasso
import android.app.ActivityOptions



class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        Picasso.with(this@SplashActivity)
            .load("https://image.tmdb.org/t/p/original/ybHM7tXJ96sXf86wAq5orGz1xET.jpg")
            .into(findViewById<ImageView>(R.id.iv_splash))

        Handler().postDelayed({
            val options = ActivityOptions.makeSceneTransitionAnimation(this)

            startActivity(Intent(this, MainActivity::class.java),options.toBundle());
            finish()
        }, 3000)
    }
}