# CalendarView
A sample library which gives you custom design CalendarView with dialog functionality and event handlers.
 

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
- Click me: [@realtimecoding](https://www.youtube.com/watch?v=rI5MAfJp7Sk)

You can subscribe the channel for more android related stuffs as I am planning to work on android tutorials.

### Contributing

Pull requests are welcomed!


