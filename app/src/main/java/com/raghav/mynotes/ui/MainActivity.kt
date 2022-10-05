package com.raghav.mynotes.ui

import android.os.Bundle
import com.raghav.mynotes.databinding.ActivityMainBinding
import com.raghav.mynotes.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object{
        var isAnimatedRecyclerView : Boolean = false
    }

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        isAnimatedRecyclerView = true
    }
}