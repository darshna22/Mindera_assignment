package com.mindera.rocketscience.retofit

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

/*
* call: suspend () -> Response<T>
Here call is variable for anonymous suspend function which is
* returning Response<T> of type T generic
* */
fun <T> result(call: suspend () -> Response<T>): Flow<NetworkResult<T?>> = flow {

    emit(NetworkResult.Loading)

    try {
        val c = call()
        c.let { it ->
            if (it.isSuccessful) {
                emit(NetworkResult.Success(it.body()))
            } else {
                it.errorBody().let {
                    it?.close()
                    emit(NetworkResult.Error(it.toString()))

                }
            }
        }
    } catch (t: Throwable) {
        emit(NetworkResult.Error(t.message.toString()))

    }
}

