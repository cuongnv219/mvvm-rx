package com.katana.koin.ui

import android.os.Bundle
import com.core.BaseActivity
import com.katana.koin.R
import com.katana.koin.ui.home.HomeFragment
import com.utils.ext.log
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private val mainViewModel by viewModel<MainViewModel>()

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun updateUI(savedInstanceState: Bundle?) {

        //input
        mainViewModel.input.edtInput.receiveTextChangesFrom(edtInput)
        mainViewModel.input.save.receiveClicksFrom(btnSave)

        addDispose(
                mainViewModel.output.edtOutput.subscribe {
                    log(it)
                },
                mainViewModel.output.showText.subscribe {
                    log("huhu")

//                    getUser()
                },
                mainViewModel.output.users.subscribe {
                    log("hay lam")
                }
//                mainViewModel.getUserGithub()
        )
//        mainViewModel.output.edtOutput.subscribe {
//            log(it)
//        }.disposedBy(bag)

//        mainViewModel.output.showText.subscribe {
//            log("huhu")
//
//            getUser()
//        }.disposedBy(bag)

//        mainViewModel.output.users.subscribe {
//            log("hay lam")
//        }.disposedBy(bag)

//        getUser()

        openFragment(R.id.content_home, HomeFragment::class.java, null, true)

    }

    private fun getUser() {
        addDispose(
                mainViewModel.getUserGithub()
        )
//        mainViewModel.getUserGithub().disposedBy(bag)
    }

    override fun onStart() {
        super.onStart()
        log("onStart")
    }

    override fun onPause() {
        super.onPause()
        log("onPause")
    }

    override fun onResume() {
        super.onResume()
        log("onResume")
    }

    override fun onStop() {
        super.onStop()
        log("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }
}
