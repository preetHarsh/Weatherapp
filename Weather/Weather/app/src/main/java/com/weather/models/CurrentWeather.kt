package com.weather.models

import com.google.gson.annotations.SerializedName

class CurrentWeather {
    @SerializedName("last_updated_epoch")
    var lastUpdatedEpoch: Long = 0

    @SerializedName("last_updated")
    var lastUpdated: String = ""

    @SerializedName("temp_c")
    var temperatureCelsius: Double = 0.0

    @SerializedName("temp_f")
    var temperatureFahrenheit: Double = 0.0

    @SerializedName("is_day")
    var isDay: Int = 0

    @SerializedName("condition")
    var condition: WeatherCondition? = null

    @SerializedName("wind_mph")
    var windMph: Double = 0.0

    @SerializedName("wind_kph")
    var windKph: Double = 0.0

    @SerializedName("wind_degree")
    var windDegree: Int = 0

    @SerializedName("wind_dir")
    var windDirection: String = ""

    @SerializedName("pressure_mb")
    var pressureMb: Double = 0.0

    @SerializedName("pressure_in")
    var pressureIn: Double = 0.0

    @SerializedName("precip_mm")
    var precipMm: Double = 0.0

    @SerializedName("precip_in")
    var precipIn: Double = 0.0

    @SerializedName("humidity")
    var humidity: Int = 0

    @SerializedName("cloud")
    var cloud: Int = 0

    @SerializedName("feelslike_c")
    var feelsLikeCelsius: Double = 0.0

    @SerializedName("feelslike_f")
    var feelsLikeFahrenheit: Double = 0.0

    @SerializedName("vis_km")
    var visibilityKm: Double = 0.0

    @SerializedName("vis_miles")
    var visibilityMiles: Double = 0.0

    @SerializedName("uv")
    var uvIndex: Double = 0.0

    @SerializedName("gust_mph")
    var gustMph: Double = 0.0

    @SerializedName("gust_kph")
    var gustKph: Double = 0.0
}
