package com.weather

import com.weather.models.ForecastResponse
import com.weather.models.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherApiClient {
    private val BASE_URL = "http://api.weatherapi.com/v1/"
    private val API_KEY = "11173a7da9a34cce986135639242703"
    private val apiService: WeatherApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(WeatherApiService::class.java)
    }

    fun getCurrentWeather(location: String, includeAqi: String, callback: WeatherCallback) {
        val call = apiService.getCurrentWeather(API_KEY, location, includeAqi)
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    callback.onSuccess(weatherResponse)
                } else {
                    callback.onError("Failed to fetch data")
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                callback.onError(t.message ?: "Unknown error")
            }
        })
    }

    interface WeatherCallback {
        fun onSuccess(weatherResponse: WeatherResponse?)
        fun onError(errorMessage: String)
    }

    fun getWeatherForecast(location: String, days: Int, includeAqi: String, includeAlerts: String, callback: ForecastCallback) {
        val call = apiService.getWeatherForecast(API_KEY, location, days, includeAqi, includeAlerts)
        call.enqueue(object : Callback<ForecastResponse> {
            override fun onResponse(call: Call<ForecastResponse>, response: Response<ForecastResponse>) {
                if (response.isSuccessful) {
                    val forecastResponse = response.body()
                    callback.onSuccess(forecastResponse)
                } else {
                    callback.onError("Failed to fetch forecast data")
                }
            }

            override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                callback.onError(t.message ?: "Unknown error")
            }
        })
    }

    interface ForecastCallback {
        fun onSuccess(forecastResponse: ForecastResponse?)
        fun onError(errorMessage: String)
    }
}
