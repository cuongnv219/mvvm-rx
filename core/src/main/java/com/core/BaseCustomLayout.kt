package com.core

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseCustomLayout : RelativeLayout {

    @LayoutRes
    abstract fun getLayoutId(): Int

    protected abstract fun updateUI()

    open fun getStyleableId(): IntArray? {
        return null
    }

    constructor(context: Context) : super(context) {
        setInitLayout()
    }

    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        initAttr(attr)
        setInitLayout()
    }

    private fun setInitLayout() {
        LayoutInflater.from(context).inflate(getLayoutId(), this, true)
        updateUI()
    }

    private fun initAttr(attr: AttributeSet) {
        getStyleableId()?.let {
            val a = context.theme.obtainStyledAttributes(attr, getStyleableId() ?: return, 0, 0)
            try {
                initDataFromStyleable(a)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                a.recycle()
            }
        }
    }

    fun <VH : RecyclerView.ViewHolder> setUpRcv(rcv: RecyclerView, adapter: RecyclerView.Adapter<VH>) {
        rcv.setHasFixedSize(true)
        rcv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        rcv.adapter = adapter
    }

    fun <VH : RecyclerView.ViewHolder> setUpRcv(
            rcv: RecyclerView,
            adapter:
            RecyclerView.Adapter<VH>,
            isHasFixedSize: Boolean,
            isNestedScrollingEnabled: Boolean,
    ) {
        rcv.setHasFixedSize(isHasFixedSize)
        rcv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        rcv.adapter = adapter
        rcv.isNestedScrollingEnabled = isNestedScrollingEnabled
    }

    fun <VH : RecyclerView.ViewHolder> setUpRcv(
            rcv: RecyclerView,
            adapter:
            RecyclerView.Adapter<VH>,
            isNestedScrollingEnabled: Boolean,
    ) {
        rcv.setHasFixedSize(true)
        rcv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        rcv.adapter = adapter
        rcv.isNestedScrollingEnabled = isNestedScrollingEnabled
    }

    open fun initDataFromStyleable(a: TypedArray) {}
}