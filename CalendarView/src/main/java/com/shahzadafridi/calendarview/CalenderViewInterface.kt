package com.shahzadafridi.calendarview

import android.view.View
import java.util.*

interface CalenderViewInterface {
    fun builder(): CalendarView
    fun withHeaderPanel(font: Int?, dateFormat: String?, textcolor: Int?, background: Int?): CalendarView
    fun withHeaderPanleMargin(top: Float = 0f,bottom: Float = 0f,left: Float = 0f,right: Float = 0f): CalendarView
    fun withDayPanel(font: Int?, textColor: Int?, background: Int?): CalendarView
    fun withDayPanelMargin(top: Float = 0f,bottom: Float = 0f,left: Float = 0f,right: Float = 0f): CalendarView
    fun withCellPanel(font: Int?, textColor: Int?,selectedTextColor: Int, selectedBackground:Int, background: Int?): CalendarView
    fun withCellPanelMargin(top: Float = 0f,bottom: Float = 0f,left: Float = 0f,right: Float = 0f): CalendarView
    fun withCalenderViewBackground(background: Int?): CalendarView
    fun build(): CalendarView
    interface EventHandler{
        fun onCellClick(view: View)
        fun onCellLongClick(view: Date, position: Int, id: Long)
        fun onNextClick(view: View)
        fun onPreviousClick(view: View)
    }

}