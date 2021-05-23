# CalendarView
A simple library which gives you custom design CalendarView with dialog functionality and event handlers.
 

### 1: CalendarView

<table>
  <tr>
    <td>Demo Screen</td>
  </tr>
  <tr>
    <td><img src="https://github.com/shahzadafridi/CalenderView/blob/master/demo-images/demo-image-1.png" width=270 height=480></td>
  </tr>
 </table>
 
#### 1.1: Import Library

```groovy
implementation 'com.github.shahzadafridi:CalenderView:1.1.2'
```
### Method A
#### 1.2: XML File
```xml
<com.shahzadafridi.calendarview.CalendarView
        android:id="@+id/calendar_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```
#### 1.3: Kotlin Code

```kotlin

  var calendarView = findViewBydId(R.id.calendar_view)

  calendarView.builder()
      .withYearPanel("yyyy",R.color.black,42,R.font.titillium_web_semibold)
      .withBackButton(true,R.drawable.ic_up_round)
      .withMonthPanel(
          font = R.font.titillium_web_semibold,
          textSize = 20,
          selectedTextColor = R.color.black,
          unSelectedTextColor = R.color.greyed_out,
          background = R.color.white,
          months = monthsNameList
      )
      .withWeekPanel(
          font = R.font.titillium_web_semibold,
          textColor = R.color.black,
          textSize = 14,
          background = R.color.white,
          weekDays = weekDaysNameList
      )
      .withDayPanel(
          font = R.font.titillium_web_semibold,
          textColor = R.color.black,
          textSize = 16,
          selectedTextColor = R.color.white,
          selectedBackground = R.drawable.ic_green_oval,
          background = R.color.white
      )
      .withCalenderViewBg(R.drawable.rect_lr_wround_bg)
      .withEvents(events, R.color.green)
      .buildCalendar()
```

### OR

### Method B
#### 1.2: XML File

```xml
    <com.shahzadafridi.calendarview.CalendarView
        android:id="@+id/calendar_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:back_button_bg="@drawable/ic_up_round"
        app:cv_bg="@drawable/rect_lr_wround_bg"
        app:day_bg="@color/white"
        app:day_font="@font/titillium_web_semibold"
        app:day_select_bg="@drawable/ic_green_oval"
        app:day_select_txt_clr="@color/white"
        app:day_text_size="5dp"
        app:day_txt_clr="@color/cblack"
        app:is_back_button_show="true"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:month_bg="@color/black"
        app:month_font="@font/titillium_web_semibold"
        app:month_selected_txt_clr="@color/green"
        app:month_txt_size="8dp"
        app:month_unselect_txt_clr="@color/white"
        app:week_bg_clr="@color/black"
        app:week_font="@font/titillium_web_semibold"
        app:week_txt_clr="@color/white"
        app:week_txt_size="6dp"
        app:year_date_Formate="MM YYYY"
        app:year_text_clr="@color/cblack"
        app:year_text_font="@font/titillium_web_semibold"
        app:year_text_size="12dp" />
```

#### 1.3: Kotlin Code

```kotlin

  var calendarView = findViewBydId(R.id.calendar_view)

  calendarView.builder()
      .withEvents(events, R.color.green)
      .buildCalendar()
```

#### 1.4: CalendarView Event Handles

```kotlin
calendarView.setEventHandler(object : CalenderViewInterface.EventHandler {

    override fun onDayClick(view: View?, date: Date, position: Int) {
        val df = SimpleDateFormat.getDateInstance()
        Toast.makeText(this@MainActivity, df.format(date), Toast.LENGTH_SHORT).show()
        Log.e("TEST", "onDayClick")
    }

    override fun onDayLongClick(view: View?, date: Date, position: Int) {
        val df = SimpleDateFormat.getDateInstance()
        Toast.makeText(this@MainActivity, df.format(date), Toast.LENGTH_SHORT).show()
        Log.e("TEST", "onDayLongClick")
    }

    override fun onBackClick(view: View?) {
        Log.e("TEST", "onBackClick")
        dialog?.dismiss()
    }

    override fun onMonthClick(view: View?, month: String, position: Int) {
        Toast.makeText(this@MainActivity, month, Toast.LENGTH_SHORT).show()
        Log.e("TEST", "onMonthClick")
    }
})
```

### 2: CalendarViewDialog

<table>
  <tr>
    <td>Demo Screen</td>
  </tr>
  <tr>
    <td><img src="https://github.com/shahzadafridi/CalenderView/blob/master/demo-images/demo-image-2.png" width=270 height=480></td>
  </tr>
 </table>
 
#### 2.1: Kotlin Code
```kotlin
        var dialog = CalendarViewDialog(this) // this is context
        dialog!!.setCancelable(false)
        dialog!!.show()
        var calendarView = dialog!!.getCalendarView()
        // Follow 1.3 Step. It is same configuration.
```

#### Methods Info
- **withYearPanel** can change Date formate, Text Color, Text Size, Text Font
- **withBackButton** can close CalendarView with Back Button
- **withMonthPanel** can change the month Text font, Text Size, Selected Text Color, Unselected Text Color, month names, Background of month layout
- **withWeekPanel** can change the week days Text font, Text Color, Text Size,days name, Background of week layout
- **withDayPanel** can change the day Text font, Text Color, Text Size, Selected background, Selected Text Color, Background of day
- **withCalenderViewBg** can change the CalendarView background
- **withEvents** can gives events dates to calendarView which shows small dot indcaotr below of day
 
 
#### Watch Demo Video
![Logo](https://yt3.ggpht.com/yti/ANoDKi5JnCxTB3YJkKnT2Rz_E2wuo6x619ACuiDmuN6n=s108-c-k-c0x00ffffff-no-rj)

Click me: [@realtimecoding](https://www.youtube.com/watch?v=rI5MAfJp7Sk)

You can subscribe the channel for more android related stuffs as I am planning to work on android tutorials.

### Contributing

Pull requests are welcomed!

## License

[MIT](https://choosealicense.com/licenses/mit/)



