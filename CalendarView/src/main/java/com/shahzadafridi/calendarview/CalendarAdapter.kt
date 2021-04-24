package com.shahzadafridi.calendarview

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class CalendarAdapter(
    context: Context,
    private val days: ArrayList<Calendar>, // days with events
    private val eventDays: HashSet<Calendar>?,
    private var eventsHandler: CalenderViewInterface.EventHandler?,
    private var cellConfig: CellConfiguration?
) : RecyclerView.Adapter<CalendarAdapter.MyViewHolder>() {

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
        cellConfig?.let {
            cell_font = it.cellFont
            cell_size = it.cellSize
            cell_bg = it.cellBg
            cell_txt_clr = it.cellTxtClr
            cell_txt_size = it.cellTxtSize
            cell_selected_txt_clr = it.cellSelectedClr
            cell_select_bg = it.cellSelectBg
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarAdapter.MyViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_calendar_day_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarAdapter.MyViewHolder, position: Int) {
        holder.updateUi(holder,days[position])
    }

    override fun getItemCount(): Int {
        return days.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {

        var textView: TextView
        var rowLayout: RelativeLayout

        init {
            textView = itemView.findViewById(R.id.control_calendar_day)
            rowLayout = itemView.findViewById(R.id.row_calendar_layout)
            rowLayout.setOnClickListener(this)
            rowLayout.setOnLongClickListener(this)
            cell_txt_size?.let {
                textView.textSize = it.toFloat()
            }
            cell_txt_clr?.let {
                textView.setTextColor(ContextCompat.getColor(mContext, it))
            }
            cell_font?.let {
                textView.typeface = ResourcesCompat.getFont(mContext, it)
            }
            cell_bg?.let {
                rowLayout.setBackgroundResource(it)
            }
        }

        fun updateUi(holder: MyViewHolder, date: Calendar) {

            val day = date.get(Calendar.DATE)
            val month = date.get(Calendar.MONTH)
            val year = date.get(Calendar.YEAR)

            // today
            val today = Calendar.getInstance()

            // set text
            holder.textView.text = date.get(Calendar.DATE).toString()


            if (month != today.get(Calendar.MONTH) || year != today.get(Calendar.YEAR)) {
                // if this day is outside current month, grey it out
                holder.textView.setTextColor(ContextCompat.getColor(mContext, R.color.greyed_out))
            } else if (day == today.get(Calendar.DATE)) {
                // if it is today, set it to blue/bold
                cell_selected_txt_clr?.let {
                    holder.textView.setTextColor(ContextCompat.getColor(mContext, it))
                }
                cell_select_bg?.let {
                    holder.rowLayout.setBackgroundResource(it)
                }
            }

            if (eventDays != null) {
                for (eventDate in eventDays) {
                    if (eventDate.get(Calendar.DATE) == day && eventDate.get(Calendar.MONTH) == month && eventDate.get(Calendar.YEAR) == year) {
                        // mark this day for event
                        cell_select_bg?.let {
                            holder.rowLayout.setBackgroundResource(it)
                        }
                        cell_selected_txt_clr?.let {
                            holder.textView.setTextColor(ContextCompat.getColor(mContext, it))
                        }
                        break
                    }
                }
            }
        }

        override fun onClick(v: View?) {
            eventsHandler?.onCellClick(view = v,date = days[adapterPosition].time,adapterPosition)
        }

        override fun onLongClick(v: View?): Boolean {
            eventsHandler?.onCellLongClick(view = v,date = days[adapterPosition].time,adapterPosition)
            return true
        }
    }

    fun setEventHandler(mEventsHandler: CalenderViewInterface.EventHandler){
        this.eventsHandler = mEventsHandler
    }

}
