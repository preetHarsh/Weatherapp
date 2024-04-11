package com.weather.models

import com.weather.TemperatureBand

class ForecastItem(
    var temp: Int,
    var day: String,
    var condition: String,
    var uv: Double,
    var chancesRain: Int
) {
    fun getTemperatureBand(): TemperatureBand {
        return when {
            temp <= 0 -> TemperatureBand.FREEZING
            temp <= 10 -> TemperatureBand.COLD
            temp <= 20 -> TemperatureBand.MODERATE
            temp <= 30 -> TemperatureBand.WARM
            else -> TemperatureBand.HOT
        }
    }
}
