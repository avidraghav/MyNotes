package com.raghav.mvvmtodo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.raghav.mvvmtodo.databinding.FragmentAllTasksBinding

class AllTasksFragment : Fragment(R.layout.fragment_all_tasks) {
    lateinit var binding: FragmentAllTasksBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAllTasksBinding.bind(view)

        binding.btnAddTasks.setOnClickListener {
            findNavController().navigate(R.id.action_allTasksFragment_to_addTaskFragment)
        }
    }
}