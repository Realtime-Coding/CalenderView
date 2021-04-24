package com.shahzadafridi.calendarview

import android.view.View
import java.util.*

interface CalenderViewInterface {
    fun builder(): CalendarView
    fun withHeaderPanel(font: Int?, dateFormat: String?, textcolor: Int?, nextIcon: Int?, previousIcon: Int?, background: Int?): CalendarView
    fun withHeaderPanleMargin(top: Int = 0,bottom: Int = 0,left: Int = 0,right: Int = 0): CalendarView
    fun withDayPanel(font: Int?, textColor: Int?, background: Int?): CalendarView
    fun withDayPanelMargin(top: Int = 0,bottom: Int = 0,left: Int = 0,right: Int = 0): CalendarView
    fun withCellPanel(font: Int?, textColor: Int?, textSize: Int, selectedTextColor: Int, selectedBackground:Int, cellSize: Int?, background: Int?): CalendarView
    fun withCellPanelMargin(top: Int = 0,bottom: Int = 0,left: Int = 0,right: Int = 0): CalendarView
    fun withCalenderViewBackground(background: Int?): CalendarView
    fun build(): CalendarView
    interface EventHandler{
        fun onCellClick(view: View?, date: Date, position: Int)
        fun onCellLongClick(view: View?, date: Date, position: Int)
        fun onNextClick(view: View)
        fun onPreviousClick(view: View)
    }

}