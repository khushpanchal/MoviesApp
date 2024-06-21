package com.khush.moviesapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.khush.moviesapp.common.UIState
import com.khush.moviesapp.databinding.ActivityMainBinding
import com.khush.moviesapp.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: MainAdapter

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = MainAdapter()
        collector()
        setupUI()
    }

    private fun setupUI() {
        binding.rv.adapter = adapter
        binding.rv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rv.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun collector() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.mainItem.collect {
                    when (it) {
                        is UIState.Success -> {
                            binding.progress.visibility = View.GONE
                            binding.error.visibility = View.GONE
                            binding.rv.visibility = View.VISIBLE
                            adapter.setItems(it.data)
                        }

                        is UIState.Failure -> {
                            binding.progress.visibility = View.GONE
                            binding.error.visibility = View.VISIBLE
                            binding.rv.visibility = View.GONE
                            binding.error.text = it.throwable.toString()
                        }

                        is UIState.Loading -> {
                            binding.progress.visibility = View.VISIBLE
                            binding.error.visibility = View.GONE
                            binding.rv.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

}