package com.shahzadafridi.calendarview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.AdapterView.OnItemClickListener
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import java.text.SimpleDateFormat
import java.util.*

class CalendarView : LinearLayout, CalenderViewInterface {

    //Header
    private var headerDateFormat: String? = null
    private var header_font: Int? = null
    private var header_txt_clr: Int? = null
    private var header_bg_clr: Int? = null
    private var header_next_icon: Int? = null
    private var header_previous_icon: Int? = null

    //Day
    private var day_font: Int? = null
    private var day_txt_clr: Int? = null
    private var day_bg_clr: Int? = null
    private var day_txt_size: Int? = null

    //Cell
    private var cell_font: Int? = null
    private var cell_size: Int? = null
    private var cell_bg: Int? = null
    private var cell_txt_clr: Int? = null
    private var cell_txt_size: Int? = null
    private var cell_selected_txt_clr: Int? = null
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
    private var dateRl: RelativeLayout? = null
    private var grid: GridView? = null
    private var adapter: CalendarAdapter? = null

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

        //HEADER
        private const val DATE_FORMAT = "MMM yyyy"
        private var HEADER_FONT: Int? = R.font.pfd_cond_regular
        private var HEADER_TEXT_COLOR: Int? = R.color.cblack
        private var HEADER_BG_COLOR: Int? = R.color.cwhite
        private var HEADER_NEXT_ICON: Int? = R.drawable.next_icon
        private var HEADER_PREVIOUS_ICON: Int? = R.drawable.previous_icon
        //DAY
        private var DAY_TEXT_COLOR: Int? = R.color.cblack
        private var DAY_BG_COLOR: Int? = R.color.cwhite
        private var DAY_FONT: Int? = R.font.pfd_cond_regular
        private var DAY_TEXT_SIZE: Int? = 16
        //CELL
        private var CELL_FONT: Int? = R.font.pfd_cond_regular
        private var CELL_BG: Int? = R.color.summer
        private var CELL_SIZE: Int? = 14
        private var CELL_TEXT_COLOR: Int? = R.color.cblack
        private var CELL_TEXT_SIZE: Int? = 14
        private var CELL_SELECTED_TEXT_COLOR: Int? = R.color.cwhite
        private var CELL_SELECTED_BG: Int? = R.color.cblack

