package com.core

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.utils.DisposeBag
import com.utils.KeyboardUtils
import com.widget.Boast
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseActivity<V : ViewDataBinding> : AppCompatActivity() {

    lateinit var loading: AlertDialog
    lateinit var loading2: AlertDialog

    lateinit var binding: V

    private var isCancelable = false
    private var isCancelable2 = false

    protected val bag by lazy { DisposeBag.create() }

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    @LayoutRes
    open fun getLayoutIdLoading(): Int = -1

    open fun getThemResId(): Int = -1

    override fun onDestroy() {
        bag.dispose()
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        initDialog()
        initDialog2()
    }

    fun addDispose(vararg disposables: Disposable) {
        bag.add(*disposables)
    }

    @Throws
    open fun openFragment(resId: Int, fragmentClazz: Class<*>, args: Bundle?, addBackStack: Boolean) {
        val tag = fragmentClazz.simpleName
        try {
            val isExisted = supportFragmentManager.popBackStackImmediate(tag, 0)    // IllegalStateException
            if (!isExisted) {
                val fragment: Fragment
                try {
                    fragment = (fragmentClazz.asSubclass(Fragment::class.java)).newInstance().apply { arguments = args }

                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.add(resId, fragment, tag)

                    if (addBackStack) {
                        transaction.addToBackStack(tag)
                    }
                    transaction.commitAllowingStateLoss()

                } catch (e: InstantiationException) {
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
        vararg aniInt: Int,
    ) {
        val tag = fragmentClazz.simpleName
        try {
            val isExisted = supportFragmentManager.popBackStackImmediate(tag, 0)
            if (!isExisted) {
                val fragment: Fragment
                try {
                    fragment = (fragmentClazz.asSubclass(Fragment::class.java)).newInstance().apply { arguments = args }

                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.setCustomAnimations(aniInt[0], aniInt[1], aniInt[2], aniInt[3])

                    transaction.add(resId, fragment, tag)

                    if (addBackStack) {
                        transaction.addToBackStack(tag)
                    }
                    transaction.commitAllowingStateLoss()

                } catch (e: InstantiationException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun addFragment(fragmentClazz: Class<*>, args: Bundle? = null, addBackStack: Boolean = true, resId: Int = R.id.container_main) {
        val tag = fragmentClazz.canonicalName ?: fragmentClazz.simpleName
        try {
            val isExisted = supportFragmentManager.popBackStackImmediate(tag, 0)
            if (!isExisted) {
                val fragment: Fragment
                try {
                    fragment = (fragmentClazz.asSubclass(Fragment::class.java)).newInstance().apply { arguments = args }

                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.add(resId, fragment, tag)

                    if (addBackStack) {
                        transaction.addToBackStack(tag)
                    }
                    transaction.commitAllowingStateLoss()

                } catch (e: InstantiationException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun addFragment(fragment: Fragment, args: Bundle? = null, addBackStack: Boolean = true, resId: Int = R.id.container_main) {
        val tag = fragment::class.java.canonicalName ?: fragment::class.java.simpleName
        try {
            val isExisted = supportFragmentManager.popBackStackImmediate(tag, 0)
            if (!isExisted) {
                try {
                    args?.let {
                        fragment.arguments = it
                    }

                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.add(resId, fragment, tag)

                    if (addBackStack) {
                        transaction.addToBackStack(tag)
                    }
                    transaction.commitAllowingStateLoss()

                } catch (e: InstantiationException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun replaceFragment(fragmentClazz: Class<*>, args: Bundle? = null, addBackStack: Boolean = true, resId: Int = R.id.container_main) {
        val tag = fragmentClazz.canonicalName ?: fragmentClazz.simpleName
        try {
            val isExisted = supportFragmentManager.popBackStackImmediate(tag, 0)
            if (!isExisted) {
                val fragment: Fragment
                try {
                    fragment = (fragmentClazz.asSubclass(Fragment::class.java)).newInstance().apply { arguments = args }

                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(resId, fragment, tag)

                    if (addBackStack) {
                        transaction.addToBackStack(tag)
                    }
                    transaction.commitAllowingStateLoss()

                } catch (e: InstantiationException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun replaceFragment(fragment: Fragment, args: Bundle? = null, addBackStack: Boolean = true, resId: Int = R.id.container_main) {
        val tag = fragment::class.java.canonicalName ?: fragment::class.java.simpleName
        try {
            val isExisted = supportFragmentManager.popBackStackImmediate(tag, 0)
            if (!isExisted) {
                try {
                    fragment.apply { arguments = args }

                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(resId, fragment, tag)

                    if (addBackStack) {
                        transaction.addToBackStack(tag)
                    }
                    transaction.commitAllowingStateLoss()

                } catch (e: InstantiationException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Show toast
     * @param msg
     */
    fun toast(msg: String) = Boast.makeText(this, msg).show()

    fun toast(msg: String, duration: Int, cancelCurrent: Boolean) {
        Boast.makeText(this, msg, duration).show(cancelCurrent)
    }

    /**
     * Init dialog loading
     */
    private fun initDialog() {
        val builder: AlertDialog.Builder = if (getThemResId() != -1)
            AlertDialog.Builder(this, getThemResId()) else AlertDialog.Builder(this)

        builder.setCancelable(isCancelable)
        builder.setView(if (getLayoutIdLoading() == -1) R.layout.layout_loading_dialog_default else getLayoutIdLoading())
        loading = builder.create()
        loading.apply {
            window?.let {
                it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                it.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }
    }

    /**
     * Init dialog loading 2
     */
    private fun initDialog2() {
        val builder: AlertDialog.Builder = if (getThemResId() != -1)
            AlertDialog.Builder(this, getThemResId()) else AlertDialog.Builder(this)

        builder.setCancelable(isCancelable2)
        builder.setView(if (getLayoutIdLoading() == -1) R.layout.layout_loading_dialog_default else getLayoutIdLoading())
        loading2 = builder.create()
    }

    /**
     * Show dialog loading
     */
    open fun showDialog() {
        runOnUiThread {
            if (!loading.isShowing) {
                loading.show()
            }
        }
    }

    /**
     * Show dialog loading 2
     */
    open fun showDialog2() {
        runOnUiThread {
            if (!loading2.isShowing) {
                loading2.show()
            }
        }
    }

    /**
     * Hide dialog loading 2
     */
    open fun hideDialog2() {
        runOnUiThread {
            if (loading2.isShowing) {
                loading2.dismiss()
            }
        }
    }

    /**
     * Hide dialog loading
     */
    open fun hideDialog() {
        runOnUiThread {
            if (loading.isShowing) {
                loading.dismiss()
            }
        }
    }

    /**
     * Set cancelable dialog
     */
    fun setCancelableDialog(isCancelable: Boolean) {
        this.isCancelable = isCancelable
    }

    /**
     * Set cancelable dialog 2
     */
    fun setCancelableDialog2(isCancelable: Boolean) {
        this.isCancelable2 = isCancelable
    }

    fun hideKeyboard() {
        KeyboardUtils.hideKeyboard(this)
    }

    fun hideKeyboardOutSide(view: View) {
        KeyboardUtils.hideKeyBoardWhenClickOutSide(view, this)
    }

    fun hideKeyboardOutSideText(view: View) {
        KeyboardUtils.hideKeyBoardWhenClickOutSideText(view, this)
    }

    fun overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }

    fun overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }

    fun <VH : RecyclerView.ViewHolder> setUpRcv(rcv: RecyclerView, adapter: RecyclerView.Adapter<VH>) {
        rcv.setHasFixedSize(true)
        rcv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
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
        rcv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
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
        rcv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        rcv.adapter = adapter
        rcv.isNestedScrollingEnabled = isNestedScrollingEnabled
    }

    open fun clearAllBackStack() {
        val fm = supportFragmentManager
        for (i in 0 until fm.backStackEntryCount) {
            fm.popBackStack()
        }
    }

//    fun Subject<String>.receiveTextChangesFrom(editText: EditText) {
//        RxTextView.textChanges(editText)
//                .subscribe { newText -> this.onNext(newText.toString()) }
//                .disposedBy(bag)
//    }
//
//    fun Subject<Unit>.receiveClicksFrom(view: View) {
//        RxView.clicks(view)
//                .subscribe { this.onNext(Unit) }
//                .disposedBy(bag)
//    }

    protected fun isCurrentFragment(java: Class<*>, containerId: Int): Boolean {
        val curr = supportFragmentManager.findFragmentById(containerId) ?: return false
        return curr::class.java == java
    }

    protected fun isCurrentFragment(java: Class<*>, tagZ: String = "", containerId: Int): Boolean {
        val curr = if (tagZ.isEmpty()) {
            supportFragmentManager.findFragmentById(containerId) ?: return false
        } else {
            supportFragmentManager.findFragmentByTag(java::class.java.simpleName + tagZ) ?: return false
        }
        return curr::class.java == java
    }
}