package com.example.gredu.androidapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gredu.androidapp.db.WheaterDataBase
import com.example.gredu.androidapp.db.entities.WeatherData
import kotlinx.android.synthetic.main.activity_main.*

private var mDb : WheaterDataBase? = null

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weatherData = WeatherData()
        if (weatherData == null){
            weatherData.humidity = 123
            weatherData.tempInC = 32.0
            weatherData.tempInF = 34.0
            weatherData.lat = 560.000
            weatherData.lon = 500.900
            weatherData.name = "adam"
            weatherData.region = "Mountainview"
            insertData(weatherData)
        }else{
            Toast.makeText(this,weatherData.humidity.toString(),Toast.LENGTH_SHORT).show()
            editText.setText(weatherData.tempInC.toString())
            editText2.setText(weatherData.tempInF.toString())
            editText3.setText(weatherData.lat.toString())
            editText4.setText(weatherData.lon.toString())
            editText5.setText(weatherData.name)
            editText6.setText(weatherData.region)
        }
    }

    private fun insertData(weatherData: WeatherData){
        mDb?.weatherDataDao()?.insert(weatherData)
    }
}
