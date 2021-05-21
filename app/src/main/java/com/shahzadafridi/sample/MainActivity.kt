package com.shahzadafridi.sample

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.shahzadafridi.calendarview.CalendarView
import com.shahzadafridi.calendarview.CalendarViewDialog
import com.shahzadafridi.calendarview.CalenderViewInterface
import com.shahzadafridi.sample.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var dialog: CalendarViewDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpCalendarView(binding.calendarView)
    }

    fun showDialogCalendar(view: View) {
        dialog = CalendarViewDialog(this)
        dialog!!.setCancelable(false)
        dialog!!.show()
        setUpCalendarView(dialog!!.getCalendarView())
    }

    private fun setUpCalendarView(calendarView: CalendarView) {
        //Fake Events
        val events = HashSet<Calendar>()
        val c1 = Calendar.getInstance()
        for (i in 15..20){
            c1.set(Calendar.DAY_OF_MONTH,i)
            events.add(Calendar.getInstance().apply {
                this.time = c1.time
            })
        }

        //Setup CalendarView.
        calendarView.builder()
            .withYearPanel("yyyy",R.color.black,42,R.font.titillium_web_semibold)
            .withBackButton(true,R.drawable.ic_up_round)
            .withMonthPanel(
                font = R.font.titillium_web_semibold,
                textSize = 20,
                selectedTextColor = R.color.black,
                unSelectedTextColor = R.color.greyed_out,
                background = R.color.white
            )
            .withWeekPanel(
                font = R.font.titillium_web_semibold,
                textColor = R.color.black,
                textSize = 14,
                background = R.color.white
            )
            .withDayPanel(
                font = R.font.titillium_web_semibold,
                textColor = R.color.black,
                textSize = 16,
                selectedTextColor = R.color.white,
                selectedBackground = R.drawable.ic_green_oval,
                background = R.color.white
            )
            .withCalenderViewBg(R.drawable.rect_lr_wround_bg)
            .withEvents(events, R.color.green)
            .buildCalendar()

        //CalendarView event handler
        calendarView.setEventHandler(object : CalenderViewInterface.EventHandler {

            override fun onDayClick(view: View?, date: Date, position: Int) {
                val df = SimpleDateFormat.getDateInstance()
                Toast.makeText(this@MainActivity, df.format(date), Toast.LENGTH_SHORT).show()
                Log.e("TEST", "onDayClick")
            }

            override fun onDayLongClick(view: View?, date: Date, position: Int) {
                val df = SimpleDateFormat.getDateInstance()
                Toast.makeText(this@MainActivity, df.format(date), Toast.LENGTH_SHORT).show()
                Log.e("TEST", "onDayLongClick")
            }

            override fun onBackClick(view: View?) {
                Log.e("TEST", "onBackClick")
                dialog?.dismiss()
            }

            override fun onMonthClick(view: View?, month: String, position: Int) {
                Toast.makeText(this@MainActivity, month, Toast.LENGTH_SHORT).show()
                Log.e("TEST", "onMonthClick")
            }
        })
    }

}