package com.shahzadafridi.calendarview

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.Window
import android.view.WindowManager

object Util {

    //Int to DP
    val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

    var months = arrayListOf<String>().apply {
        add("Januari")
        add("Februari")
        add("Maart")
        add("April")
        add("Mei")
        add("Juni")
        add("Juli")
        add("Augustus")
        add("September")
        add("Oktober")
        add("November")
        add("December")
    }

    var weekDays = arrayListOf<String>().apply {
        add("ZO")
        add("MA")
        add("DI")
        add("WO")
        add("DO")
        add("VR")
        add("ZA")
    }
}