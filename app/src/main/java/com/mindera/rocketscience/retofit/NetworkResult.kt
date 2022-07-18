package com.mindera.rocketscience.retofit

sealed class NetworkResult<out R>{
    data class Success<T>(val data:T): NetworkResult<T>()
    data class Error(val message:String): NetworkResult<Nothing>()
    object Loading: NetworkResult<Nothing>()

}
