package com.weather

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.AsyncTask
import android.os.Bundle
import android.text.format.DateUtils.formatDateTime
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weather.models.CurrentWeather
import com.weather.models.ForecastItem
import com.weather.models.ForecastResponse
import com.weather.models.MyLocation
import com.weather.models.WeatherResponse
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException

class Home : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ForecastAdapter
    private lateinit var textViewTempCelsius: TextView
    private lateinit var textViewCountry: TextView
    private lateinit var textViewCity: TextView
    private lateinit var textViewDate: TextView
    private lateinit var textViewDay: TextView
    private lateinit var textViewCondition: TextView
    private lateinit var textViewTFeelsLike: TextView
    private lateinit var textViewHumidity: TextView
    private lateinit var textViewWind: TextView
    private lateinit var textViewUV: TextView
    private lateinit var locationEditText: EditText
    private lateinit var editLocation: LinearLayout

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 100
        var location: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        textViewTempCelsius = findViewById(R.id.textViewTempCelsius)
        textViewCountry = findViewById(R.id.textViewCountry)
        textViewCity = findViewById(R.id.textViewCity)
        textViewDate = findViewById(R.id.textViewDate)
        textViewDay = findViewById(R.id.textViewDay)
        textViewCondition = findViewById(R.id.textViewCondition)
        textViewTFeelsLike = findViewById(R.id.textViewTFeelsLike)
        textViewHumidity = findViewById(R.id.textViewHumidity)
        textViewWind = findViewById(R.id.textViewWind)
        textViewUV = findViewById(R.id.textViewUV)
        editLocation = findViewById(R.id.editLocation)
        locationEditText = findViewById(R.id.locationEditText)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            getCurrentLocation()
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        WeatherForecastTask().execute()

        val btnLeft = findViewById<ImageButton>(R.id.btnLeft)
        val btnRight = findViewById<ImageButton>(R.id.btnRight)
        btnLeft.setOnClickListener {
            val firstVisibleItemPosition =
                (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            if (firstVisibleItemPosition > 0) {
                recyclerView.smoothScrollToPosition(firstVisibleItemPosition - 1)
            }
        }

        btnRight.setOnClickListener {
            val lastVisibleItemPosition =
                (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            if (lastVisibleItemPosition < adapter.itemCount - 1) {
                recyclerView.smoothScrollToPosition(lastVisibleItemPosition + 1)
            }
        }
    }

    fun submitLocation(view: View) {
        locationEditText = findViewById(R.id.locationEditText)
        location = locationEditText.text.toString()

        // Dismiss the keyboard
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(locationEditText.windowToken, 0)

        getTodayWeather()
        WeatherForecastTask().execute()
        locationEditText.setText("")
        toggleEditTextVisibility()
    }

    fun toggleEditTextVisibility() {
        editLocation = findViewById(R.id.editLocation)
        editLocation.visibility = if (editLocation.visibility == View.VISIBLE) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    fun toggleEditTextVisibility(view: View) {
        editLocation = findViewById(R.id.editLocation)
        editLocation.visibility = if (editLocation.visibility == View.VISIBLE) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun getCurrentLocation() {
        val locationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val lastKnownLocation = if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request the missing permissions. This will invoke onRequestPermissionsResult() callback.
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            null // Return null for lastKnownLocation since permissions are not granted yet.
        } else {
            // Permissions are granted. Proceed to get the last known location.
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        }

        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (lastKnownLocation != null) {
            val currentCity =
                getCityFromLocation(lastKnownLocation.latitude, lastKnownLocation.longitude)
            location = currentCity
            locationEditText.setText(currentCity)

        } else {
            Toast.makeText(this, "Failed to get location", Toast.LENGTH_SHORT).show()
            textViewCity.text = "Nairobi"
        }
        getTodayWeather()
        WeatherForecastTask().execute()
    }

    private fun getCityFromLocation(latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1) as List<Address>
            if (addresses.isNotEmpty()) {
                return addresses[0].locality
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return "Unknown"
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation()
        } else {
            Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getTodayWeather() {
        val weatherApiClient = WeatherApiClient()
        weatherApiClient.getCurrentWeather(
            location,
            "no",
            object : WeatherApiClient.WeatherCallback {
                override fun onSuccess(weatherResponse: WeatherResponse?) {
                    val location = weatherResponse?.location
                    val currentWeather = weatherResponse?.current
                    textViewTempCelsius.text = "${currentWeather?.temperatureCelsius?.toInt()}°C"
                    textViewCountry.text = location?.country
                    textViewCity.text = "${location?.name}, "
                    if (location != null) {
                        textViewDate.text = formatDateTime(location.localTime )
                    }
                    if (location != null) {
                        textViewDay.text = getDayOfWeek(location.localTime)
                    }
                    textViewCondition.text = currentWeather?.condition?.conditionText
                    textViewTFeelsLike.text = "${currentWeather?.feelsLikeCelsius}°C"
                    textViewHumidity.text = currentWeather?.humidity.toString()
                    textViewWind.text = "${currentWeather?.windMph} mph"
                    textViewUV.text = currentWeather?.uvIndex.toString()                }

                override fun onError(errorMessage: String) {
                    Log.d("pss", errorMessage)
                }
            })
    }

    private inner class WeatherForecastTask : AsyncTask<Void, Void, List<ForecastItem>>() {

        override fun doInBackground(vararg voids: Void): List<ForecastItem>? {
            return try {
                callWeatherForecast()
            } catch (e: ExecutionException) {
                e.printStackTrace()
                null
            } catch (e: InterruptedException) {
                e.printStackTrace()
                null
            }
        }

        override fun onPostExecute(forecasts: List<ForecastItem>?) {
            if (forecasts != null) {
                adapter = ForecastAdapter(forecasts)
                recyclerView.adapter = adapter
            }
        }
    }

    @Throws(ExecutionException::class, InterruptedException::class)
    private fun callWeatherForecast(): List<ForecastItem> {
        val apiClient = WeatherApiClient()
        val future = CompletableFuture<List<ForecastItem>>()

        val days = 7
        val includeAqi = "no"
        val includeAlerts = "no"

        apiClient.getWeatherForecast(
            location, days, includeAqi, includeAlerts,
            object : WeatherApiClient.ForecastCallback {

                override fun onSuccess(forecastResponse: ForecastResponse?) {
                    val forecasts = ArrayList<ForecastItem>()
                    forecastResponse?.forecast?.forecastday?.forEach { v ->
                        forecasts.add(
                            ForecastItem(
                                v.day?.avgtemp_c!!.toInt(),
                                convertToDayOfWeek(v.date) ?: "",
                                v.day?.condition?.conditionText ?: "",
                                v.day?.uv ?: 0.0,
                                v.day?.daily_chance_of_rain ?: 0
                            )
                        )
                    }
                    future.complete(forecasts)                }

                override fun onError(errorMessage: String) {
                    println("Error fetching forecast: $errorMessage")
                    future.completeExceptionally(RuntimeException("Error fetching forecast: $errorMessage"))
                }
            })

        return future.get()
    }

    private fun getDayOfWeek(dateString: String): String? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
        return try {
            val date = dateFormat.parse(dateString)
            val calendar = Calendar.getInstance()
            calendar.time = date
            val dayOfWeek = calendar[Calendar.DAY_OF_WEEK]
            val daysOfWeek = arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
            daysOfWeek[dayOfWeek - 1]
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }

    private fun formatDateTime(inputDate: String): String? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("dd MMMM yyyy, hh:mm a", Locale.ENGLISH)
        return try {
            val date = inputFormat.parse(inputDate)
            outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }

    private fun convertToDayOfWeek(dateString: String): String? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val date: Date
        return try {
            date = dateFormat.parse(dateString)!!
            val calendar = Calendar.getInstance()
            calendar.time = date
            val dayOfWeekInt = calendar[Calendar.DAY_OF_WEEK]
            val daysOfWeek = arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
            daysOfWeek[dayOfWeekInt - 1]
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }
}