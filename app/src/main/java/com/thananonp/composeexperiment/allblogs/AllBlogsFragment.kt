package com.thananonp.composeexperiment.allblogs

import android.os.Bundle
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thananonp.composeexperiment.MyApplication
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AllBlogsFragment : Fragment(), AllBlogsViewDelegate {
    private val viewModel = AllBlogsViewModel(MyApplication.appModule.allBlogsService, this)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AllBlogsView(viewModel)
            }
        }
    }

    override fun didClickBlog(blog: Blog) {
        Toast.makeText(requireContext(), blog.title, Toast.LENGTH_LONG).show()
    }
}

@Composable
fun AllBlogsView(viewModel: AllBlogsViewModel) {
    LaunchedEffect(key1 = "Init", block = {
        viewModel.getAllBlogs()
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
                    Button({ viewModel.didClickBlog(blog) }) {
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