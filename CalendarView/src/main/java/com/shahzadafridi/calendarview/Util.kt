package com.shahzadafridi.calendarview

import android.content.res.Resources

object Util {
    //Int to DP
    val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
}