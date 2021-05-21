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

    /**
     * CREATE CUSTOM DIALOG
     */

    fun onCreateDialog(context: Context, layout: CalendarView, cancelable: Boolean): Dialog {
        val dialog = Dialog(context, android.R.style.Theme_Dialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(layout)
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.attributes.windowAnimations = R.style.AnimatedDialogStyle
        dialog.window!!.setGravity(Gravity.TOP)
        dialog.setCancelable(cancelable)
        return dialog
    }
}