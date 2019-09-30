package com.katana.koin.data.remote

import com.google.gson.JsonArray
import io.reactivex.Single

interface ApiHelper {

    fun getUserGitHub(): Single<JsonArray>
}