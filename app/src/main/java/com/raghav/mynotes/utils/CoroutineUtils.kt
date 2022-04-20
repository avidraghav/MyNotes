package com.raghav.mynotes.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

object CoroutineUtils {

    fun Fragment.executeInCoroutine(
        lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
        block: suspend () -> Unit
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(lifecycleState) {
                block()
            }
        }
    }
}