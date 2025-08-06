package com.develop.tidytasks.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.develop.tidytasks.R
import com.develop.tidytasks.databinding.FragmentNewTodoSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewTodoSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val initialTitle = arguments?.getString(ARG_TASK_TITLE) ?: ""
        val initialStatus = arguments?.getBoolean(ARG_TASK_STATUS) ?: false
        //binding.editTextTaskTitle.setText(initialTitle)

        binding.button.setOnClickListener {
            //val updatedTitle = binding.editTextTaskTitle.text.toString()

            val result = Bundle().apply {
                putString("task_title", "Driving")
                putBoolean("task_status", true)
            }

            parentFragmentManager.setFragmentResult("new_task_result", result)
            dismiss()
        }
    }
}