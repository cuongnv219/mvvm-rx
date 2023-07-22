package com.core

import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.utils.DisposeBag
import com.utils.ext.observe
import com.widget.Boast
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseFragment<V : ViewDataBinding> : Fragment(), ViewTreeObserver.OnGlobalLayoutListener {

    protected lateinit var binding: V

    private var rootView: View? = null

    protected val bag by lazy { DisposeBag.create() }

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onGlobalLayout() {
        rootView?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
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
                rootView = binding.root
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
        view.isClickable = true
        view.isFocusable = true
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun onDestroy() {
        bag.dispose()
        super.onDestroy()
    }

    @Throws
    open fun openFragment(
            resId: Int,
            fragmentClazz: Class<*>,
            args: Bundle?,
            addBackStack: Boolean,
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
            vararg aniInt: Int,
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

    fun replaceFragment(fragmentClazz: Class<*>, args: Bundle? = null, addBackStack: Boolean = true, resId: Int = R.id.container_main) {
        val tag = fragmentClazz.canonicalName ?: fragmentClazz.simpleName
        try {
            val isExisted = activity?.supportFragmentManager?.popBackStackImmediate(tag, 0) ?: return
            if (!isExisted) {
                val fragment: Fragment
                try {
                    fragment = (fragmentClazz.asSubclass(Fragment::class.java)).newInstance().apply { arguments = args }

                    val transaction = activity?.supportFragmentManager?.beginTransaction() ?: return
                    transaction.replace(resId, fragment, tag)

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

    fun addFragment(fragmentClazz: Class<*>, args: Bundle? = null, addBackStack: Boolean = true, resId: Int = R.id.container_main) {
        val tag = fragmentClazz.canonicalName ?: fragmentClazz.simpleName
        try {
            val isExisted = activity?.supportFragmentManager?.popBackStackImmediate(tag, 0) ?: return
            if (!isExisted) {
                val fragment: Fragment
                try {
                    fragment = (fragmentClazz.asSubclass(Fragment::class.java)).newInstance().apply { arguments = args }

                    val transaction = activity?.supportFragmentManager?.beginTransaction() ?: return
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

    fun addFragment(fragment: Fragment, args: Bundle? = null, addBackStack: Boolean = true, resId: Int = R.id.container_main) {
        val tag = fragment::class.java.canonicalName ?: fragment::class.java.simpleName
        try {
            val isExisted = activity?.supportFragmentManager?.popBackStackImmediate(tag, 0) ?: return
            if (!isExisted) {
                try {
                    fragment.apply { arguments = args }

                    val transaction = activity?.supportFragmentManager?.beginTransaction() ?: return
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

    fun replaceFragment(fragment: Fragment, args: Bundle? = null, addBackStack: Boolean = true, resId: Int = R.id.container_main) {
        val tag = fragment::class.java.canonicalName ?: fragment::class.java.simpleName
        try {
            val isExisted = activity?.supportFragmentManager?.popBackStackImmediate(tag, 0) ?: return
            if (!isExisted) {
                try {
                    fragment.apply { arguments = args }

                    val transaction = activity?.supportFragmentManager?.beginTransaction() ?: return
                    transaction.replace(resId, fragment, tag)

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
        activity?.runOnUiThread {
            context?.let {
                Boast.makeText(it, msg).show()
            }
        }
    }

    fun toast(msg: String, duration: Int, cancelCurrent: Boolean) {
        activity?.runOnUiThread {
            context?.let {
                Boast.makeText(it, msg, duration).show(cancelCurrent)
            }
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

    protected fun <VM : BaseViewModel> observeLoading(viewModel: VM) {
        observe(viewModel.isLoading, {
            if (it == true) {
                showDialog()
            } else {
                hideDialog()
            }
        })
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
}