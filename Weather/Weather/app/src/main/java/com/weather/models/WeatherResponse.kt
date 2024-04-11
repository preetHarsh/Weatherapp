package com.weather.models

import com.google.gson.annotations.SerializedName

class WeatherResponse {
    @SerializedName("location")
    var location: MyLocation? = null

    @SerializedName("current")
    var current: CurrentWeather? = null


}
