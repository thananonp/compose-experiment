package com.thananonp.composeexperiment.allblogs

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.thananonp.composeexperiment.MainActivity
import com.thananonp.composeexperiment.MyApplication
import com.thananonp.composeexperiment.R

class AllBlogsFragment : Fragment(), AllBlogsViewDelegate {
    private val viewModel: AllBlogsViewModel by viewModels {
        AllBlogsViewModel.factory(
            MyApplication.appModule.allBlogsService, this
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        Log.d("ViewModel", "Fragment Created ${this.id}")
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AllBlogsView(viewModel)
            }
        }
    }

    override fun didClickBlog(blog: Blog) {
        findNavController().navigate(
            AllBlogsFragmentDirections.actionAllBlogsFragmentToBlogDetailFragment(
                blog
            )
        )
    }
}

@Composable
fun AllBlogsView(viewModel: AllBlogsViewModel) {
    var hasAppeared by rememberSaveable {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = "Init", block = {
        if (!hasAppeared) {
            viewModel.getAllBlogs()
            hasAppeared = true
        }
    })

    Column {
        if (viewModel.isLoading) {
            CircularProgressIndicator()
        } else if (viewModel.error != null) {
            Text(text = "Error", color = Color.Red)
            Button(onClick = { viewModel.getAllBlogs() }) {
                Text("Retry")
            }
        } else {
            viewModel.data.forEach { blog ->
                Column {
                    Button({
                        viewModel.didClickBlog(blog)

                    }) {
                        Card {
                            Text(text = blog.title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            Text(text = blog.description)
                        }
                    }
                }
            }
        }
    }
}

interface AllBlogsViewDelegate {
    fun didClickBlog(blog: Blog)
}