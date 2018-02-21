package com.example.gredu.androidapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import android.widget.Toast
import com.example.gredu.androidapp.db.WheaterDataBase
import com.example.gredu.androidapp.db.entities.WeatherData
import com.example.gredu.androidapp.model.WeatherForecast
import com.example.gredu.androidapp.service.Injection
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    private var mDb : WheaterDataBase? = null

    private lateinit var tempC : TextView
    private lateinit var tempF : TextView
    private lateinit var latitude: TextView
    private lateinit var longitude: TextView
    private lateinit var name: TextView
    private lateinit var region: TextView

    private lateinit var mDbWorkerThread: DbWorkerThread

    private val UiHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDbWorkerThread = DbWorkerThread("dbWorkerThread")
        mDbWorkerThread.start()

        tempC = findViewById(R.id.editText)
        tempF = findViewById(R.id.editText2)
        latitude = findViewById(R.id.editText3)
        longitude = findViewById(R.id.editText4)
        name = findViewById(R.id.editText5)
        region = findViewById(R.id.editText6)

        mDb = WheaterDataBase.getInstance(this)

        if (isConnectedToInternet()){
            Injection.provideApiService()
                    .getWeatherData("Jakarta",4)
                    .enqueue(object : retrofit2.Callback<WeatherForecast>{
                        override fun onFailure(call: Call<WeatherForecast>?, t: Throwable?) {
                            Timber.d(t?.message)
                        }

                        override fun onResponse(call: Call<WeatherForecast>?, response: Response<WeatherForecast>?) {
                            Timber.d(response?.body().toString())
                            val weatherForecast = response?.body()
                            val weatherData = WeatherData()

                            weatherData.humidity = weatherForecast?.current?.humidity ?: 0
                            weatherData.tempInC = weatherForecast?.current?.temp_c ?: 0.0
                            weatherData.tempInF = weatherForecast?.current?.temp_f ?: 0.0
                            weatherData.lat = weatherForecast?.location?.lat ?: 0.0
                            weatherData.lon = weatherForecast?.location?.lon ?: 0.0
                            weatherData.name = weatherForecast?.location?.name ?: ""
                            weatherData.region = weatherForecast?.location?.region ?: ""

                            bindDataWithUi(weatherData)

                            insertWeatherInDb(weatherData = weatherData)

                        }

                    })
        }else{
            fetchWeatherDataFromDb()
        }
    }

    private fun bindDataWithUi(weatherData: WeatherData){
        tempC.text = weatherData.tempInC.toString()
        tempF.text = weatherData.tempInF.toString()
        latitude.text = weatherData.lat.toString()
        longitude.text = weatherData.lon.toString()
        name.text = weatherData.name.toString()
        region.text = weatherData.region.toString()
    }

    private fun fetchWeatherDataFromDb(){
        val task = Runnable {
            val weatherData = mDb?.weatherDataDao()?.getAll()
            UiHandler.post {
                if (weatherData == null || weatherData.isEmpty()){
                    showToast("No data in chace..",Toast.LENGTH_SHORT)
                }else{
                    bindDataWithUi(weatherData = weatherData.get(0))
                }
            }
        }
        mDbWorkerThread.postTask(task)
    }

    private fun insertWeatherInDb(weatherData: WeatherData){
        val task = Runnable { mDb?.weatherDataDao()?.insert(weatherData) }
        mDbWorkerThread.postTask(task)
    }

    override fun onDestroy() {
        WheaterDataBase.destroyInstance()
        mDbWorkerThread.quit()
        super.onDestroy()
    }
}
