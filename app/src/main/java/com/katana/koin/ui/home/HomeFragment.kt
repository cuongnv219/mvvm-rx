package com.katana.koin.ui.home

import android.os.Bundle
import com.core.BaseFragment
import com.katana.koin.R
import com.katana.koin.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_home

    fun updateUI(savedInstanceState: Bundle?) {
    }
}
