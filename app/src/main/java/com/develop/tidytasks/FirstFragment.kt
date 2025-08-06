package com.develop.tidytasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.develop.tidytasks.data.remote.NetworkResult
import com.develop.tidytasks.databinding.FragmentFirstBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels() // We'll define this below

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isSignedIn.collect { signedIn ->
                    if (signedIn) {
                        binding.signInText.text = "User is signed in"
                    } else {
                        binding.signInText.text = "User is NOT signed in"
                    }
                }
            }
        }

        binding.btnLoginTest.setOnClickListener {
            viewModel.testLogin()
        }

        binding.btnAddTodoTest.setOnClickListener {
            viewModel.testAddTodo()
        }

        binding.btnGetTodosTest.setOnClickListener {
            viewModel.testGetTodos()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.testResult.collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        binding.tvResult.text = "✅ Success: ${result.data}"
                    }
                    is NetworkResult.Error -> {
                        binding.tvResult.text = "❌ Error: ${result.message}"
                    }
                    is NetworkResult.Loading -> {
                        binding.tvResult.text = "⏳ Loading..."
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}