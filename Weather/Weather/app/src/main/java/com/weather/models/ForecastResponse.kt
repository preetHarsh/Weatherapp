package com.weather.models

import com.google.gson.annotations.SerializedName

class ForecastResponse {
    var location: MyLocation? = null
    @SerializedName("current")
    var current: CurrentWeather? = null
    var forecast: Forecast? = null

}
