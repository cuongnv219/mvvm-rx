package com.katana.koin.data.remote

import com.google.gson.JsonArray
import io.reactivex.rxjava3.core.Single

interface ApiHelper {

    fun getUserGitHub(): Single<JsonArray>
}