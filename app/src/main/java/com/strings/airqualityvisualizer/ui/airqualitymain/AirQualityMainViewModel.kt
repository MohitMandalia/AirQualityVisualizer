package com.strings.airqualityvisualizer.ui.airqualitymain

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strings.airqualityvisualizer.data.remote.AirQualitySocketService
import com.strings.airqualityvisualizer.data.remote.ConnectionState
import com.strings.airqualityvisualizer.domain.model.AirQualityData
import com.strings.airqualityvisualizer.domain.model.AirQualityDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.android.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AirQualityMainViewModel @Inject constructor(
    private val airQualitySocketService: AirQualitySocketService,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    private var _airQualityDataList = MutableStateFlow(AirQualityDataState())
    val airQualityDataList : StateFlow<AirQualityDataState> = _airQualityDataList

    private val _toastEvent = MutableSharedFlow<String>()
    val  toastEvent = _toastEvent.asSharedFlow()

    private val _navigateToChartEvent = MutableSharedFlow<String>()
    val  navigateToChartEvent = _navigateToChartEvent.asSharedFlow()


    fun onNavigateToChart(city: String){
        viewModelScope.launch {
            _navigateToChartEvent.emit(city)
        }

    }

     fun connectToSocket(){
        viewModelScope.launch {
            val result = airQualitySocketService.openSession()
            when(result){
                is ConnectionState.Connected -> {
                    airQualitySocketService.refreshData()
                        .onEach { airDataList ->
                            _airQualityDataList.value = airQualityDataList.value.copy(
                                airQualityDataList = airDataList.sortedBy { it.city })
                            delay(30000)

                        }.launchIn(viewModelScope)


                }
                is ConnectionState.CannotConnect ->_toastEvent.emit(result.message.toString())

                else -> _toastEvent.emit(result.message.toString())
            }
        }
    }




    fun disconnect(){
        viewModelScope.launch {
            airQualitySocketService.closeSession()
        }
    }

    override fun onCleared() {
        super.onCleared()
        disconnect()

    }

}


