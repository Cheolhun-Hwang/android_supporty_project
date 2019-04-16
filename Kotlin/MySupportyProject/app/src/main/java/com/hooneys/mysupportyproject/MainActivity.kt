package com.hooneys.mysupportyproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ScrollView
import com.hooneys.mysupportyproject.MyWeather.WeatherView

class MainActivity : AppCompatActivity() {
    private val tag : String = "Main"
    private lateinit var scrollView : ScrollView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        addViewInScroll()
    }

    private fun addViewInScroll() {
        scrollView.addView(WeatherView(applicationContext))
    }

    private fun init() {
        Log.d(tag, "init...")
        scrollView = findViewById(R.id.main_scroll_view)
    }
}
