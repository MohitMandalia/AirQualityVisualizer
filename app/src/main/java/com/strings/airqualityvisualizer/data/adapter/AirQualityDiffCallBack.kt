package com.strings.airqualityvisualizer.data.adapter

import androidx.recyclerview.widget.DiffUtil
import com.strings.airqualityvisualizer.domain.model.AirQualityData

class AirQualityDiffCallBack : DiffUtil.ItemCallback<AirQualityData>() {
    override fun areItemsTheSame(oldItem: AirQualityData, newItem: AirQualityData): Boolean {
        return oldItem.city == newItem.city
    }

    override fun areContentsTheSame(oldItem: AirQualityData, newItem: AirQualityData): Boolean {
        return oldItem == newItem
    }

}