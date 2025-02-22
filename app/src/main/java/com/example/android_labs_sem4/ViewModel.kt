package com.example.android_labs_sem4

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

class ViewModel(
    private val service: OpenWeatherMapService,
    private val resources: Resources
) : ViewModel() {

    private val _forecastData = MutableLiveData<List<ForecastItem>>()
    val forecastData: LiveData<List<ForecastItem>> = _forecastData

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage

    private val _isCelsius = MutableLiveData(true)
    val isCelsius: LiveData<Boolean> = _isCelsius

    fun fetchWeather(city: String) {
        val apiKey = "xxx"
        val call = service.getForecast(city, "metric", apiKey)

        call.enqueue(object : Callback<Forecast> {
            override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                when {
                    response.code() == 404 ->
                        _toastMessage.value = "Город не найден"
                    response.body() == null ->
                        _toastMessage.value = "Ошибка формата данных"
                }
                if (response.isSuccessful) {
                    response.body()?.list?.let {
                        _forecastData.value = it
                    }
                }
            }

            override fun onFailure(call: Call<Forecast>, t: Throwable) {
                _toastMessage.value = "Ошибка сети: ${t.localizedMessage}"
            }
        })
    }

    fun convertTemperature(celsius: Double): Double {
        return if (_isCelsius.value == true) celsius else celsius * 9 / 5 + 32
    }

    fun onToastShown() {
        _toastMessage.value = null
    }

    fun toggleTemperatureUnit() {
        _isCelsius.value = !(_isCelsius.value ?: true)
    }
}

interface OpenWeatherMapService {
    @GET("forecast")
    fun getForecast(
        @Query("q") city: String,
        @Query("units") units: String,
        @Query("appid") apiKey: String
    ): Call<Forecast>
}