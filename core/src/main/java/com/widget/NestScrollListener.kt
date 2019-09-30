package com.widget

import androidx.core.widget.NestedScrollView

abstract class NestScrollListener : NestedScrollView.OnScrollChangeListener {

    override fun onScrollChange(v: NestedScrollView, p1: Int, scrollY: Int, p3: Int, p4: Int) {
        if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
            onLoadMore()
        }
    }

    abstract fun onLoadMore()
}
