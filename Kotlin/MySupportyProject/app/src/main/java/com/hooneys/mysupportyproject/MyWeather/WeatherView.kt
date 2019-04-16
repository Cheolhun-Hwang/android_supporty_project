package com.hooneys.mysupportyproject.MyWeather

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.hooneys.mysupportyproject.R
import com.hooneys.mysupportyproject.R.layout.view_weather

class WeatherView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    init {
        LayoutInflater.from(context).inflate(R.layout.view_weather, this, true)
    }
}