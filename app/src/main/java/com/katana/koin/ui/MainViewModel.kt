package com.katana.koin.ui

import com.core.BaseViewModel
import com.google.gson.JsonArray
import com.katana.koin.data.DataManager
import com.utils.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

interface MainInput {
    val edtInput: BehaviorSubject<String>
    var save: PublishSubject<Unit>
}

interface MainOutput {
    val showText: Observable<Unit>
    val edtOutput: Observable<String>
    val users: Observable<JsonArray>
}

class MainViewModel(
        dataManager: DataManager,
        schedulerProvider: SchedulerProvider
) : BaseViewModel<MainInput, MainOutput, DataManager>(dataManager, schedulerProvider), MainInput, MainOutput {

    override val users: PublishSubject<JsonArray> = PublishSubject.create()

    override val edtInput = BehaviorSubject.createDefault("")

    override var save = PublishSubject.create<Unit>()

    override val showText: Observable<Unit> = save.onErrorReturn { }

    override val edtOutput: Observable<String> = edtInput

    override val input: MainInput
        get() = this

    override val output: MainOutput
        get() = this

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
}