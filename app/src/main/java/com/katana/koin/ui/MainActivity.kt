package com.katana.koin.ui

import androidx.activity.viewModels
import com.core.BaseActivity
import com.katana.koin.R
import com.katana.koin.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun getLayoutId(): Int = R.layout.activity_main

    private fun getUser() {
        addDispose(
                mainViewModel.getUserGithub()
        )
//        mainViewModel.getUserGithub().disposedBy(bag)
    }
}
