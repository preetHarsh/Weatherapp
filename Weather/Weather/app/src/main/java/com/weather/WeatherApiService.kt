package com.weather

import com.weather.models.ForecastResponse
import com.weather.models.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("current.json")
    fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("aqi") includeAqi: String
    ): Call<WeatherResponse>

    @GET("forecast.json")
    fun getWeatherForecast(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("days") days: Int,
        @Query("aqi") includeAqi: String,
        @Query("alerts") includeAlerts: String
    ): Call<ForecastResponse>
}
