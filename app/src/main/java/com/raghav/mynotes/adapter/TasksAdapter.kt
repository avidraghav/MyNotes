package com.raghav.mynotes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.raghav.mynotes.databinding.ItemTaskBinding
import com.raghav.mynotes.models.TaskEntity
import com.raghav.mynotes.ui.AllTasksFragmentDirections

class TasksAdapter(
    private val tasks: List<TaskEntity>,
    private val onItemClickListener: (ItemTaskBinding, TaskEntity) -> Unit = { _: ItemTaskBinding, _: TaskEntity -> }
) :
    RecyclerView.Adapter<TasksAdapter.TasksViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {

        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TasksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        holder.bind(createOnClickListener(holder.binding, tasks[position]), tasks[position])
    }

    override fun getItemCount() = tasks.size

    inner class TasksViewHolder(val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: View.OnClickListener, item: TaskEntity) {
            binding.tvTitle.text = item.title
            binding.tvDescription.text = item.description

            ViewCompat.setTransitionName(binding.tvTitle, "title_${item.id}")
            ViewCompat.setTransitionName(binding.tvDescription, "description_${item.id}")
            ViewCompat.setTransitionName(binding.tvDeadline, "deadline_${item.id}")

            if (item.deadLine != "0")
                binding.tvDeadline.text = item.deadLine

            binding.root.setOnClickListener(listener)
            onItemClickListener(binding, item)
        }
    }

    private fun createOnClickListener(
        binding: ItemTaskBinding,
        task: TaskEntity
    ): View.OnClickListener {
        return View.OnClickListener {
            val directions =
                AllTasksFragmentDirections.actionAllTasksFragmentToAddTaskFragment(task)
            val extras = FragmentNavigatorExtras(
                binding.tvTitle to "title_${task.id}",
                binding.tvDescription to "description_${task.id}",
                binding.tvDeadline to "deadline_${task.id}"
            )
            it.findNavController().navigate(directions, extras)
        }
    }
}