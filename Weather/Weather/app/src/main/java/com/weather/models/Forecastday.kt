package com.weather.models

class Forecastday {
    var date: String = ""
    var date_epoch: Int = 0
    var day: Day? = null
    var astro: Astro? = null
    var hour: ArrayList<Hour> = ArrayList()


}
