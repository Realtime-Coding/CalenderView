package com.shahzadafridi.calendarview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemLongClickListener
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*

class CalendarView : LinearLayout, CalenderViewInterface {

    //Header
    private var headerDateFormat: String? = null
    private var header_font: Int? = null
    private var header_txt_clr: Int? = null
    private var header_bg_clr: Int? = null
    //Day
    private var day_font: Int? = null
    private var day_txt_clr: Int? = null
    private var day_bg_clr: Int? = null
    //Cell
    private var cell_font: Int? = null
    private var cell_size: Int? = null
    private var cell_bg: Int? = null
    private var cell_txt_clr: Int? = null
    private var cell_txt_size: Int? = null
    private var cell_selected_clr: Int? = null
    private var cell_select_bg: Int? = null
    private var cv_bg: Int? = null

    // current displayed month
    private val currentDate = Calendar.getInstance()

    //event handling
    private var eventHandler: CalenderViewInterface.EventHandler? = null

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
        private var FONT: Int? = null
        private var HEADER_TEXT_COLOR: Int? = null
        private var HEADER_BG_COLOR: Int? = null
        private var DAY_TEXT_COLOR: Int? = null
        private var DAY_BG_COLOR: Int? = null
        private var CELL_SIZE: Int? = null
        private var CELL_BG: Int? = null
        private var CALENDER_VIEW_BG: Int? = null
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
        val headerPanel = context.obtainStyledAttributes(attrs, R.styleable.CvHeaderPanel)
        val dayPanel = context.obtainStyledAttributes(attrs, R.styleable.CvDayPanel)
        val cellPanel = context.obtainStyledAttributes(attrs, R.styleable.CvCellPanel)
        try {
            // try to load provided date format, and fallback to default otherwise
            headerDateFormat = headerPanel.getString(R.styleable.CvHeaderPanel_dateFormat)
            header_font = headerPanel.getResourceId(R.styleable.CvHeaderPanel_header_font, 0)
            header_txt_clr = headerPanel.getResourceId(R.styleable.CvHeaderPanel_header_txt_clr,-1)
            header_bg_clr = headerPanel.getResourceId(R.styleable.CvHeaderPanel_header_bg_clr,-1)
            day_font = dayPanel.getResourceId(R.styleable.CvDayPanel_day_font, 0)
            day_txt_clr = dayPanel.getResourceId(R.styleable.CvDayPanel_day_txt_clr,-1)
            day_bg_clr = dayPanel.getResourceId(R.styleable.CvDayPanel_day_bg_clr,-1)
            cell_font = cellPanel.getResourceId(R.styleable.CvCellPanel_cell_font, 0)
            cell_size = cellPanel.getResourceId(R.styleable.CvCellPanel_cell_size,-1)
            cell_bg = cellPanel.getResourceId(R.styleable.CvCellPanel_cell_bg,-1)
            cell_select_bg = cellPanel.getResourceId(R.styleable.CvCellPanel_cell_select_bg,-1)
            cv_bg = cellPanel.getResourceId(R.styleable.CvCellPanel_cv_bg,-1)
            if (headerDateFormat == null)
                headerDateFormat = DATE_FORMAT
        } finally {
            headerPanel.recycle()
            dayPanel.recycle()
            cellPanel.recycle()
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
                eventHandler!!.onCellLongClick(view.getItemAtPosition(position) as Date,position,id)
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
        val sdf = SimpleDateFormat(headerDateFormat)
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
    fun setEventHandler(eventHandler: CalenderViewInterface.EventHandler?) {
        this.eventHandler = eventHandler
    }


    /**
     * Interface Methods
     */

    override fun builder(): CalendarView {
       return this
    }

    override fun withHeaderPanel(
        font: Int?,
        dateFormat: String?,
        textcolor: Int?,
        background: Int?
    ): CalendarView {
        header_font = font
        headerDateFormat = dateFormat
        header_txt_clr = textcolor
        header_bg_clr = background
        return this
    }

    override fun withHeaderPanleMargin(
        top: Float,
        bottom: Float,
        left: Float,
        right: Float
    ): CalendarView {
        return this
    }

    override fun withDayPanel(font: Int?, textColor: Int?, background: Int?): CalendarView {
        day_font = font
        day_txt_clr = textColor
        day_bg_clr = background
        return this
    }

    override fun withDayPanelMargin(
        top: Float,
        bottom: Float,
        left: Float,
        right: Float
    ): CalendarView {
        return this
    }

    override fun withCellPanel(
        font: Int?,
        textColor: Int?,
        selectedTextColor: Int,
        selectedBackground: Int,
        background: Int?
    ): CalendarView {
        cell_font = font
        cell_txt_clr = textColor
        cell_selected_clr = selectedTextColor
        cell_select_bg = selectedBackground
        cell_bg = background
        return this
    }

    override fun withCellPanelMargin(
        top: Float,
        bottom: Float,
        left: Float,
        right: Float
    ): CalendarView {
        return this
    }

    override fun withCalenderViewBackground(background: Int?): CalendarView {
        cv_bg = background
        return this
    }

    override fun build(): CalendarView {
        return this
    }

}
