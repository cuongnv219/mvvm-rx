package com.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.Executors

private interface BaseRecyclerAdapter<Item : Any, ViewBinding : ViewDataBinding> {

    fun getLayoutRes(viewType: Int): Int

    fun bindFirstTime(binding: ViewBinding) {}

    fun bindView(binding: ViewBinding, item: Item, position: Int) {}
}

abstract class BaseListAdapter<Item : Any, ViewBinding : ViewDataBinding>(
        callBack: DiffUtil.ItemCallback<Item>,
) : ListAdapter<Item, BaseViewHolder<ViewBinding>>(
        AsyncDifferConfig.Builder(callBack)
                .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
                .build()
), BaseRecyclerAdapter<Item, ViewBinding> {

    fun submitData(list: List<Item>?) {
        super.submitList(ArrayList<Item>(list ?: listOf()))
    }

    fun submitDataRef(list: List<Item>?) {
        super.submitList(list)
    }

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
        val item: Item? = getItem(position)
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

