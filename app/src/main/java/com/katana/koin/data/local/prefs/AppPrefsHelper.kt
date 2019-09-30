package com.katana.koin.data.local.prefs

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

/**
 * Created by Kaz on 10:38 2018-12-19
 */
class AppPrefsHelper constructor(context: Context, prefsName: String, private val gson: Gson) : PrefsHelper {

    companion object {
        const val KEY_USER                          = "KEY_USER"
    }

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)


    override fun saveUser(user: String) {
        val u = gson.toJson(user)
        sharedPreferences.edit().putString(KEY_USER, u).apply()
    }

    override fun getUser(): String? {
        return sharedPreferences.getString(KEY_USER, "")
    }
}