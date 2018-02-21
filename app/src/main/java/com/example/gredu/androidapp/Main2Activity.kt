package com.example.gredu.androidapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main2.*
import AdapterMain
import com.example.gredu.androidapp.model.CityEvent
import com.example.gredu.androidapp.model.DummyData

class Main2Activity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val data = ArrayList<CityEvent>()
        data.add(CityEvent(0,"","Semester 2",0,1))
        for (i in DummyData.data){
            if (i.id<= 6){
                data.add(CityEvent(i.id,i.name,i.description,i.year,2))
            }
        }

        data.add(CityEvent(0,"","Semester 1",0,1))
        for (i in DummyData.data){
            if (i.id>= 7){
                data.add(CityEvent(i.id,i.name,i.description,i.year,2))
            }
        }

        val adapter = AdapterMain(data, this)
        listDate.layoutManager = LinearLayoutManager(this)
        listDate.adapter = adapter
    }
}

