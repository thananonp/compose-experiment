package com.thananonp.composeexperiment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

class FormFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val sharedViewModel: SharedFormViewModel by activityViewModels()

        Log.d("s", findNavController().currentBackStack.value.toString())
        return ComposeView(requireContext()).apply {
            // Dispose of the Composition when the view's LifecycleOwner is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                Column {
                    Text(text = "this is a form ${sharedViewModel.count}", color = Color.White)
                    Button(onClick = {
                        sharedViewModel.updateCount()
                        findNavController().navigate(R.id.formFragment)
                    }) {
                        Text(text = "Go to next form")
                    }
                    if (sharedViewModel.count >= 5) {
                        Button(onClick = {
                            sharedViewModel.clear()
                            findNavController().clearBackStack(R.id.formFragment)
                            findNavController().popBackStack()
                        }) {
                            Text(text = "Pop back stack")
                        }
                    }
                }
            }
        }
    }
}

class SharedFormViewModel : ViewModel() {
    var count by mutableIntStateOf(0)

    fun updateCount() {
        count += 1
    }

    fun clear() {
        count = 0
    }
}