package com.example.android_labs_sem4

data class Forecast(
    val cod: String,
    val message: Double,
    val cnt: Int,
    val list: List<ForecastItem>,
    val city: City
)