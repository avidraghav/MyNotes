package com.raghav.mvvmtodo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.raghav.mvvmtodo.databinding.FragmentAddTaskBinding

class AddTaskFragment : Fragment(R.layout.fragment_add_task) {
    private lateinit var binding: FragmentAddTaskBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddTaskBinding.bind(view)

        binding.btnSaveTask.setOnClickListener {
            findNavController().navigate(R.id.action_addTaskFragment_to_allTasksFragment)
        }
    }
}