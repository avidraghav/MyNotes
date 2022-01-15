package com.raghav.mvvmtodo

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.raghav.mvvmtodo.databinding.FragmentAddTaskBinding
import com.raghav.mvvmtodo.models.TaskEntity

class AddTaskFragment : Fragment(R.layout.fragment_add_task) {
    private lateinit var binding: FragmentAddTaskBinding

    private val viewModel by viewModels<AddTasksVM> {
        TasksViewModelFactory((activity?.application as MvvmTodo).repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddTaskBinding.bind(view)

        binding.btnSaveTask.setOnClickListener {
            val title = binding.etTaskTitle.text.toString()
            val description = binding.description.text.toString()
            val aTask = TaskEntity(title, description)
            saveTask(aTask)

        }
    }

    private fun saveTask(aTask: TaskEntity) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            showProgressBar()
            viewModel.saveTask(aTask)
            hideProgressBar()
            Toast.makeText(activity, "Task Saved", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addTaskFragment_to_allTasksFragment)
        }

    }

    private fun hideProgressBar() {
        binding.progressbar.visibility = VISIBLE
    }

    private fun showProgressBar() {
        binding.progressbar.visibility = GONE
    }
}