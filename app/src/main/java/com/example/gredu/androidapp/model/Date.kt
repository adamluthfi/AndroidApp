package com.example.gredu.androidapp.model

class CityEvent(var id : Int,var name: String?,var description:String,val year:Int,var type: Int)


object DummyData {

    val data: ArrayList<CityEvent>
        get() {
            val list = ArrayList<CityEvent>()

            list.add(CityEvent(7, "juli", "asd",2017,2))
            list.add(CityEvent(8, "agustus", "asd",2017,2))
            list.add(CityEvent(9, "september", "asd",2017,2))
            list.add(CityEvent(10, "okotber", "asd",2017,2))
            list.add(CityEvent(11, "november", "asd",2017,2))
            list.add(CityEvent(12, "desember", "asd",2017,2))
            return list
        }

}