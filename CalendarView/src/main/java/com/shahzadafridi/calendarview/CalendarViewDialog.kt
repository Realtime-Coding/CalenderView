package com.shahzadafridi.calendarview

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager


class CalendarViewDialog(context: Context): Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_layout)
        setUpDefaultConfig()
    }

    private fun setUpDefaultConfig() {
        this.window!!.setGravity(Gravity.CENTER)
        this.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        this.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.window!!.attributes.windowAnimations = R.style.AnimatedDialogStyle
        this.window!!.setGravity(Gravity.TOP)
    }

    fun getCalendarView(): CalendarView{
        val calendarView = findViewById<CalendarView>(R.id.dialog_calendar_view)
        return calendarView
    }
}