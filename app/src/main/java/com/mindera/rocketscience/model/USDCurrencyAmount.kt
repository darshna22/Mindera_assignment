package com.mindera.rocketscience.model

data class USDCurrencyAmount(
    val date: String,
    val info: Info,
    val query: Query,
    val result: Int,
    val success: Boolean
)

data class Query(
    val amount: Long,
    val from: String,
    val to: String
)

data class Info(
    val rate: Double,
    val timestamp: Int
)