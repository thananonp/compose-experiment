package com.thananonp.composeexperiment.allblogs

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class AllBlogsViewModel(
    private val service: AllBlogsService, private val delegate: AllBlogsViewDelegate
) : ViewModel(), AllBlogsViewDelegate by delegate {
    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<Error?>(null)
    var data by mutableStateOf<List<Blog>>(listOf())

    override fun onCleared() {
        super.onCleared()
        Log.d("ViewModel", "Cleared")
    }

    fun getAllBlogs() {
        Log.d("ViewModel", "Blogs")
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
}