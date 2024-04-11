package com.weather.models

import com.google.gson.annotations.SerializedName

class WeatherCondition {
    @SerializedName("text")
    var conditionText: String = ""

    @SerializedName("icon")
    var conditionIconUrl: String = ""

    @SerializedName("code")
    var conditionCode: Int = 0


}
