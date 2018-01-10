package com.example.gredu.androidapp.db.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.gredu.androidapp.db.entities.WeatherData

@Dao
interface WheaterDataDao {

    @Query("SELECT * from weatherData")
    fun getAll():List<WeatherData>

    @Insert(onConflict = REPLACE)
    fun insert(weatherData: WeatherData)

    @Query("DELETE from weatherData")
    fun deleteAll()

}