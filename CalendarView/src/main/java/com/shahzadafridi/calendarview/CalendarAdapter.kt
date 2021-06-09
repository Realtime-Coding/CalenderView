package com.shahzadafridi.calendarview

import android.content.Context
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception
import java.util.*

class CalendarAdapter(
    context: Context,
    private val days: ArrayList<Calendar>, // days with events
    private val eventDays: HashSet<Calendar>?,
    private var eventsHandler: CalenderViewInterface.EventHandler?,
    private var dayConfig: DayConfiguration?,
    monthNumber: Int,
    private var selectedDate: Calendar?
) : RecyclerView.Adapter<CalendarAdapter.MyViewHolder>() {

    val TAG: String = "CalendarAdapter"
    // for view inflation
    private val inflater: LayoutInflater
    private val mContext: Context
    private val mMonthNumber = monthNumber

    //Day Configuration
    private var day_font: Int? = null
    private var day_bg: Int? = null
    private var day_txt_clr: Int? = null
    private var day_txt_size: Float? = null
    private var day_selected_txt_clr: Int? = null
    private var day_selected_bg: Int? = null
    private var event_dot_clr: Int? = null

    init {
        inflater = LayoutInflater.from(context)
        mContext = context
        dayConfig?.let {
            day_font = it.dayFont
            day_bg = it.dayBg
            day_txt_clr = it.dayTxtClr
            day_txt_size = it.dayTxtSize
            day_selected_txt_clr = it.daySelectedClr
            day_selected_bg = it.daySelectedBg
            event_dot_clr = it.eventDotColor
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CalendarAdapter.MyViewHolder {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.row_calendar_day_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarAdapter.MyViewHolder, position: Int) {
        holder.updateUi(holder, days[position])
    }

    override fun getItemCount(): Int {
        return days.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {

        var textView: TextView
        var textViewEvent: TextView
        var rowLayout: RelativeLayout

        init {
            textView = itemView.findViewById(R.id.control_calendar_day)
            textViewEvent = itemView.findViewById(R.id.control_calendar_event)
            rowLayout = itemView.findViewById(R.id.row_calendar_layout)
            rowLayout.setOnClickListener(this)
            rowLayout.setOnLongClickListener(this)
            day_txt_size?.let {
                textView.textSize = it
            }
            day_txt_clr?.let {
                textView.setTextColor(ContextCompat.getColor(mContext, it))
            }
            try {
                day_font?.let {
                    textView.typeface = ResourcesCompat.getFont(mContext, it)
                }
            }catch (e: Exception){
                Log.e(TAG, "$day_font not found!")
            }
            day_bg?.let {
                rowLayout.setBackgroundResource(it)
            }
            event_dot_clr?.let {
                eventShape(textViewEvent,it)
            }
        }

        fun updateUi(holder: MyViewHolder, date: Calendar) {

            val day = date.get(Calendar.DATE)
            val month = date.get(Calendar.MONTH)
            val year = date.get(Calendar.YEAR)

            // set text
            holder.textView.text = date.get(Calendar.DATE).toString()


            if (month != mMonthNumber || year != selectedDate!!.get(Calendar.YEAR)) {
                // if this day is outside current month, grey it out
                holder.textView.setTextColor(ContextCompat.getColor(mContext, R.color.greyed_out))
                holder.rowLayout.isEnabled = false
            } else if (selectedDate!!.get(Calendar.DATE) == day && selectedDate!!.get(Calendar.MONTH) == month && selectedDate!!.get(Calendar.YEAR) == year) {
                // if it is today, set it to color/bold
                day_selected_txt_clr?.let {
                    holder.textView.setTextColor(ContextCompat.getColor(mContext, it))
                } ?: run{
                    holder.textView.setTextColor(ContextCompat.getColor(mContext, R.color.cwhite))
                }
                day_selected_bg?.let {
                    holder.rowLayout.setBackgroundResource(it)
                } ?:run {
                    holder.rowLayout.setBackgroundResource(R.drawable.ic_black_oval)
                }
                holder.rowLayout.isEnabled = true
            }

            textViewEvent.visibility = View.GONE

            if (eventDays != null) {
                for (eventDate in eventDays) {
                    if (eventDate.get(Calendar.DATE) == day && eventDate.get(Calendar.MONTH) == month && eventDate.get(Calendar.YEAR) == year) {
                        // mark this day for event
                        textViewEvent.visibility = View.VISIBLE
                        break
                    }
                }
            }
        }

        override fun onClick(v: View?) {
            selectedDate = days[adapterPosition]
            eventsHandler?.onDayClick(view = v, date = days[adapterPosition].time, adapterPosition)
            notifyDataSetChanged()
        }

        override fun onLongClick(v: View?): Boolean {
            eventsHandler?.onDayLongClick(
                view = v,
                date = days[adapterPosition].time,
                adapterPosition
            )
            return true
        }
    }

    fun setEventHandler(
        mEventsHandler: CalenderViewInterface.EventHandler
    ) {
        this.eventsHandler = mEventsHandler
    }

    fun eventShape(v: View, backgroundColor: Int) {
        val r = 8f
        val shape = ShapeDrawable(RoundRectShape(floatArrayOf(r, r, r, r, r, r, r, r), null, null))
        shape.paint.color = backgroundColor
        v.setBackground(shape)
    }

}
