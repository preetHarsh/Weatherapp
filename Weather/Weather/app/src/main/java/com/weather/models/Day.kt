package com.weather.models

import com.google.gson.annotations.SerializedName

class Day {
    var maxtemp_c: Double = 0.0
    var maxtemp_f: Double = 0.0
    var mintemp_c: Double = 0.0
    var mintemp_f: Double = 0.0
    var avgtemp_c: Double = 0.0
    var avgtemp_f: Double = 0.0
    var maxwind_mph: Double = 0.0
    var maxwind_kph: Double = 0.0
    var totalprecip_mm: Double = 0.0
    var totalprecip_in: Double = 0.0
    var totalsnow_cm: Double = 0.0
    var avgvis_km: Double = 0.0
    var avgvis_miles: Double = 0.0
    var avghumidity: Int = 0
    var daily_will_it_rain: Int = 0
    var daily_chance_of_rain: Int = 0
    var daily_will_it_snow: Int = 0
    var daily_chance_of_snow: Int = 0
    @SerializedName("condition")
    var condition: WeatherCondition? = null
    var uv: Double = 0.0

}
