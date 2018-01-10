package com.example.gredu.androidapp.db

import android.arch.persistence.db.SupportSQLiteOpenHelper
import android.arch.persistence.room.*
import android.content.Context
import com.example.gredu.androidapp.db.daos.WheaterDataDao
import com.example.gredu.androidapp.db.entities.WeatherData


@Database(entities = arrayOf(WeatherData::class),version = 1)
abstract class WheaterDataBase : RoomDatabase(){

    abstract fun weatherDataDao():WheaterDataDao

    companion object {
        private var INSTANCE: WheaterDataBase? = null

        fun getInstance(context: Context): WheaterDataBase?{
            if (INSTANCE == null){
                synchronized(WheaterDataBase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext
                            ,WheaterDataBase::class.java,"weather.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}