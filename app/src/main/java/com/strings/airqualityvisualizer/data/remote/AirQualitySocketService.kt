package com.strings.airqualityvisualizer.data.remote

import com.strings.airqualityvisualizer.data.remote.dto.AirQualityDataDto
import com.strings.airqualityvisualizer.domain.model.AirQualityData
import kotlinx.coroutines.flow.Flow


interface AirQualitySocketService {

    suspend fun openSession() : ConnectionState

    fun refreshData(): Flow<List<AirQualityData>>

    suspend fun  closeSession()

    companion object{
        const val BASE_URL = "ws://city-ws.herokuapp.com"
    }

}