package com.core

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RelativeLayout
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.utils.DisposeBag
import com.widget.Boast
import io.reactivex.disposables.Disposable

abstract class BaseDialog<V : ViewDataBinding> : DialogFragment() {

    lateinit var binding: V

    protected val bag by lazy { DisposeBag.create() }

    @LayoutRes
    abstract fun getLayoutId(): Int

    open fun isFullWidth(): Boolean = false

    abstract fun updateUI(savedInstanceState: Bundle?)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val root = RelativeLayout(activity)
        root.layoutParams =
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val dialog = Dialog(activity as FragmentActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(root)

        dialog.window?.let {
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            if (isFullWidth()) {
                it.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            } else {
                it.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            }
        }

        return dialog
    }

    fun addDispose(vararg disposables: Disposable) {
        bag.add(*disposables)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return inflater.inflate(getLayoutId(), container, false)
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUI(savedInstanceState)
    }

    override fun show(fragManager: FragmentManager, tag: String?) {
        val transaction = fragManager.beginTransaction()
        val prev: Fragment? = fragManager.findFragmentByTag(tag)
        prev?.let { transaction.remove(it) }
        transaction.addToBackStack(null)
        super.show(transaction, tag)
    }

    @Throws()
    open fun dismissDialog(fragManager: FragmentManager, tag: String) {
        val frag: Fragment? = fragManager.findFragmentByTag(tag)
        frag?.let {
            fragManager.beginTransaction()
                    .disallowAddToBackStack()
                    .remove(it)
                    .commitAllowingStateLoss()
        }
    }

    @Throws()
    open fun dismissDialog(fragManager: FragmentManager, tag: String, aniIn: Int, aniOut: Int) {
        val frag: Fragment? = fragManager.findFragmentByTag(tag)
        frag?.let {
            fragManager.beginTransaction()
                    .disallowAddToBackStack()
                    .setCustomAnimations(aniIn, aniOut)
                    .remove(it)
                    .commitAllowingStateLoss()
        }
    }

    fun toast(msg: String) {
        context?.let {
            Boast.makeText(it, msg).show()
        }
    }

    fun showDialog() {
        if (activity is BaseActivity<*>) {
            (activity as BaseActivity<*>).showDialog()
        }
    }

    fun hideDialog() {
        if (activity is BaseActivity<*>) {
            (activity as BaseActivity<*>).hideDialog()
        }
    }

    override fun onDestroy() {
        bag.dispose()
        super.onDestroy()
    }
}