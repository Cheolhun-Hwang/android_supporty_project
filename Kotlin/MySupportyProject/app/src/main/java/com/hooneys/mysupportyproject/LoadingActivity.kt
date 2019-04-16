package com.hooneys.mysupportyproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log

class LoadingActivity : AppCompatActivity() {
    private val tag : String = "Loading"
    private val splashDelay : Long = 3000
    private val handler:Handler = Handler()
    private val runnable:Runnable = Runnable {
        startActivity(Intent(applicationContext, MainActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        Log.d(tag, "on Create...")
    }

    override fun onStart() {
        Log.d(tag, "on Start...")
        super.onStart()
        startLoading()
    }

    private fun startLoading() {
        handler.postDelayed(runnable, splashDelay)
    }

    override fun onStop() {
        Log.d(tag, "on Stop...")
        handler.removeCallbacks(runnable)
        super.onStop()
    }
}
