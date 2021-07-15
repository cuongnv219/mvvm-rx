package com.katana.koin.data.remote

import com.google.gson.JsonArray
//import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.rxjava3.core.Single
import java.util.function.ToDoubleBiFunction

class AppApiHelper : ApiHelper {

    override fun getUserGitHub(): Single<JsonArray> = TODO()
//            Rx2AndroidNetworking.get("https://api.github.com/users?since=XXX")
//                    .build()
//                    .getObjectSingle(JsonArray::class.java)
}