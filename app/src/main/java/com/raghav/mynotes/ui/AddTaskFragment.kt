package com.raghav.mynotes.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.raghav.mynotes.R
import com.raghav.mynotes.databinding.FragmentAddTaskBinding
import com.raghav.mynotes.models.TaskEntity
import com.raghav.mynotes.ui.base.BaseFragment
import com.raghav.mynotes.utils.CoroutineUtils.executeInCoroutine
import com.raghav.mynotes.utils.SnackBarUtils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTaskFragment : BaseFragment<FragmentAddTaskBinding>() {

    private val args: AddTaskFragmentArgs by navArgs()
    private var key: Int? = null
    private val viewModel by viewModels<AddTasksVM>()

    override fun getViewBinding() = FragmentAddTaskBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (args.task != null) {
            sharedElementEnterTransition =
                TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        executeInCoroutine {
            val deadline = viewModel.fetchDeadline()
            if (deadline != null)
                binding.deadline.text = deadline.toString()
        }

        if (args.task != null) {
            val task = args.task
            binding.etTaskTitle.setText(task?.title)
            binding.description.setText(task?.description)
            binding.deadline.text = task?.deadLine
            key = task?.id

            ViewCompat.setTransitionName(binding.etTaskTitle, "title_${key}")
            ViewCompat.setTransitionName(binding.description, "description_${key}")
            ViewCompat.setTransitionName(binding.deadline, "deadline_${key}")
        }

        binding.date.setOnClickListener {
            showDatePicker()
        }

        binding.myToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSaveTask.setOnClickListener {
            val title = binding.etTaskTitle.text.toString()
            val description = binding.description.text.toString()
            val deadLine = binding.deadline.text.toString()
            val isInputValid = validateInput(title, description, deadLine)

            if (isInputValid.first) {
                val aTask = TaskEntity(
                    id = key,
                    title = title,
                    description = description,
                    deadLine = deadLine
                )
                saveTask(aTask)
                requireContext().showSnackBar(binding.root, "Task Saved")
                findNavController().navigate(R.id.action_addTaskFragment_to_allTasksFragment)
            } else {
                requireContext().showSnackBar(binding.root, isInputValid.second)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showDatePicker() {
        val datePickerFragment = DatePickerFragment { deadLine ->
            binding.deadline.text = deadLine
            binding.date.text = "Change date"
            viewModel.setDeadline(deadLine)
        }
        datePickerFragment.show(childFragmentManager, "datePicker")
    }

    private fun validateInput(
        title: String,
        description: String,
        deadline: String
    ): Pair<Boolean, String> {
        var message = ""
        if (title.isEmpty()) {
            message = "Please enter a title"
            return Pair(false, message)
        }
        if (description.isEmpty()) {
            message = "Please enter task description"
            return Pair(false, message)
        }
        if (deadline.isEmpty()) {
            message = "Please select a deadline"
            return Pair(false, message)
        }
        return Pair(true, message)
    }

    private fun saveTask(task: TaskEntity) {
        showProgressBar()
        executeInCoroutine {
            viewModel.saveTask(task)
            hideProgressBar()
        }
    }

    private fun hideProgressBar() {
        binding.progressbar.visibility = GONE
    }

    private fun showProgressBar() {
        binding.progressbar.visibility = VISIBLE
    }
}