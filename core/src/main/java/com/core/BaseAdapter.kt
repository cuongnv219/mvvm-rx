package com.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

private interface BaseRcvAdapter<Item : Any, ViewBinding : ViewDataBinding> {

    fun getLayoutRes(viewType: Int): Int

    fun bindFirstTime(binding: ViewBinding) {}

    fun bindView(binding: ViewBinding, item: Item, position: Int) {}
}

abstract class BaseAdapter<Item : Any, ViewBinding : ViewDataBinding>(
) : RecyclerView.Adapter<BaseViewHolder<ViewBinding>>(), BaseRcvAdapter<Item, ViewBinding> {

    var items = listOf<Item>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun submitData(list: List<Item>?) {
        items = list ?: listOf()
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewBinding> {
        return BaseViewHolder(DataBindingUtil.inflate<ViewBinding>(
                LayoutInflater.from(parent.context),
                getLayoutRes(viewType),
                parent, false
        ).apply {
            bindFirstTime(this)
        })
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ViewBinding>, position: Int) {
        val item: Item? = items.get(position)
//        holder.binding.setVariable(BR.item, item)
        item?.let {
            bindView(holder.binding, it, position)
        }

        holder.binding.executePendingBindings()
    }
}

open class BaseViewHolder<ViewBinding : ViewDataBinding>(
        val binding: ViewBinding,
) : RecyclerView.ViewHolder(binding.root)

interface OnItemClickListener<M> {
    fun onItemClick(view: View?, position: Int, item: M)
}
