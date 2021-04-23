package com.shahzadafridi

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemLongClickListener
import androidx.core.content.ContextCompat
import com.shahzadafridi.sample.R
import java.text.SimpleDateFormat
import java.util.*

class CalendarView : LinearLayout {

    // date format
    private var dateFormat: String? = null

    // current displayed month
    private val currentDate = Calendar.getInstance()

    //event handling
    private var eventHandler: EventHandler? = null

    // internal components
    private var header: LinearLayout? = null
    private var btnPrev: ImageView? = null
    private var btnNext: ImageView? = null
    private var txtDate: TextView? = null
    private var grid: GridView? = null

    // seasons' rainbow
    var rainbow = intArrayOf(
        R.color.summer,
        R.color.fall,
        R.color.winter,
        R.color.spring
    )

    var monthSeason = intArrayOf(2, 2, 3, 3, 3, 0, 0, 0, 1, 1, 1, 2)

    companion object {
        // for logging
        private const val LOGTAG = "Calendar View"

        // how many days to show, defaults to six weeks, 42 days
        private const val DAYS_COUNT = 42

        // default date format
        private const val DATE_FORMAT = "MMM yyyy"
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initControl(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initControl(context, attrs)
    }

    /**
     * Load control xml layout
     */
    private fun initControl(context: Context, attrs: AttributeSet?) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.control_calendar, this)
        loadDateFormat(attrs)
        assignUiElements()
        assignClickHandlers()
        updateCalendar()
    }

    private fun loadDateFormat(attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.CalendarView)
        try {
            // try to load provided date format, and fallback to default otherwise
            dateFormat = ta.getString(R.styleable.CalendarView_dateFormat)
            if (dateFormat == null) dateFormat = DATE_FORMAT
        } finally {
            ta.recycle()
        }
    }

    private fun assignUiElements() {
        // layout is inflated, assign local variables to components
        header = findViewById<View>(R.id.calendar_header) as LinearLayout
        btnPrev = findViewById<View>(R.id.calendar_prev_button) as ImageView
        btnNext = findViewById<View>(R.id.calendar_next_button) as ImageView
        txtDate = findViewById<View>(R.id.calendar_date_display) as TextView
        grid = findViewById<View>(R.id.calendar_grid) as GridView
    }

    private fun assignClickHandlers() {
        // add one month and refresh UI
        btnNext!!.setOnClickListener {
            currentDate.add(Calendar.MONTH, 1)
            updateCalendar()
        }

        // subtract one month and refresh UI
        btnPrev!!.setOnClickListener {
            currentDate.add(Calendar.MONTH, -1)
            updateCalendar()
        }

        // long-pressing a day
        grid!!.onItemLongClickListener =
            OnItemLongClickListener { view, cell, position, id -> // handle long-press
                if (eventHandler == null) return@OnItemLongClickListener false
                eventHandler!!.onDayLongPress(view.getItemAtPosition(position) as Date)
                true
            }
    }

    /**
     * Display dates correctly in grid
     */
    @JvmOverloads
    fun updateCalendar(events: HashSet<Calendar>? = null) {
        val cells = ArrayList<Calendar>()
        val calendar = currentDate.clone() as Calendar

        // determine the cell for current month's beginning
        calendar[Calendar.DAY_OF_MONTH] = 1
        val monthBeginningCell = calendar[Calendar.DAY_OF_WEEK] - 1

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell)

        // fill cells
        while (cells.size < DAYS_COUNT) {
            cells.add(Calendar.getInstance().apply {
                this.time = calendar.time
            })
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        // update grid
        grid!!.adapter = CalendarAdapter(context, cells, events)

        // update title
        val sdf = SimpleDateFormat(dateFormat)
        txtDate!!.text = sdf.format(currentDate.time)

        // set header color according to current season
        val month = currentDate[Calendar.MONTH]
        val season = monthSeason[month]
        val color = rainbow[season]
        header!!.setBackgroundColor(ContextCompat.getColor(context,color))
    }

    /**
     * Assign event handler to be passed needed events
     */
    fun setEventHandler(eventHandler: EventHandler?) {
        this.eventHandler = eventHandler
    }

    /**
     * This interface defines what events to be reported to
     * the outside world
     */
    interface EventHandler {
        fun onDayLongPress(date: Date?)
    }
}
