package com.raghav.mynotes.ui.fragments

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.raghav.mynotes.R
import com.raghav.mynotes.adapter.TasksAdapter
import com.raghav.mynotes.databinding.FragmentAllTasksBinding
import com.raghav.mynotes.ui.AllTasksVM
import com.raghav.mynotes.utils.Resource
import com.raghav.mynotes.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllTasksFragment : Fragment(R.layout.fragment_all_tasks) {
    lateinit var binding: FragmentAllTasksBinding

    private val viewModel by viewModels<AllTasksVM>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAllTasksBinding.bind(view)
        setUpRecyclerView()

        binding.btnAddTasks.setOnClickListener {
            findNavController().navigate(R.id.action_allTasksFragment_to_addTaskFragment)
        }
        binding.checkboxSort.setOnClickListener {
            val isChecked = binding.checkboxSort.isChecked
            if (isChecked) {
                // TODO
            }
        }

        viewModel.tasks.observe(viewLifecycleOwner, { data ->
            when (data) {
                is Resource.Loading -> showProgressBar()
                is Resource.Success -> {
                    hideProgressBar()
                    if (data.data?.isEmpty() == true) {
                        binding.tvNoTasks.visibility = VISIBLE
                        binding.rvTasks.adapter = TasksAdapter(emptyList())
                    } else {
                        binding.rvTasks.adapter = data.data?.let {
                            TasksAdapter(it) { binding, item ->
                                binding.ivDelete.setOnClickListener {
                                    viewModel.deleteTask(item)
                                    ToastUtils.showToast(requireContext(), "Task Deleted")
                                }
                            }
                        }
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    ToastUtils.showToast(requireContext(), data.message.toString())
                }
            }
        })
    }

    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            when (view.id) {
                R.id.checkbox_sort -> {
                    if (checked) {
                        ToastUtils.showToast(requireContext(), "ajknaj")
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.rvTasks.layoutManager = LinearLayoutManager(activity)
    }

    private fun hideProgressBar() {
        binding.progressbar.visibility = GONE
    }

    private fun showProgressBar() {
        binding.progressbar.visibility = VISIBLE
    }
}
