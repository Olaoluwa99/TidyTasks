package com.develop.tidytasks.presentation.home

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.TextView
import androidx.compose.runtime.collectAsState
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.develop.tidytasks.data.model.Todo
import com.develop.tidytasks.data.remote.NetworkResult
import com.develop.tidytasks.data.remote.api.TodoResponse
import com.develop.tidytasks.databinding.DialogProgressBinding
import com.develop.tidytasks.databinding.FragmentHomeBinding
import com.develop.tidytasks.presentation.home.utils.SpacingItemDecorator
import com.develop.tidytasks.presentation.home.utils.TodoAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var _progressDialogBinding: DialogProgressBinding? = null
    private val progressDialogBinding get() = _progressDialogBinding!!

    private val homeViewModel: HomeViewModel by activityViewModels()

    private lateinit var progressBar: ProgressBar
    private lateinit var emptyText: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var isCompletedButton: CheckBox
    private lateinit var inProgressButton: CheckBox
    private lateinit var createButton: FloatingActionButton

    private lateinit var progressDialog: AlertDialog
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
        //homeViewModel.getTodos()
        retrieveFromBottomSheet()


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.status.collect { result ->
                    when (result) {
                        is NetworkResult.Success -> {
                            progressBar.visibility = View.GONE
                            emptyText.visibility = View.GONE

                            val spacingInDp = 8
                            val spacingInPx = (resources.displayMetrics.density * spacingInDp).toInt()
                            setupRecyclerView(homeViewModel.todoList.value)
                            recyclerView.layoutManager = LinearLayoutManager(context)
                            recyclerView.addItemDecoration(SpacingItemDecorator(spacingInPx))
                        }
                        is NetworkResult.Error -> {
                            //dismissProgressDialog()
                            progressBar.visibility = View.GONE
                            emptyText.visibility = View.VISIBLE
                            emptyText.text = "Error: ${result.message}"
                        }
                        is NetworkResult.Loading -> {
                            progressBar.visibility = View.VISIBLE
                            emptyText.visibility = View.GONE
                            //showProgressDialog("Loading…")
                        }
                    }
                }
            }
        }

        createButton.setOnClickListener {
            openEditSheet(isCreate = true)
        }

        isCompletedButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (inProgressButton.isChecked) inProgressButton.isChecked = false
                setupRecyclerView(homeViewModel.getCompletedTodos())
            } else if (!inProgressButton.isChecked) {
                setupRecyclerView(homeViewModel.todoList.value)
            }
        }

        inProgressButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (isCompletedButton.isChecked) isCompletedButton.isChecked = false
                setupRecyclerView(homeViewModel.getInProgressTodos())
            } else if (!isCompletedButton.isChecked) {
                setupRecyclerView(homeViewModel.todoList.value)
            }
        }

    }

    private fun initialization(){
        recyclerView = binding.recyclerView
        selectedTodo = Todo(id = 1, todo = "", completed = false, userId = 123)
        progressBar = binding.loading
        emptyText = binding.emptyText
        inProgressButton = binding.checkInProgress
        isCompletedButton = binding.checkIsCompleted
        createButton = binding.fab
    }

    private fun openEditSheet(isCreate: Boolean = false){
        var initialTodoText = ""
        var initialTodoStatus = false
        if (!isCreate){
            initialTodoText = selectedTodo.todo
            initialTodoStatus = selectedTodo.completed
        }
        val bottomSheet = NewTodoSheetFragment.newInstance(initialTodoText, initialTodoStatus)
        bottomSheet.show(parentFragmentManager, "NewTaskBottomSheet")
    }

    private fun setupRecyclerView(todoList : List<Todo>){
        /*val spacingInDp = 8
        val spacingInPx = (resources.displayMetrics.density * spacingInDp).toInt()*/
        adapter = TodoAdapter(todoList, homeViewModel){clickedTodo->
            selectedTodo = clickedTodo
            openEditSheet()
        }
        //recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        //recyclerView.addItemDecoration(SpacingItemDecorator(spacingInPx))
    }

    private fun retrieveFromBottomSheet(){
        parentFragmentManager.setFragmentResultListener("new_task_result", this) { _, bundle ->
            val taskTitle = bundle.getString("task_title")
            val taskStatus = bundle.getBoolean("task_status")
            val taskIsCreate = bundle.getBoolean("task_is_create")
            val taskIsDelete = bundle.getBoolean("task_is_delete")

            if (taskIsDelete){
                homeViewModel.deleteTodo(selectedTodo.id)
            }else{
                if (taskIsCreate){
                    homeViewModel.addTodo(
                        Todo(
                            id = homeViewModel.generateRandomId(),
                            todo = taskTitle ?: "",
                            completed = false,
                            userId = 1
                        )
                    )
                }else{
                    homeViewModel.updateTodo(
                        selectedTodo.copy(todo = taskTitle ?: "Blank task")
                    )
                }
            }
        }
    }

    private fun showProgressDialog(message: String) {
        _progressDialogBinding = DialogProgressBinding.inflate(layoutInflater)

        val builder = AlertDialog.Builder(requireContext())
        builder.setView(progressDialogBinding.root)
        builder.setCancelable(false)

        //
        progressDialogBinding.progressText.text = message

        progressDialog = builder.create()
        progressDialog.show()
    }

    private fun dismissProgressDialog() {
        progressDialog.dismiss()
        if (::progressDialog.isInitialized && progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}