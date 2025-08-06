package com.develop.tidytasks.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.develop.tidytasks.data.model.Todo
import com.develop.tidytasks.data.remote.NetworkResult
import com.develop.tidytasks.data.remote.api.TodoResponse
import com.develop.tidytasks.databinding.FragmentHomeBinding
import com.develop.tidytasks.presentation.home.utils.SpacingItemDecorator
import com.develop.tidytasks.presentation.home.utils.TodoAdapter
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by activityViewModels()

    private lateinit var emptyText: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var isCompletedButton: Button
    private lateinit var inProgressButton: Button

    private lateinit var adapter: TodoAdapter
    private lateinit var selectedTodo: Todo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization()
        retrieveFromBottomSheet()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.testResult.collect { result ->
                    when (result) {
                        is NetworkResult.Success -> {
                            emptyText.visibility = View.GONE
                            val todoResponse = result.data as? TodoResponse
                            val todos = todoResponse?.todos ?: emptyList()
                            setupRecyclerView(todos)
                        }
                        is NetworkResult.Error -> {
                            emptyText.visibility = View.VISIBLE
                            emptyText.text = "❌ Error: ${result.message}"
                        }
                        is NetworkResult.Loading -> {
                            emptyText.visibility = View.GONE
                            //TODO - Show Loading Dialog
                        }
                    }
                }
            }
        }

        // 🟢 Open BottomSheet on button click
        isCompletedButton.setOnClickListener {//TODO - CHANGE LATER
            val initialTodoText = selectedTodo.todo
            val initialTodoStatus = selectedTodo.completed
            val bottomSheet = NewTodoSheetFragment.newInstance(initialTodoText, initialTodoStatus)
            bottomSheet.show(parentFragmentManager, "NewTaskBottomSheet")
        }
    }

    private fun initialization(){
        recyclerView = binding.recyclerView
        selectedTodo = Todo(id = 1, todo = "", completed = false, userId = 123)
        emptyText = binding.emptyText
        inProgressButton = binding.inProgress
        isCompletedButton = binding.completed
    }

    private fun setupRecyclerView(todoList : List<Todo>){
        val spacingInDp = 8
        val spacingInPx = (resources.displayMetrics.density * spacingInDp).toInt()
        adapter = TodoAdapter(todoList, homeViewModel){clickedTodo->
            selectedTodo = clickedTodo
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(SpacingItemDecorator(spacingInPx))
    }

    private fun retrieveFromBottomSheet(){
        parentFragmentManager.setFragmentResultListener("new_task_result", this) { _, bundle ->
            val taskTitle = bundle.getString("task_title")
            //Toast.makeText(requireContext(), "Received: $taskTitle", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}