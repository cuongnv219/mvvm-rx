package com.utils

import android.annotation.TargetApi
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.text.format.DateUtils
import android.text.format.Time
import android.view.KeyCharacterMap
import android.view.KeyEvent
import android.view.ViewConfiguration
import java.io.File

object AndroidVersion {
    fun hasJellyBean(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
    }

    fun hasJellyBeanMR1(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
    }

    fun hasJellyBeanMR2(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2
    }

    fun hasKitKat(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
    }

    fun hasLollipop(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
    }

    fun hasLollipopMR1(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1
    }

    fun hasMarshmallow(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    fun hasNougat(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
    }

    fun hasNougatMR1(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1
    }

    fun hasOreo(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
    }

    fun hasOreoMR1(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1
    }

    fun hasPie(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
    }

    fun hasMoreHeap(): Boolean {
        return Runtime.getRuntime().maxMemory() > 20971520
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    fun isLowRamDevice(context: Context): Boolean {
        if (hasKitKat()) {
            val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            return am.isLowRamDevice
        }
        return !hasMoreHeap()
    }

    fun isTablet(context: Context): Boolean {
        return context.resources.configuration.smallestScreenWidthDp >= 600
    }

    fun formatTime(context: Context?, `when`: Long): String { // TODO: DateUtils should make this easier
        val then = Time()
        then.set(`when`)
        val now = Time()
        now.setToNow()
        var flags = DateUtils.FORMAT_NO_NOON or DateUtils.FORMAT_NO_MIDNIGHT or DateUtils.FORMAT_ABBREV_ALL
        flags = if (then.year != now.year) {
            flags or DateUtils.FORMAT_SHOW_YEAR or DateUtils.FORMAT_SHOW_DATE
        } else if (then.yearDay != now.yearDay) {
            flags or DateUtils.FORMAT_SHOW_DATE
        } else {
            flags or DateUtils.FORMAT_SHOW_TIME
        }
        return DateUtils.formatDateTime(context, `when`, flags)
    }

    fun getDirectorySize(dir: File): Long {
        var result = 0L
        if (dir.listFiles() != null && dir.listFiles().isNotEmpty()) {
            for (eachFile in dir.listFiles()) {
                result += if (eachFile.isDirectory && eachFile.canRead()) getDirectorySize(eachFile) else eachFile.length()
            }
        } else if (!dir.isDirectory) {
            result = dir.length()
        }
        return result
    }

//    fun hasSoftNavBar(context: Context?): Boolean {
//        val hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey()
//        val hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
//        return !hasMenuKey && !hasBackKey
//    }

    private const val BRIGHTNESS_THRESHOLD = 150
    fun isColorDark(color: Int): Boolean {
        return (30 * Color.red(color) + 59 * Color.green(color) + 11 * Color.blue(color)) / 100 <= BRIGHTNESS_THRESHOLD
    }

    const val PRESSED_COLOR_LIGHTUP = 255 / 25
    fun getLightColor(color: Int, amount: Int): Int {
        return Color.argb(Math.min(255, Color.alpha(color)), Math.min(255, Color.red(color) + amount),
                Math.min(255, Color.green(color) + amount), Math.min(255, Color.blue(color) + amount))
    }

    fun getLightColor(color: Int): Int {
        val amount = PRESSED_COLOR_LIGHTUP
        return Color.argb(Math.min(255, Color.alpha(color)), Math.min(255, Color.red(color) + amount),
                Math.min(255, Color.green(color) + amount), Math.min(255, Color.blue(color) + amount))
    }

    fun getStatusBarColor(color1: Int): Int {
        val color2 = Color.parseColor("#000000")
        return blendColors(color1, color2, 0.9f)
    }

    fun getActionButtonColor(color1: Int): Int {
        val color2 = Color.parseColor("#ffffff")
        return blendColors(color1, color2, 0.9f)
    }

    fun blendColors(color1: Int, color2: Int, ratio: Float): Int {
        val inverseRation = 1f - ratio
        val r = Color.red(color1) * ratio + Color.red(color2) * inverseRation
        val g = Color.green(color1) * ratio + Color.green(color2) * inverseRation
        val b = Color.blue(color1) * ratio + Color.blue(color2) * inverseRation
        return Color.rgb(r.toInt(), g.toInt(), b.toInt())
    }

    fun getComplementaryColor(colorToInvert: Int): Int {
        val hsv = FloatArray(3)
        Color.RGBToHSV(Color.red(colorToInvert), Color.green(colorToInvert),
                Color.blue(colorToInvert), hsv)
        hsv[0] = (hsv[0] + 180) % 360
        return Color.HSVToColor(hsv)
    }

    fun isIntentAvailable(context: Context, intent: Intent?): Boolean {
        val packageManager = context.packageManager
        val list = packageManager.queryIntentActivities(intent ?: return false, PackageManager.MATCH_DEFAULT_ONLY)
        return list.size > 0
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun isActivityAlive(activity: Activity?): Boolean {
        return !(null == activity || if (hasJellyBeanMR1()) activity.isDestroyed else activity.isFinishing)
    }

    fun hasFeature(context: Context, feature: String?): Boolean {
        return context.packageManager.hasSystemFeature(feature ?: return false)
    }

    fun hasLeanback(context: Context): Boolean {
        return hasFeature(context, PackageManager.FEATURE_LEANBACK)
    }

    fun hasWiFi(context: Context): Boolean {
        return hasFeature(context, PackageManager.FEATURE_WIFI)
    }

    fun checkUSBDevices(): Boolean {
        return !hasNougat()
    }
}