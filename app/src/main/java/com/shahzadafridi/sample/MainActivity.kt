package com.shahzadafridi.sample

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.shahzadafridi.calendarview.CalendarView
import com.shahzadafridi.calendarview.CalenderViewInterface
import com.shahzadafridi.sample.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Event
        val events = HashSet<Calendar>()
        events.add(Calendar.getInstance().apply {
            this.time = Date()
        })

        binding.calendarView.updateCalendar(events)

        // assign event handler
        binding.calendarView.setEventHandler(object : CalenderViewInterface.EventHandler{

            override fun onCellClick(view: View) {
                TODO("Not yet implemented")
            }

            override fun onCellLongClick(date: Date, position: Int, id: Long) {
                val df = SimpleDateFormat.getDateInstance()
                Toast.makeText(this@MainActivity, df.format(date), Toast.LENGTH_SHORT).show()
            }

            override fun onNextClick(view: View) {
                TODO("Not yet implemented")
            }

            override fun onPreviousClick(view: View) {
                TODO("Not yet implemented")
            }
        })
    }
}