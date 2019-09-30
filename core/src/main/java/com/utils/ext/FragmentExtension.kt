package com.utils.ext

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment

fun Fragment.startActivity(clazz: Class<*>) {
    val intent = Intent(context, clazz)
    startActivity(intent)
}

fun Fragment.startActivity(clazz: Class<*>, bundle: Bundle) {
    val intent = Intent(context, clazz)
    intent.putExtras(bundle)
    startActivity(intent)
}

fun Fragment.startActivityForResult(clazz: Class<*>, requestCode: Int) {
    val intent = Intent(context, clazz)
    activity?.startActivityForResult(intent, requestCode)
}

fun Fragment.startActivityForResult(clazz: Class<*>, requestCode: Int, bundle: Bundle) {
    val intent = Intent(context, clazz)
    intent.putExtras(bundle)
    activity?.startActivityForResult(intent, requestCode)
}

fun Fragment.startActivityNewTask(clazz: Class<*>) {
    val intent = Intent(context, clazz)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
    activity?.startActivity(intent)
}

fun Fragment.isConnectedInternet(): Boolean {
    val connectivityManager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    return if (connectivityManager != null) {
        val networkInfo = connectivityManager.activeNetworkInfo
        networkInfo != null && networkInfo.isConnected
    } else {
        false
    }
}

fun Fragment.currentFrag(container: Int) = childFragmentManager.findFragmentById(container)

inline fun <reified T> Fragment.isExistFragmentInBackStack(container: Int): Boolean {
    var isExist = false
    val fm = childFragmentManager
    val count = fm.backStackEntryCount
    for (i in 0 until count) {
        val fr = childFragmentManager.findFragmentById(container)
        if (fr is T) {
            isExist = true
            continue
        }
//            fm.popBackStackImmediate()
    }
    return isExist
}

fun Fragment.removeLastFragmentInBackStack(container: Int) {
    val fm = childFragmentManager
    val count = fm.backStackEntryCount
    for (i in 0 until count) {
        fm.popBackStackImmediate()
        break
    }
}
