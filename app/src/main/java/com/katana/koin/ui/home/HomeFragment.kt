package com.katana.koin.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.core.BaseFragment
import com.katana.koin.R
import com.katana.koin.databinding.FragmentHomeBinding

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_home

    fun updateUI(savedInstanceState: Bundle?) {

    }
}
