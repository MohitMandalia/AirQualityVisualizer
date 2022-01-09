package com.strings.airqualityvisualizer.data.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.TypedArrayUtils
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.strings.airqualityvisualizer.R
import com.strings.airqualityvisualizer.databinding.AirQualityListBinding
import com.strings.airqualityvisualizer.domain.model.AirQualityData

class AirQualityAdapter(private val clickListener : AirListClickListener) : ListAdapter<AirQualityData,RecyclerView.ViewHolder>(AirQualityDiffCallBack()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolderAirQuality.from(parent)
    }

    init {
        Log.i("Adapter","Called")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolderAirQuality ->{
                val airItem = getItem(position)
                holder.bind(airItem,clickListener)
            }
        }
    }

   class ViewHolderAirQuality private constructor(val binding: AirQualityListBinding) :RecyclerView.ViewHolder(binding.root){

        fun bind(item : AirQualityData, clickListener: AirListClickListener){
            binding.airData = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

       companion object{
            fun from(parent: ViewGroup): ViewHolderAirQuality{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AirQualityListBinding.inflate(layoutInflater,parent,false)
                return ViewHolderAirQuality(binding)
            }
       }
   }

}

class AirListClickListener(val clickListener: (city : String) -> Unit){
    fun onClick(airQualityData: AirQualityData) = clickListener(airQualityData.city)
}