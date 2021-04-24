package com.shahzadafridi.calendarview

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewDebug
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import java.util.*

class CalendarAdapter(
        context: Context,
        days: ArrayList<Calendar>, // days with events
        private val eventDays: HashSet<Calendar>?
) : ArrayAdapter<Calendar>(context, R.layout.control_calendar_day, days) {

    // for view inflation
    private val inflater: LayoutInflater
    private val mContext: Context

    //Cell Configuration
    private var cell_font: Int? = null
    private var cell_size: Int? = null
    private var cell_bg: Int? = null
    private var cell_txt_clr: Int? = null
    private var cell_txt_size: Int? = null
    private var cell_selected_txt_clr: Int? = null
    private var cell_select_bg: Int? = null

    init {
        inflater = LayoutInflater.from(context)
        mContext = context
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {

        // day in question
        var view = view
        val date = getItem(position)

        val day = date!!.get(Calendar.DATE)
        val month = date.get(Calendar.MONTH)
        val year = date.get(Calendar.YEAR)

        // today
        val today = Calendar.getInstance()

        // inflate item if it does not exist yet
        if (view == null) view = inflater.inflate(R.layout.control_calendar_day, parent, false)

        var textView = view as TextView
        // clear styling
        textView.setTypeface(null, Typeface.NORMAL)
        textView.setTextColor(Color.BLACK)
        if (month != today.get(Calendar.MONTH) || year != today.get(Calendar.YEAR)) {
            // if this day is outside current month, grey it out
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.greyed_out))
        } else if (day == today.get(Calendar.DATE)) {
            // if it is today, set it to blue/bold
            textView.setTypeface(null, Typeface.BOLD)
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.today))
        }

        cell_txt_size?.let {
            textView.textSize = it.toFloat()
        }
        cell_txt_clr?.let {
            textView.setTextColor(ContextCompat.getColor(mContext, it))
        }
        cell_font?.let {
            textView.typeface = ResourcesCompat.getFont(context, it)
        }
        // set text
        textView.text = date.get(Calendar.DATE).toString()

        // if this day has an event, specify event image
        cell_bg?.let {
            view.setBackgroundResource(it)
        }

        if (eventDays != null) {
            for (eventDate in eventDays) {
                if (eventDate.get(Calendar.DATE) == day && eventDate.get(Calendar.MONTH) == month && eventDate.get(Calendar.YEAR) == year) {
                    // mark this day for event
                    cell_select_bg?.let {
                        view.setBackgroundResource(it)
                    }
                    cell_selected_txt_clr?.let {
                        textView.setTextColor(ContextCompat.getColor(mContext, it))
                    }
                    break
                }
            }
        }

        return view
    }

    fun setCellConfig(cellTxtClr: Int?, cellBg: Int?, cellSelectedClr: Int?, cellSelectBg: Int?, cellFont: Int?, cellSize: Int?, cellTxtSize: Int?) {
        cell_txt_clr = cellTxtClr
        cell_bg = cellBg
        cell_selected_txt_clr = cellSelectedClr
        cell_select_bg = cellSelectBg
        cell_font = cellFont
        cell_size = cellSize
        cell_txt_size = cellTxtSize
        notifyDataSetChanged()
    }
}