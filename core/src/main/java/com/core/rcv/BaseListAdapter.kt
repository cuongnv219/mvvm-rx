package com.core.rcv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.core.anotation.ItemType
import java.util.concurrent.Executors

private interface BaseRecyclerAdapter<Item : Any, ViewBinding : ViewDataBinding> {

    fun getLayoutRes(viewType: Int) = 0

    fun getLayoutRes() = 0

    fun getLayoutShimmer() = 0

    fun getLayoutHeader() = 0

    fun getLayoutFooter() = 0

    /**
     * bind first time
     * use for set item onClickListener, something only set one time
     */
    fun bindFirstTime(binding: ViewBinding) {}
    fun bindView(binding: ViewBinding, item: Item, position: Int) {}
    fun bindFooter(binding: ViewBinding) {}
    fun bindHeader(binding: ViewBinding) {}
    fun bindShimmer(binding: ViewBinding) {}
}

abstract class BaseListAdapter<Item : Any, ViewBinding : ViewDataBinding>(
        callBack: DiffUtil.ItemCallback<Item>,
) : ListAdapter<Item, BaseListViewHolder<ViewBinding>>(
        AsyncDifferConfig.Builder(callBack)
                .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
                .build()
), BaseRecyclerAdapter<Item, ViewBinding> {

    protected fun initShimmer() {
        val shimmers = mutableListOf<Item>()
        repeat(7) { shimmers.add(Shimmer() as Item) }
        submitShimmer(shimmers)
    }

    override fun submitList(list: List<Item>?) {
        super.submitList(list.orEmpty())
    }

    fun submitShimmer(list: MutableList<Item>?) {
        super.submitList(list.orEmpty())
    }

    fun submitData(list: List<Item>?) {
        super.submitList(ArrayList<Item>(list ?: listOf()))
    }

    fun addMore(list: List<Item>?) {
        submitData(currentList.plus(list.orEmpty()))
    }

    fun refreshListItem(item: Item) {
        val position = currentList.indexOf(item)
        notifyItemChanged(position)
    }

    //tham chieu gia tri
    fun submitDataReference(list: List<Item>?) {
        super.submitList(list ?: listOf())
    }

    override fun getItemViewType(position: Int): Int {
        val item = currentList.getOrNull(position)
        return if (item is Header) {
            ItemType.HEADER
        } else if (item is Shimmer) {
            ItemType.SHIMMER
        } else if (item is Footer || position == itemCount - 1) {
            ItemType.FOOTER
        } else {
            ItemType.NORMAL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseListViewHolder<ViewBinding> {
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
                getLayoutRes()
            }
        }
        return BaseListViewHolder(DataBindingUtil.inflate<ViewBinding>(
                LayoutInflater.from(parent.context),
                layout,
                parent, false
        ).apply {
            bindFirstTime(this)
        })
    }

    override fun onBindViewHolder(holder: BaseListViewHolder<ViewBinding>, position: Int) {
        when (getItemViewType(position)) {
            ItemType.FOOTER -> {
                bindFooter(holder.binding)
            }
            ItemType.SHIMMER -> {
                bindShimmer(holder.binding)
            }
            ItemType.HEADER -> {
                bindHeader(holder.binding)
            }
            else -> {
                val item: Item? = getItem(position)
                item?.let {
                    bindView(holder.binding, it, position)
                }
            }
        }
        holder.binding.executePendingBindings()
    }

    fun getItemz(position: Int): Item? = super.getItem(position)
}

open class BaseListViewHolder<ViewBinding : ViewDataBinding>(
        val binding: ViewBinding,
) : RecyclerView.ViewHolder(binding.root)
