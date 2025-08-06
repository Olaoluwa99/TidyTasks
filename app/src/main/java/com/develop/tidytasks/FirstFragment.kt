package com.develop.tidytasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.develop.tidytasks.data.remote.NetworkResult
import com.develop.tidytasks.databinding.FragmentFirstBinding
import dagger.hilt.android.AndroidEntryPoint

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

        binding.btnLoginTest.setOnClickListener {
            viewModel.testLogin()
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