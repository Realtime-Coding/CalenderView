package com.shahzadafridi.sample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.shahzadafridi.CalendarView
import com.shahzadafridi.sample.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val events = HashSet<Date>()
        events.add(Date())

        binding.calendarView.updateCalendar(events)

        // assign event handler
        binding.calendarView.setEventHandler(object : CalendarView.EventHandler{
            override fun onDayLongPress(date: Date?) {
                // show returned day
                val df = SimpleDateFormat.getDateInstance()
                Toast.makeText(this@MainActivity, df.format(date), Toast.LENGTH_SHORT).show()

            }

        })
    }
}