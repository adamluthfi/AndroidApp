package com.example.gredu.androidapp.service

import com.example.gredu.androidapp.model.WeatherForecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("/v1/forecast.json?key=f308568837cd42d793d123416170610")
    fun getWeatherData(@Query("q") city: String, @Query("days") totalCount: Int):Call<WeatherForecast>
}