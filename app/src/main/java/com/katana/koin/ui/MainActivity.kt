package com.katana.koin.ui

import com.core.BaseActivity
import com.katana.koin.R
import com.katana.koin.databinding.ActivityMainBinding
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val mainViewModel by viewModel<MainViewModel>()

    override fun getLayoutId(): Int = R.layout.activity_main

    private fun getUser() {
        addDispose(
                mainViewModel.getUserGithub()
        )
//        mainViewModel.getUserGithub().disposedBy(bag)
    }
}
