package com.develop.tidytasks.presentation.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ProgressBar
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.develop.tidytasks.R
import com.develop.tidytasks.data.remote.NetworkResult
import com.develop.tidytasks.databinding.FragmentAuthenticationBinding
import com.develop.tidytasks.databinding.FragmentHomeBinding
import com.develop.tidytasks.presentation.home.HomeViewModel
import com.develop.tidytasks.presentation.home.utils.SpacingItemDecorator
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class AuthenticationFragment : Fragment() {

    private var _binding: FragmentAuthenticationBinding? = null
    private val binding get() = _binding!!

    private val authenticationViewModel: AuthenticationViewModel by activityViewModels()

    private lateinit var submitButton: Button
    private lateinit var progressBar: ProgressBar

    private lateinit var passwordTextLayout: TextInputLayout
    private lateinit var passwordText: TextInputEditText

    private lateinit var emailTextLayout: TextInputLayout
    private lateinit var emailText: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthenticationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization()
        authenticationViewModel.getTodos()
        retrieveFromBottomSheet()


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                authenticationViewModel.status.collect { result ->
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

        submitButton.setOnClickListener {
            //openEditSheet(isCreate = true)
        }

    }

    private fun initializations() {
        submitButton = binding.doneButton
        progressBar = binding.progressBar
        passwordTextLayout = binding.password
        passwordText = binding.passwordText
        emailTextLayout = binding.email
        emailText = binding.emailText
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}