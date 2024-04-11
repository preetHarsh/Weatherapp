package com.weather.models

import com.google.gson.annotations.SerializedName

class MyLocation {
    @SerializedName("name")
    var name: String = ""

    @SerializedName("region")
    var region: String = ""

    @SerializedName("country")
    var country: String = ""

    @SerializedName("lat")
    var latitude: Double = 0.0

    @SerializedName("lon")
    var longitude: Double = 0.0

    @SerializedName("tz_id")
    var timeZoneId: String = ""

    @SerializedName("localtime_epoch")
    var localTimeEpoch: Long = 0

    @SerializedName("localtime")
    var localTime: String = ""

 }
