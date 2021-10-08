package com.katana.koin.data.local.prefs

interface PrefsHelper {

    fun getUser(): String?

    fun saveUser(user: String)

    var count: Int
}