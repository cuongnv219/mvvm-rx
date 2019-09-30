package com.katana.koin.data.remote

import com.google.gson.JsonArray
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Single

class AppApiHelper : ApiHelper {

    override fun getUserGitHub(): Single<JsonArray> =
            Rx2AndroidNetworking.get("https://api.github.com/users?since=XXX")
                    .build()
                    .getObjectSingle(JsonArray::class.java)
}