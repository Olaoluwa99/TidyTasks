package com.develop.tidytasks.presentation.home.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import com.develop.tidytasks.data.model.Todo
import com.develop.tidytasks.databinding.ItemTodoBinding
import com.develop.tidytasks.presentation.home.HomeViewModel

class TodoAdapter(
    initTodos: List<Todo>,
    private val viewModel: HomeViewModel,
    private val onTodoClick: (Todo) -> Unit
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    var todos = initTodos

    inner class TodoViewHolder(private val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            binding.todo = todo
            binding.viewModel = viewModel
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                onTodoClick(todo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTodoBinding.inflate(inflater, parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(todos[position])
    }

    override fun getItemCount(): Int = todos.size

    fun updateList(newTodos: List<Todo>) {
        val diffCallback = TodoDiffCallback(todos, newTodos)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        todos = newTodos
        diffResult.dispatchUpdatesTo(this)
    }
}
