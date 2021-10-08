package com.katana.koin.ui

import com.google.gson.JsonArray
import com.katana.koin.data.DataManager
import com.utils.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
        dataManager: DataManager,
        schedulerProvider: SchedulerProvider,
) : BaseAppViewModel(dataManager, schedulerProvider) {

    val users: PublishSubject<JsonArray> = PublishSubject.create()

    val edtInput = BehaviorSubject.createDefault("")

    var save = PublishSubject.create<Unit>()

    val showText: Observable<Unit> = save.onErrorReturn { }

    val edtOutput: Observable<String> = edtInput

    fun saveUser(user: String) = dataManager.saveUser("Ahihi")

    fun getUser() = dataManager.getUser()

    fun getUserGithub() =
            dataManager.getUserGitHub()
                    .compose(schedulerProvider.ioToMainSingleScheduler())
                    .subscribe({
                        users.onNext(it)
                    }, {
                        it.printStackTrace()
                    })

//    fun getUser() = dataManager.getUser()
}