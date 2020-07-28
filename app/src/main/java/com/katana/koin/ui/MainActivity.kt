package com.katana.koin.ui

import android.os.Bundle
import com.core.BaseActivity
import com.katana.koin.R
import com.katana.koin.databinding.ActivityMainBinding
import com.katana.koin.ui.home.HomeFragment
import com.utils.applyClickShrink
import com.utils.ext.log
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val mainViewModel by viewModel<MainViewModel>()

    override fun getLayoutId(): Int = R.layout.activity_main


    fun updateUI(savedInstanceState: Bundle?) {

        //input
        mainViewModel.edtInput.receiveTextChangesFrom(edtInput)
        mainViewModel.save.receiveClicksFrom(btnSave)

        addDispose(
                mainViewModel.edtOutput.subscribe {
                    log(it)
                },
                mainViewModel.showText.subscribe {
                    log("huhu")

//                    getUser()
                },
                mainViewModel.users.subscribe {
                    log("hay lam")
                }
//                mainViewModel.getUserGithub()
        )

        openFragment(R.id.content_home, HomeFragment::class.java, null, true)
        btnSave.applyClickShrink()
    }

    private fun getUser() {
        addDispose(
                mainViewModel.getUserGithub()
        )
//        mainViewModel.getUserGithub().disposedBy(bag)
    }
}
