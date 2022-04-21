package com.raghav.mynotes.ui

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.raghav.mynotes.R
import com.raghav.mynotes.adapter.TasksAdapter
import com.raghav.mynotes.databinding.FragmentAllTasksBinding
import com.raghav.mynotes.prefstore.TaskDatastore
import com.raghav.mynotes.prefstore.TaskDatastoreImpl
import com.raghav.mynotes.ui.base.BaseFragment
import com.raghav.mynotes.utils.CoroutineUtils.executeInCoroutine
import com.raghav.mynotes.utils.Resource
import com.raghav.mynotes.utils.SnackBarUtils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
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

        executeInCoroutine {
            val isSorted = datastore.isTasksSorted.firstOrNull() ?: false
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
                            saveSortCheckBoxState(false)
                        } else {
                            binding.rvTasks.adapter = data.data?.let {
                                TasksAdapter(it) { taskItemBinding, item ->
                                    taskItemBinding.ivDelete.setOnClickListener {
                                        viewModel.deleteTask(item)
                                        requireContext().showSnackBar(
                                            rootView = binding.root,
                                            message = "Deleted",
                                            anchorView = binding.btnAddTasks,
                                        )
                                    }
                                }
                            }
                            enableSortCheckBox(true)
                        }
                    }
                    is Resource.Error -> {
                        hideProgressBar()
                        requireContext().showSnackBar(
                            rootView = binding.root,
                            message = data.message.toString(),
                            anchorView = binding.btnAddTasks,
                        )
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
                saveSortCheckBoxState(true)
            } else {
                viewModel.getTasks()
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
        executeInCoroutine {
            datastore.setValue(
                TaskDatastoreImpl.IS_SORTED_KEY,
                isChecked
            )
        }
    }

    private fun enableSortCheckBox(isEnabled: Boolean) {
        binding.checkboxSort.isEnabled = isEnabled
    }
}
