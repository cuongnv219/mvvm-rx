package com.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.core.anotation.ItemType
import com.core.rcv.Footer
import com.core.rcv.Header
import com.core.rcv.Shimmer

private interface BaseRcvAdapter<Item : Any, ViewBinding : ViewDataBinding> {

    fun getLayoutRes(viewType: Int): Int = 0
    fun bindFirstTime(binding: ViewBinding) {}
    fun bindView(binding: ViewBinding, item: Item, position: Int) {}
    fun bindFooter(binding: ViewBinding, item: Item?) {}
    fun bindHeader(binding: ViewBinding) {}
    fun bindShimmer(binding: ViewBinding) {}
    fun getLayoutRes() = 0
    fun getLayoutShimmer() = 0
    fun getLayoutHeader() = 0
    fun getLayoutFooter() = 0
}

abstract class BaseAdapter<Item : Any, ViewBinding : ViewDataBinding>(
) : RecyclerView.Adapter<BaseViewHolder<ViewBinding>>(), BaseRcvAdapter<Item, ViewBinding> {

    var items = mutableListOf<Item>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun submitData(list: List<Item>?) {
        items = list.orEmpty().toMutableList()
    }

    fun submitList(list: List<Item>?) {
        items = list.orEmpty().toMutableList()
    }

    fun getItemz(position: Int) = items.getOrNull(position)

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        val item = items.getOrNull(position)
        return when (item) {
            is Header -> {
                ItemType.HEADER
            }
            is Shimmer -> {
                ItemType.SHIMMER
            }
            is Footer -> {
                ItemType.FOOTER
            }
            else -> {
                ItemType.NORMAL
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewBinding> {
        val layout = when (viewType) {
            ItemType.HEADER -> {
                getLayoutHeader()
            }
            ItemType.SHIMMER -> {
                getLayoutShimmer()
            }
            ItemType.FOOTER -> {
                getLayoutFooter()
            }
            else -> {
                if (getLayoutRes() == 0) getLayoutRes(viewType) else getLayoutRes()
            }
        }
        return BaseViewHolder(DataBindingUtil.inflate<ViewBinding>(
                LayoutInflater.from(parent.context),
                layout,
                parent, false
        ).apply {
            bindFirstTime(this)
        })
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ViewBinding>, position: Int) {
        when (getItemViewType(position)) {
            ItemType.FOOTER -> {
                bindFooter(holder.binding, item = items.getOrNull(position))
            }
            ItemType.SHIMMER -> {
                bindShimmer(holder.binding)
            }
            ItemType.HEADER -> {
                bindHeader(holder.binding)
            }
            else -> {
                val item: Item? = items.getOrNull(position)
                item?.let {
                    bindView(holder.binding, it, position)
                }
            }
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
