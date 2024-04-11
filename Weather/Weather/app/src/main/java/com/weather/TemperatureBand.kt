package com.weather

enum class TemperatureBand(val imageResourceId: Int) {
    FREEZING(R.drawable.freezing),
    COLD(R.drawable.cold),
    MODERATE(R.drawable.moderate),
    WARM(R.drawable.warm),
    HOT(R.drawable.hot);

    companion object {
        fun getImageResourceId(tempBand: TemperatureBand): Int {
            return tempBand.imageResourceId
        }
    }
}
