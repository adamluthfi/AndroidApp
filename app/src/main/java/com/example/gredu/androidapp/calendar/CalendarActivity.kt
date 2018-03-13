package com.example.gredu.androidapp.calendar

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.applandeo.materialcalendarview.EventDay
import com.example.gredu.androidapp.R
import kotlinx.android.synthetic.main.activity_calendar.*
import java.util.*
import com.github.sundeepk.compactcalendarview.domain.Event
import kotlin.collections.ArrayList

class CalendarActivity : AppCompatActivity() {

    private val currentCalender = Calendar.getInstance(Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        val event = ArrayList<EventDay>()

        val calendar = Calendar.getInstance()
        val calendar1 = Calendar.getInstance()
        calendar1.add(Calendar.DAY_OF_MONTH,2)
        val calendar2 = Calendar.getInstance()
        calendar2.add(Calendar.DAY_OF_MONTH,3)
        val calendar3 = Calendar.getInstance()
        calendar3.add(Calendar.DAY_OF_MONTH,4)
        val calendar4 = Calendar.getInstance()
        calendar4.add(Calendar.DAY_OF_MONTH,5)
        val calendar5 = Calendar.getInstance()
        calendar5.add(Calendar.DAY_OF_MONTH,6)
        val calendar6 = Calendar.getInstance()
        calendar6.add(Calendar.DAY_OF_MONTH,7)

        event.add(EventDay(calendar,R.drawable.ic_event))
        event.add(EventDay(calendar1, R.drawable.ic_event))
        event.add(EventDay(calendar2, R.drawable.ic_event))
        event.add(EventDay(calendar3, R.drawable.ic_event))
        event.add(EventDay(calendar4, R.drawable.ic_event))
        event.add(EventDay(calendar5, R.drawable.ic_event))
        event.add(EventDay(calendar6, R.drawable.ic_event))

        addEvents(Calendar.FEBRUARY,-1)

/*        calendarView.setDate(calendar.time)
        calendarView.setEvents(event)*/

    }

    private fun addEvents(month: Int, year: Int) {
        currentCalender.time = Date()
        currentCalender.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayOfMonth = currentCalender.time
        for (i in 0..11) {
            currentCalender.time = firstDayOfMonth
            if (month > -1) {
                currentCalender.set(Calendar.MONTH, month)
            }
            if (year > -1) {
                currentCalender.set(Calendar.ERA, GregorianCalendar.AD)
                currentCalender.set(Calendar.YEAR, year)
            }
            currentCalender.add(Calendar.DATE, i)
            setToMidnight(currentCalender)
            val timeInMillis = currentCalender.timeInMillis

            val events = getEvents(timeInMillis, i)

            compatCalendarView.addEvents(events)
        }
    }

    private fun setToMidnight(calendar: Calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
    }

    private fun getEvents(timeInMillis: Long, day: Int): List<Event> {
        return Arrays.asList(Event(Color.rgb(255, 142, 142), timeInMillis, "Event at " + Date(timeInMillis)))
    }

}
