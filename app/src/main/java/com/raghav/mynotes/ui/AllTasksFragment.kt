package com.raghav.mynotes.ui

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.raghav.mynotes.R
import com.raghav.mynotes.adapter.TasksAdapter
import com.raghav.mynotes.databinding.FragmentAllTasksBinding
import com.raghav.mynotes.utils.MyNotesApplication
import com.raghav.mynotes.utils.Resource
import com.raghav.mynotes.viewmodelfactories.AllTasksViewModelFactory

class AllTasksFragment : Fragment(R.layout.fragment_all_tasks) {
    lateinit var binding: FragmentAllTasksBinding

    private val viewModel by viewModels<AllTasksVM> {
        AllTasksViewModelFactory((activity?.application as MyNotesApplication).repository)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAllTasksBinding.bind(view)
        setUpRecyclerView()


        binding.btnAddTasks.setOnClickListener {
            findNavController().navigate(R.id.action_allTasksFragment_to_addTaskFragment)
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
                            TasksAdapter(it.reversed()) { binding, item ->
                                binding.ivDelete.setOnClickListener {
                                    viewModel.deleteTask(item)
                                    Toast.makeText(activity, "Task Deleted", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        }
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    Toast.makeText(activity, data.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })


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
