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

class MonthAdapter(
    context: Context,
    private var eventsHandler: CalenderViewInterface?,
    private var monthConfig: MonthConfiguration?,
    private var months: List<String>,
    private var mPositon: Int
) : RecyclerView.Adapter<MonthAdapter.MyViewHolder>() {

    // for view inflation
    private val inflater: LayoutInflater
    private val mContext: Context
    private var selectedPosition = mPositon

    //Cell Configuration
    private var month_font: Int? = null
    private var month_txt_size: Int? = null
    private var month_selected_txt_clr: Int? = null
    private var month_unselected_txt_clr: Int? = null

    init {
        inflater = LayoutInflater.from(context)
        mContext = context
        if (monthConfig != null) {
            month_font = monthConfig!!.mFont
            month_txt_size = monthConfig!!.mTxtSize
            month_selected_txt_clr = monthConfig!!.mSelectedClr
            month_unselected_txt_clr = monthConfig!!.mUnSelectedClr
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthAdapter.MyViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_calendar_month_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MonthAdapter.MyViewHolder, position: Int) {
        holder.updateUi(holder,months[position],position)
    }

    override fun getItemCount(): Int {
        return months.size
    }

    fun updateSelectedPosMonth(positon: Int){
        selectedPosition = positon
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var textView: TextView
        var rowLayout: RelativeLayout

        init {
            textView = itemView.findViewById(R.id.control_calendar_month)
            rowLayout = itemView.findViewById(R.id.row_calendar_month_layout)
            rowLayout.setOnClickListener(this)

            if (month_txt_size != null) textView.textSize = month_txt_size!!.toFloat()
            if (month_unselected_txt_clr != null) textView.setTextColor(ContextCompat.getColor(mContext, month_unselected_txt_clr!!))
            if (month_font != null) textView.typeface = ResourcesCompat.getFont(mContext, month_font!!)
        }

        fun updateUi(holder: MyViewHolder, month: String, position: Int) {
            if (selectedPosition != position) {
                if (month_unselected_txt_clr != null)
                    holder.textView.setTextColor(ContextCompat.getColor(mContext, month_unselected_txt_clr!!))
                else
                    holder.textView.setTextColor(ContextCompat.getColor(mContext, R.color.greyed_out))
                holder.textView.text = month
            } else {
                if (month_selected_txt_clr != null)
                    holder.textView.setTextColor(ContextCompat.getColor(mContext, month_selected_txt_clr!!))
                else
                    holder.textView.setTextColor(ContextCompat.getColor(mContext, R.color.cblack))
                holder.textView.text = month
            }
        }

        override fun onClick(v: View?) {
            eventsHandler?.onMonthClick(view = v,month = months[adapterPosition],adapterPosition)
        }
    }

}
