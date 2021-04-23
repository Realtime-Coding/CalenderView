package com.shahzadafridi

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.shahzadafridi.sample.R
import java.util.*

class CalendarAdapter(
    context: Context,
    days: ArrayList<Date>, // days with events
    private val eventDays: HashSet<Date>?
): ArrayAdapter<Date>(context, R.layout.control_calendar_day, days) {

    // for view inflation
    private val inflater: LayoutInflater
    private val mContext: Context

    init {
        inflater = LayoutInflater.from(context)
        mContext = context
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        // day in question
        var view = view
        val date = getItem(position)
        val day = date!!.day
        val month = date.month
        val year = date.year


        // today
        val today = Date()

        // inflate item if it does not exist yet
        if (view == null) view = inflater.inflate(R.layout.control_calendar_day, parent, false)

        // if this day has an event, specify event image
        view!!.setBackgroundResource(0)
        if (eventDays != null) {
            for (eventDate in eventDays) {
                if (eventDate.date == day && eventDate.month == month && eventDate.year == year) {
                    // mark this day for event
                    view.setBackgroundResource(R.drawable.ic_green_oval)
                    break
                }
            }
        }

        // clear styling
        (view as TextView?)!!.setTypeface(null, Typeface.NORMAL)
        (view as TextView?)!!.setTextColor(Color.BLACK)
        if (month != today.month || year != today.year) {
            // if this day is outside current month, grey it out
            (view as TextView?)!!.setTextColor(mContext.resources.getColor(R.color.greyed_out))
        } else if (day == today.date) {
            // if it is today, set it to blue/bold
            (view as TextView?)!!.setTypeface(null, Typeface.BOLD)
            (view as TextView?)!!.setTextColor(mContext.resources.getColor(R.color.today))
        }

        // set text
        (view as TextView?)!!.text = date.date.toString()

        return view
    }
}