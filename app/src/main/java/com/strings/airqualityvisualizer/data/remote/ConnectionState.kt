package com.strings.airqualityvisualizer.data.remote

sealed class ConnectionState(val message : String? =null){
    class Connected(message: String) : ConnectionState(message)
    class CannotConnect(message: String) : ConnectionState(message)
    class Error(message: String) : ConnectionState(message)
}