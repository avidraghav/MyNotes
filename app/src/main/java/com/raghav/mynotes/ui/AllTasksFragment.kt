package com.raghav.mynotes.ui

import android.os.Bundle
import android.view.View
import android.view.View.*
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.raghav.mynotes.R
import com.raghav.mynotes.adapter.TasksAdapter
import com.raghav.mynotes.databinding.FragmentAllTasksBinding
import com.raghav.mynotes.models.TaskEntity
import com.raghav.mynotes.ui.base.BaseFragment
import com.raghav.mynotes.utils.CoroutineUtils.executeInCoroutine
import com.raghav.mynotes.utils.Resource
import com.raghav.mynotes.utils.SnackBarUtils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllTasksFragment : BaseFragment<FragmentAllTasksBinding>() {

    private val viewModel by viewModels<AllTasksVM>()
    private val addTasksVM by viewModels<AddTasksVM>()

    override fun getViewBinding() = FragmentAllTasksBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        executeInCoroutine {
            viewModel.getSortCheckBoxState()

            viewModel.checkBoxState.observe(viewLifecycleOwner) { isChecked ->
                binding.checkboxSort.isChecked = isChecked
                viewModel.getTasks(isChecked)
            }

            viewModel.tasks.observe(viewLifecycleOwner) { data ->
                when (data) {
                    is Resource.Loading -> showProgressBar()
                    is Resource.Success -> {
                        hideProgressBar()
                        if (data.data?.isEmpty() == true) {
                            binding.animNoTask.visibility = VISIBLE
                            binding.tvNoTasks.visibility = VISIBLE
                            binding.rvTasks.adapter = TasksAdapter(emptyList())
                            binding.checkboxSort.isChecked = false
                            enableSortCheckBox(false)
                            saveSortCheckBoxState(false)
                        } else {
                            binding.animNoTask.visibility = INVISIBLE
                            binding.tvNoTasks.visibility = INVISIBLE
                            binding.rvTasks.layoutManager = LinearLayoutManager(context)
                            binding.rvTasks.adapter = data.data?.let {
                                TasksAdapter(it) { taskItemBinding, item ->
                                    taskItemBinding.ivDelete.setOnClickListener {
                                        viewModel.deleteTask(item)
                                        requireContext().showSnackBar(
                                            rootView = binding.root,
                                            message = "Deleted",
                                            anchorView = binding.btnAddTasks,
                                            actionText = "Undo",
                                            onAction = { undoDelete(item) }
                                        )
                                    }
                                }
                            }
                            if (MainActivity.isAnimatedRecyclerView){
                                val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_fall_down)
                                binding.rvTasks.layoutAnimation = controller
                                binding.rvTasks.scheduleLayoutAnimation()
                                MainActivity.isAnimatedRecyclerView = false
                            }
                            //for return animation
                            postponeEnterTransition()
                            view.viewTreeObserver.addOnPreDrawListener {
                                startPostponedEnterTransition()
                                true
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
        viewModel.saveSortCheckBoxState(isChecked)
    }

    private fun enableSortCheckBox(isEnabled: Boolean) {
        binding.checkboxSort.isEnabled = isEnabled
    }

    private fun undoDelete(task: TaskEntity) {
        executeInCoroutine {
            addTasksVM.saveTask(task)
        }
    }
}
