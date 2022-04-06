package com.raghav.mynotes.ui

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.raghav.mynotes.R
import com.raghav.mynotes.adapter.TasksAdapter
import com.raghav.mynotes.databinding.FragmentAllTasksBinding
import com.raghav.mynotes.prefstore.TaskDatastore
import com.raghav.mynotes.prefstore.TaskDatastoreImpl
import com.raghav.mynotes.ui.base.BaseFragment
import com.raghav.mynotes.utils.Resource
import com.raghav.mynotes.utils.SnackBarUtils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AllTasksFragment : BaseFragment<FragmentAllTasksBinding>() {

    private val viewModel by viewModels<AllTasksVM>()

    @Inject
    lateinit var datastore: TaskDatastore

    override fun getViewBinding() = FragmentAllTasksBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                // fetch previous state of Sort By Deadline Checkbox
                val isSorted = datastore.isTasksSorted.firstOrNull() ?: false
                // update it's current state accordingly
                binding.checkboxSort.isChecked = isSorted

                viewModel.getTasks(isSorted)

                viewModel.tasks.observe(viewLifecycleOwner) { data ->
                    when (data) {
                        is Resource.Loading -> showProgressBar()
                        is Resource.Success -> {
                            hideProgressBar()
                            if (data.data?.isEmpty() == true) {
                                binding.tvNoTasks.visibility = VISIBLE
                                binding.rvTasks.adapter = TasksAdapter(emptyList())
                                binding.checkboxSort.isChecked = false
                                enableSortCheckBox(false)
                                // Save checkbox state in Datastore
                                saveSortCheckBoxState(false)
                            } else {
                                binding.rvTasks.adapter = data.data?.let {
                                    TasksAdapter(it) { binding, item ->
                                        binding.ivDelete.setOnClickListener {
                                            viewModel.deleteTask(item)
                                            requireContext().showSnackBar(
                                                binding.root,
                                                "Deleted",
                                                700
                                            )
                                        }
                                    }
                                }
                                enableSortCheckBox(true)
                            }
                        }
                        is Resource.Error -> {
                            hideProgressBar()
                            requireContext().showSnackBar(binding.root, data.message.toString())
                        }
                    }
                }
            }
        }

        binding.btnAddTasks.setOnClickListener {
            findNavController().navigate(R.id.action_allTasksFragment_to_addTaskFragment)
        }
        binding.checkboxSort.setOnClickListener {
            val isChecked = binding.checkboxSort.isChecked
            if (isChecked) {
                viewModel.getTasks(true)
                // Save checkbox state in Datastore
                saveSortCheckBoxState(true)

            } else {
                viewModel.getTasks()
                // Save checkbox state in Datastore
                saveSortCheckBoxState(false)
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

    private fun saveSortCheckBoxState(isChecked: Boolean) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                datastore.setValue(
                    TaskDatastoreImpl.IS_SORTED_KEY,
                    isChecked
                )
            }
        }
    }

    private fun enableSortCheckBox(isEnabled: Boolean) {
        binding.checkboxSort.isEnabled = isEnabled
    }
}
