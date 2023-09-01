package com.thananonp.composeexperiment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.findNavController

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            // Dispose of the Composition when the view's LifecycleOwner is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                Scaffold(bottomBar = {
                    MainBottomNavigationBar(findNavController())
                }) {
                    Box(modifier = Modifier.padding(it)) {
                        Text("Home View", color = Color.White)
                        Button(onClick = { findNavController().navigate(R.id.action_homeFragment_to_allBlogsFragment) }) {
                            Text(text = "Go to blog")
                        }
                    }
                }
            }
        }
    }
}

sealed class BottomNavigationScreens(val id: Int, val name: String, val icon: ImageVector) {
    object Home : BottomNavigationScreens(R.id.homeFragment, "Home", Icons.Filled.Home)
    object Form : BottomNavigationScreens(R.id.formFragment, "Form", Icons.Filled.ShoppingCart)
    object Notifications : BottomNavigationScreens(
        R.id.onboardingFragment, "Notifications", Icons.Filled.Notifications
    )

    object More : BottomNavigationScreens(R.id.homeFragment, "More", Icons.Filled.Menu)
}

@Composable
fun MainBottomNavigationBar(
    navController: NavController,
) {
    val bottomItems = listOf(
        BottomNavigationScreens.Home,
        BottomNavigationScreens.Form,
        BottomNavigationScreens.Notifications,
        BottomNavigationScreens.More
    )
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        bottomItems.forEach { screen ->
            BottomNavigationItem(icon = {
                Icon(
                    imageVector = screen.icon, contentDescription = null
                )
            },
                label = { Text(text = screen.name) },
                selected = currentDestination?.hierarchy?.any { it.id == screen.id } == true,
                onClick = {
                    navController.navigate(resId = screen.id)
                })
        }
    }
}