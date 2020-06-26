package com.core

import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.utils.DisposeBag
import com.utils.ext.disposedBy
import com.widget.Boast
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.Subject

abstract class BaseFragment<V : ViewDataBinding> : Fragment(), ViewTreeObserver.OnGlobalLayoutListener {

    protected var binding: V? = null

    private var rootView: View? = null

    protected val bag by lazy { DisposeBag.create() }

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onGlobalLayout() {
        rootView!!.viewTreeObserver.removeOnGlobalLayoutListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    fun addDispose(vararg disposables: Disposable) {
        bag.add(*disposables)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layoutId = getLayoutId()
        if (rootView != null) {
            //todo fix something
            val parent = rootView!!.parent as ViewGroup?
            parent?.removeView(rootView)
        } else {
            try {
                binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
                rootView = if (binding != null) {
                    binding!!.root
                } else {
                    inflater.inflate(layoutId, container, false)
                }
                rootView!!.viewTreeObserver.addOnGlobalLayoutListener(this)
//                updateUI(savedInstanceState)
            } catch (e: InflateException) {
                e.printStackTrace()
            }
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        hideKeyboardOutSideText(view)
        view.isClickable = true
        view.isFocusable = true
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun onDestroy() {
        bag.dispose()
        super.onDestroy()
    }

    fun <VH : RecyclerView.ViewHolder> setUpRcv(rcv: RecyclerView, adapter: RecyclerView.Adapter<VH>) {
        rcv.setHasFixedSize(true)
        rcv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        rcv.adapter = adapter
    }

    fun <VH : RecyclerView.ViewHolder> setUpRcv(
            rcv: RecyclerView, adapter:
            RecyclerView.Adapter<VH>,
            isHasFixedSize: Boolean,
            isNestedScrollingEnabled: Boolean
    ) {
        rcv.setHasFixedSize(isHasFixedSize)
        rcv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        rcv.adapter = adapter
        rcv.isNestedScrollingEnabled = isNestedScrollingEnabled
    }

    fun <VH : RecyclerView.ViewHolder> setUpRcv(
            rcv: RecyclerView, adapter:
            RecyclerView.Adapter<VH>,
            isNestedScrollingEnabled: Boolean
    ) {
        rcv.setHasFixedSize(true)
        rcv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        rcv.adapter = adapter
        rcv.isNestedScrollingEnabled = isNestedScrollingEnabled
    }

    @Throws
    open fun openFragment(
            resId: Int,
            fragmentClazz: Class<*>,
            args: Bundle?,
            addBackStack: Boolean
    ) {
        val tag = fragmentClazz.simpleName
        try {
            val isExisted = childFragmentManager.popBackStackImmediate(tag, 0)    // IllegalStateException
            if (!isExisted) {
                val fragment: Fragment
                try {
                    fragment = (fragmentClazz.asSubclass(Fragment::class.java)).newInstance().apply { arguments = args }

                    val transaction = childFragmentManager.beginTransaction()
                    //transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                    transaction.add(resId, fragment, tag)

                    if (addBackStack) {
                        transaction.addToBackStack(tag)
                    }
                    transaction.commitAllowingStateLoss()

                } catch (e: java.lang.InstantiationException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws
    open fun openFragment(
            resId: Int, fragmentClazz: Class<*>, args: Bundle?, addBackStack: Boolean,
            vararg aniInt: Int
    ) {
        val tag = fragmentClazz.simpleName
        try {
            val isExisted = childFragmentManager.popBackStackImmediate(tag, 0)    // IllegalStateException
            if (!isExisted) {
                val fragment: Fragment
                try {
                    fragment = (fragmentClazz.asSubclass(Fragment::class.java)).newInstance().apply { arguments = args }

                    val transaction = childFragmentManager.beginTransaction()
                    transaction.setCustomAnimations(aniInt[0], aniInt[1], aniInt[2], aniInt[3])

                    transaction.add(resId, fragment, tag)

                    if (addBackStack) {
                        transaction.addToBackStack(tag)
                    }
                    transaction.commitAllowingStateLoss()

                } catch (e: java.lang.InstantiationException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun toast(msg: String) {
        context?.let {
            Boast.makeText(it, msg).show()
        }
    }

    fun toast(msg: String, duration: Int, cancelCurrent: Boolean) {
        context?.let {
            Boast.makeText(it, msg, duration).show(cancelCurrent)
        }
    }

    fun showDialog() {
        activity?.let {
            if (it is BaseActivity<*>) {
                it.showDialog()
            }
        }
    }

    fun hideDialog() {
        activity?.let {
            if (it is BaseActivity<*>) {
                it.hideDialog()
            }
        }
    }

    fun hideKeyboard() {
        activity?.let {
            if (it is BaseActivity<*>) {
                it.hideKeyboard()
            }
        }
    }

    fun hideKeyboardOutSide(view: View) {
        activity?.let {
            if (it is BaseActivity<*>) {
                it.hideKeyboardOutSide(view)
            }
        }
    }

    fun hideKeyboardOutSideText(view: View) {
        activity?.let {
            if (it is BaseActivity<*>) {
                it.hideKeyboardOutSideText(view)
            }
        }
    }

    fun onBackPressed() {
        activity?.onBackPressed()
    }

    open fun clearAllBackStack() {
        activity?.let {
            if (it is BaseActivity<*>) {
                it.clearAllBackStack()
            }
        }
    }

    fun finish() {
        activity?.finish()
    }

    fun Subject<String>.receiveTextChangesFrom(editText: EditText) {
        RxTextView.textChanges(editText)
                .subscribe { newText -> this.onNext(newText.toString()) }
                .disposedBy(bag)
    }

    fun Subject<Unit>.receiveClicksFrom(view: View) {
        RxView.clicks(view)
                .subscribe { this.onNext(Unit) }
                .disposedBy(bag)
    }
}