        private var CALENDER_VIEW_BG: Int? = R.color.cwhite
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
        val calenderViewAttr = context.obtainStyledAttributes(attrs, R.styleable.CalendarView)
        try {
            // try to load provided date format, and fallback to default otherwise
            headerDateFormat = calenderViewAttr.getString(R.styleable.CalendarView_dateFormat)
            header_font = calenderViewAttr.getResourceId(R.styleable.CalendarView_header_font,-1)
            header_txt_clr = calenderViewAttr.getResourceId(R.styleable.CalendarView_header_txt_clr, -1)
            header_bg_clr = calenderViewAttr.getResourceId(R.styleable.CalendarView_header_bg_clr, -1)
            header_next_icon = calenderViewAttr.getResourceId(R.styleable.CalendarView_header_next_icon, -1)
            header_previous_icon = calenderViewAttr.getResourceId(R.styleable.CalendarView_header_previous_icon, -1)
            if (header_font == -1) header_font = HEADER_FONT
            if (header_txt_clr == -1) header_txt_clr = HEADER_TEXT_COLOR
            if (header_bg_clr == -1) header_bg_clr = HEADER_BG_COLOR
            if (header_next_icon == -1) header_next_icon = HEADER_NEXT_ICON
            if (header_previous_icon == -1) header_previous_icon = HEADER_PREVIOUS_ICON
            day_font = calenderViewAttr.getResourceId(R.styleable.CalendarView_day_font, -1)
            day_txt_clr = calenderViewAttr.getResourceId(R.styleable.CalendarView_day_txt_clr, -1)
            day_bg_clr = calenderViewAttr.getResourceId(R.styleable.CalendarView_day_bg_clr, -1)
            day_txt_size = calenderViewAttr.getResourceId(R.styleable.CalendarView_day_txt_size, -1)
            if (day_font == -1) day_font = DAY_FONT
            if (day_txt_clr == -1) day_txt_clr = DAY_TEXT_COLOR
            if (day_bg_clr == -1) day_bg_clr = DAY_BG_COLOR
            if (day_txt_size == -1) day_txt_size = DAY_TEXT_SIZE
            cell_font = calenderViewAttr.getResourceId(R.styleable.CalendarView_cell_font, -1)
            cell_size = calenderViewAttr.getResourceId(R.styleable.CalendarView_cell_size, -1)
            cell_txt_clr = calenderViewAttr.getResourceId(R.styleable.CalendarView_cell_txt_clr, -1)
            cell_txt_size = calenderViewAttr.getResourceId(R.styleable.CalendarView_cell_text_size, -1)
            cell_bg = calenderViewAttr.getResourceId(R.styleable.CalendarView_cell_bg, -1)
            cell_select_bg = calenderViewAttr.getResourceId(R.styleable.CalendarView_cell_select_bg, -1)
            cell_selected_txt_clr = calenderViewAttr.getResourceId(R.styleable.CalendarView_cell_select_txt_clr, -1)
            cv_bg = calenderViewAttr.getResourceId(R.styleable.CalendarView_cv_bg, -1)
            if (cell_font == -1) cell_font = CELL_FONT
            if (cell_size == -1) cell_size = CELL_SIZE
            if (cell_txt_clr == -1) cell_txt_clr = CELL_TEXT_COLOR
            if (cell_txt_size == -1) cell_txt_size = CELL_TEXT_SIZE
            if (cell_bg == -1) cell_bg = CELL_BG
            if (cell_select_bg == -1) cell_select_bg = CELL_SELECTED_BG
            if (cell_selected_txt_clr == -1) cell_selected_txt_clr = CELL_SELECTED_TEXT_COLOR
            if (cv_bg == -1) cv_bg = CALENDER_VIEW_BG
            if (headerDateFormat == null) headerDateFormat = DATE_FORMAT

        } finally {
            calenderViewAttr.recycle()
        }
    }

    private fun assignUiElements() {
        // layout is inflated, assign local variables to components
        header = findViewById<View>(R.id.calendar_header) as LinearLayout
        btnPrev = findViewById<View>(R.id.calendar_prev_button) as ImageView
        btnNext = findViewById<View>(R.id.calendar_next_button) as ImageView
        txtDate = findViewById<View>(R.id.calendar_date_display) as TextView
        dateRl = findViewById<RelativeLayout>(R.id.calendar_date_rl)
        grid = findViewById<View>(R.id.calendar_grid) as GridView
    }

    private fun assignClickHandlers() {
        // add one month and refresh UI
        btnNext!!.setOnClickListener {
            currentDate.add(Calendar.MONTH, 1)
            updateCalendar()
            eventHandler!!.onNextClick(it)
        }

        // subtract one month and refresh UI
        btnPrev!!.setOnClickListener {
            currentDate.add(Calendar.MONTH, -1)
            updateCalendar()
            eventHandler!!.onPreviousClick(it)
        }

        // long-pressing a day
        grid!!.onItemLongClickListener = OnItemLongClickListener { view, cell, position, id -> // handle long-press
            if (eventHandler == null) return@OnItemLongClickListener false
            eventHandler!!.onCellLongClick(view, view.getItemAtPosition(position) as Date, position, id)
            true
        }

        grid!!.onItemClickListener = OnItemClickListener { view, cell, position, id ->
            if (eventHandler == null) return@OnItemClickListener
            eventHandler!!.onCellClick(view, view.getItemAtPosition(position) as Date, position, id)
        }
    }

    /**
     * Display dates correctly in grid
     */
    @JvmOverloads
    fun updateCalendar(events: HashSet<Calendar>? = null): CalendarView {
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
        adapter = CalendarAdapter(context, cells, events)
        grid!!.adapter = adapter

        // update title
        val sdf = SimpleDateFormat(headerDateFormat)
        txtDate!!.text = sdf.format(currentDate.time)

        // set header color according to current season
        val month = currentDate[Calendar.MONTH]
        val season = monthSeason[month]
        val color = rainbow[season]
        header!!.setBackgroundColor(ContextCompat.getColor(context, color))
        header_bg_clr?.let {
            header!!.setBackgroundColor(ContextCompat.getColor(context, it))
        }

        return this
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
            nextIcon: Int?,
            previousIcon: Int?,
            background: Int?
    ): CalendarView {
        header_font = font
        headerDateFormat = dateFormat
        header_txt_clr = textcolor
        header_bg_clr = background

        header_font?.let {
            txtDate!!.typeface = ResourcesCompat.getFont(context,it)
        }
        header_txt_clr?.let {
            txtDate!!.setTextColor(ContextCompat.getColor(context,it))
        }
        header_bg_clr?.let {
            dateRl!!.background = ContextCompat.getDrawable(context,it)
        }
        header_next_icon?.let {
            btnNext!!.background = ContextCompat.getDrawable(context,it)
        }
        header_previous_icon?.let {
            btnPrev!!.background = ContextCompat.getDrawable(context,it)
        }
        return this
    }

    override fun withHeaderPanleMargin(
            top: Int,
            bottom: Int,
            left: Int,
            right: Int
    ): CalendarView {
        return this
    }

    override fun withDayPanel(font: Int?, textColor: Int?, background: Int?): CalendarView {
        day_font = font
        day_txt_clr = textColor
        day_bg_clr = background

        day_font?.let { font ->
            header!!.children.iterator().forEach {
                (it as TextView).typeface = ResourcesCompat.getFont(context,font)
            }
        }
        day_txt_clr?.let { clr ->
            header!!.children.iterator().forEach {
                (it as TextView).setTextColor(ContextCompat.getColor(context,clr))
            }
        }
        day_bg_clr?.let {
            header!!.background = ContextCompat.getDrawable(context,it)
        }
        return this
    }

    override fun withDayPanelMargin(
            top: Int,
            bottom: Int,
            left: Int,
            right: Int
    ): CalendarView {
        setMargin(dateRl!!, left, right, top, bottom)
        return this
    }


    override fun withCellPanel(
            font: Int?,
            textColor: Int?,
            textSize: Int,
            selectedTextColor: Int,
            selectedBackground: Int,
            background: Int?
    ): CalendarView {
        cell_font = font
        cell_txt_clr = textColor
        cell_txt_size = textSize
        cell_selected_txt_clr = selectedTextColor
        cell_select_bg = selectedBackground
        cell_bg = background
        adapter!!.setCellConfig(cell_txt_clr,cell_bg,cell_selected_txt_clr,cell_select_bg,cell_font,cell_size,cell_txt_size)
        return this
    }

    override fun withCellPanelMargin(
            top: Int,
            bottom: Int,
            left: Int,
            right: Int
    ): CalendarView {
        setMargin(grid!!, left, right, top, bottom)
        return this
    }

    override fun withCalenderViewBackground(background: Int?): CalendarView {
        cv_bg = background
        this.background = ContextCompat.getDrawable(context,cv_bg!!)
        return this
    }

    override fun build(): CalendarView {
        return this
    }

    //Set Margin to view.
    fun setMargin(view: View, left: Int, right: Int, top: Int, bottom: Int) {
        val params = view.getLayoutParams() as RelativeLayout.LayoutParams
        params.setMargins(left, top, right, bottom)
        view.setLayoutParams(params)
    }

}
