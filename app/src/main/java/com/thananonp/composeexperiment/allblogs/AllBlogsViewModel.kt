package com.thananonp.composeexperiment.allblogs

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch


class AllBlogsViewModel(
    private val service: AllBlogsService, private val delegate: AllBlogsViewDelegate
) : ViewModel(), AllBlogsViewDelegate by delegate {

    init {
        Log.d("ViewModel", "viewModel Created")
    }

    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<Error?>(null)
    var data by mutableStateOf<List<Blog>>(listOf())

    override fun onCleared() {
        super.onCleared()
        Log.d("ViewModel", "viewModel Cleared")
    }

    fun getAllBlogs() {
        Log.d("ViewModel", "viewModel Blogs")
        viewModelScope.launch {
            isLoading = true
            error = null

            try {
                data = service.getAllBlogs()
            } catch (e: Error) {
                error = e
            }
            isLoading = false

        }
    }

    companion object {
        fun factory(
            service: AllBlogsService,
            delegate: AllBlogsViewDelegate
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                AllBlogsViewModel(service = service, delegate = delegate)
            }
        }
    }
}