package com.strings.airqualityvisualizer.di

import com.strings.airqualityvisualizer.data.remote.AirQualitySocketService
import com.strings.airqualityvisualizer.data.remote.AirQualitySocketServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.time.withTimeout
import kotlinx.coroutines.withTimeout
import java.time.Duration
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient{
        return HttpClient(CIO){
            install(Logging)
            install(WebSockets){
            }
            install(ContentNegotiation){
                json()
            }
        }
    }

    @Provides
    @Singleton
    fun provideAirQualitySocketService(client: HttpClient) : AirQualitySocketService{
        return AirQualitySocketServiceImpl(client)
    }

}