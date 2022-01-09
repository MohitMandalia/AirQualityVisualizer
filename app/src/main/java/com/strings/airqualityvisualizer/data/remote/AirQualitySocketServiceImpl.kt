package com.strings.airqualityvisualizer.data.remote

import com.strings.airqualityvisualizer.data.remote.dto.AirQualityDataDto
import com.strings.airqualityvisualizer.domain.model.AirQualityData
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.websocket.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class AirQualitySocketServiceImpl(
    private val client: HttpClient
): AirQualitySocketService {

    private var socketSession : WebSocketSession? = null

    override suspend fun openSession(): ConnectionState {

        return try {
            socketSession = client.webSocketSession {
                url(AirQualitySocketService.BASE_URL)
            }
            if (socketSession?.isActive == true){
                ConnectionState.Connected("Connection")
            }else{
                ConnectionState.CannotConnect("Connection not established")
            }
        }catch (e: Exception){
            ConnectionState.Error("Unknown Error")
        }
    }

    override fun refreshData(): Flow<List<AirQualityData>> {
        return try{
            socketSession?.incoming
                ?.receiveAsFlow()
                ?.filter { it is Frame.Text }
                ?.map {
                    val json  = (it as? Frame.Text)?.readText() ?: ""
                    val listAirQualityDataDto = Json.decodeFromString<List<AirQualityDataDto>>(json)
                    listAirQualityDataDto.map { dto ->
                        dto.toAirQualityData()
                    }
                } ?: flow{}
        }catch(e : Exception){
            flow{}
        }

    }

    override suspend fun closeSession() {
        socketSession?.close()
    }
}

