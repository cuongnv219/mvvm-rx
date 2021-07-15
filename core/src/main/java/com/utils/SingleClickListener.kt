package com.utils

import android.os.SystemClock
import android.view.View

abstract class SingleClickListener : View.OnClickListener {

    abstract fun onSingleClick(v: View)

    override fun onClick(v: View) {
        if (!isEnableClick) {
            return
        }
        onSingleClick(v)
    }

    companion object {
        private var mLastClickTime: Long = 0
        val isEnableClick: Boolean
            get() {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 600) {
                    return false
                }
                mLastClickTime = SystemClock.elapsedRealtime()
                return true
            }
    }
}