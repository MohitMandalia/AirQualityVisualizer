package com.strings.airqualityvisualizer.data.adapter

import android.graphics.Color
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.strings.airqualityvisualizer.domain.model.AirQualityData
import kotlin.math.nextDown

@BindingAdapter("cityName")
fun TextView.setCityName(item: AirQualityData?){
    item?.let {
        text = item.city
    }
}

@BindingAdapter("airQuality")
fun TextView.setAirQuality(item: AirQualityData?){
    item?.let {

        text = (Math.round(item.aqi * 100) / 100.0).toString()
    }
}

@BindingAdapter("airColor")
fun TextView.setAirColor(item: AirQualityData?){
    item?.let {
        when (item.aqi) {
            in 0.0..50.0 -> {
                setTextColor(Color.rgb(0,153,0))
            }
            in 51.0..100.0 -> {
                setTextColor(Color.rgb(0,255,0))
            }
            in 101.0..200.0 -> {
                setTextColor(Color.rgb(204,204,0))
            }
            in 201.0..300.0 -> {
                setTextColor(Color.rgb(255,128,0))
            }
            in 301.0..400.0 -> {
                setTextColor(Color.rgb(204,0,0))
            }
            in 401.0..500.0 -> {
                setTextColor(Color.rgb(153,0,0))
            }
        }
    }
}



