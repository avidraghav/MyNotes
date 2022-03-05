package com.raghav.mynotes.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.raghav.mynotes.R
import com.raghav.mynotes.databinding.FragmentAddTaskBinding
import com.raghav.mynotes.models.TaskEntity
import com.raghav.mynotes.ui.AddTasksVM
import com.raghav.mynotes.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTaskFragment : Fragment(R.layout.fragment_add_task) {
    private lateinit var binding: FragmentAddTaskBinding
    private val args: AddTaskFragmentArgs by navArgs()
    private var key: Int? = null

    private val viewModel by viewModels<AddTasksVM>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddTaskBinding.bind(view)

        if (args.task != null) {
            val task = args.task
            binding.etTaskTitle.setText(task?.title)
            binding.description.setText(task?.description)
            binding.deadline.text = task?.deadLine
            key = task?.id
        }

        binding.date.setOnClickListener {
            val datePickerFragment = DatePickerFragment { deadLine ->
                binding.deadline.text = deadLine
            }
            datePickerFragment.show(childFragmentManager, "datePicker")
        }

        binding.btnSaveTask.setOnClickListener {
            val title = binding.etTaskTitle.text.toString()
            val description = binding.description.text.toString()
            val deadLine = binding.deadline.text.toString()

            when {
                TextUtils.isEmpty(title) -> {
                    ToastUtils.showToast(requireContext(), "Please Enter A Title")
                }
                TextUtils.isEmpty(description) -> {
                    ToastUtils.showToast(requireContext(), "Please Enter Task Description")
                }
                TextUtils.isEmpty(deadLine) -> {
                    ToastUtils.showToast(requireContext(), "Please Select a Deadline")
                }
                else -> {
                    val aTask = TaskEntity(title, description, key, deadLine)
                    saveTask(aTask)
                }
            }
        }
    }

    private fun saveTask(aTask: TaskEntity) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            showProgressBar()
            viewModel.saveTask(aTask)
            hideProgressBar()
            ToastUtils.showToast(requireContext(), "Task Saved")
            findNavController().navigate(R.id.action_addTaskFragment_to_allTasksFragment)
        }
    }

    private fun hideProgressBar() {
        binding.progressbar.visibility = GONE
    }

    private fun showProgressBar() {
        binding.progressbar.visibility = VISIBLE
    }
}