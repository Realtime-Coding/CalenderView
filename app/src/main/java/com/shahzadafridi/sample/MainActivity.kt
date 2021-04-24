package com.shahzadafridi.sample

import android.os.Bundle
import android.util.Log
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


        binding.calendarView.builder()
            .withHeaderPanel(
                font = R.font.pfd_cond_regular,
                dateFormat = "MMM yyyy",
                textcolor = R.color.black,
                null,
                null,
                R.color.white
            )
            .withHeaderPanleMargin(0, 0, 0, 0)
            .withDayPanel(
                font = R.font.pfd_cond_regular,
                textColor = R.color.white,
                background = R.color.black
            )
            .withDayPanelMargin(0, 0, 0, 0)
            .withCellPanel(
                font = R.font.pfd_cond_regular,
                textColor = R.color.black,
                textSize = 16,
                selectedTextColor = R.color.white,
                selectedBackground = R.drawable.ic_green_oval,
                0,
                background = R.color.white
            )
            .withCellPanelMargin(0, 0, 0, 0)
            .withCalenderViewBackground(R.drawable.rect_lr_wround_bg)
            .updateCalendar(events)

        // assign event handler
        binding.calendarView.setEventHandler(object : CalenderViewInterface.EventHandler {

            override fun onCellClick(view: View?, date: Date, position: Int) {
                Log.e("TEST", "onCellClick")
            }

            override fun onCellLongClick(view: View?, date: Date, position: Int) {
                val df = SimpleDateFormat.getDateInstance()
                Toast.makeText(this@MainActivity, df.format(date), Toast.LENGTH_SHORT).show()
                Log.e("TEST", "onCellLongClick")
            }

            override fun onNextClick(view: View) {
                Log.e("TEST", "onNextClick")
            }

            override fun onPreviousClick(view: View) {
                Log.e("TEST", "onPreviousClick")
            }
        })
    }
}