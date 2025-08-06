package com.develop.tidytasks.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import com.develop.tidytasks.R
import com.develop.tidytasks.databinding.FragmentNewTodoSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class NewTodoSheetFragment : BottomSheetDialogFragment() {

    companion object {
        private const val ARG_TASK_TITLE = "arg_task_title"
        private const val ARG_TASK_STATUS = "arg_task_status"

        fun newInstance(taskTitle: String, taskStatus: Boolean): NewTodoSheetFragment {
            val fragment = NewTodoSheetFragment()
            val args = Bundle().apply {
                putString(ARG_TASK_TITLE, taskTitle)
                putBoolean(ARG_TASK_STATUS, taskStatus)
            }
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding: FragmentNewTodoSheetBinding? = null
    private val binding get() = _binding!!

    private lateinit var titleText: TextView
    private lateinit var doneButton: Button
    private lateinit var deleteButton: Button
    private lateinit var editTextLayout: TextInputLayout
    private lateinit var editTextTask: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewTodoSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialization()
        val initialTitle = arguments?.getString(ARG_TASK_TITLE) ?: ""
        val initialStatus = arguments?.getBoolean(ARG_TASK_STATUS) ?: false

        if (initialTitle.isBlank()){
            titleText.text = "Create Task"

        }else{
            titleText.text = "Edit Task"
            editTextTask.setText(initialTitle)
        }

        doneButton.setOnClickListener {
            val updatedTitle = binding.editTextTask.text.toString()
            if (updatedTitle.isBlank()){
                editTextLayout.error = "Task cannot be empty"
            }else{
                val result = Bundle().apply {
                    putString("task_title", updatedTitle)
                    putBoolean("task_status", initialStatus)
                    putBoolean("task_is_create", updatedTitle.isBlank())
                    putBoolean("task_is_delete", false)
                }
                parentFragmentManager.setFragmentResult("new_task_result", result)
                dismiss()
            }
        }

        doneButton.setOnClickListener {
            val result = Bundle().apply {
                putBoolean("task_is_delete", true)
            }
            parentFragmentManager.setFragmentResult("new_task_result", result)
            dismiss()
        }

    }

    private fun initialization(){
        doneButton = binding.doneButton
        deleteButton = binding.deleteButton
        titleText = binding.titleText
        editTextLayout = binding.editTextLayout
        editTextTask = binding.editTextTask
    }
}