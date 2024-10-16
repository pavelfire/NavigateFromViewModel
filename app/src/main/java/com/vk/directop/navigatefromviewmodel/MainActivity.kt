package com.vk.directop.navigatefromviewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.vk.directop.navigatefromviewmodel.ui.theme.NavigateFromViewModelTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import java.util.UUID

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigateFromViewModelTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    val navigator = koinInject<Navigator>()

                    ObserveAsEvents(flow = navigator.navigationActions) { action ->
                        when (action) {
                            is NavigationAction.Navigate -> navController.navigate(
                                action.destination
                            ) {
                                action.navOptions(this)
                            }

                            NavigationAction.NavigateUp -> navController.navigateUp()
                        }
                    }
                    NavHost(
                        navController = navController,
                        startDestination = navigator.startDestination,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        navigation<Destination.AuthGraph>(
                            startDestination = Destination.LoginScreen
                        ) {
                            composable<Destination.LoginScreen> {
                                val viewModel = koinViewModel<LoginViewModel>()
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Button(onClick = viewModel::login) {
                                        Text(text = "Login")
                                    }
                                }
                            }
                        }

                        navigation<Destination.HomeGraph>(
                            startDestination = Destination.HomeScreen
                        ) {
                            composable<Destination.HomeScreen> {
                                val viewModel = koinViewModel<HomeViewModel>()
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Button(onClick = {
                                        viewModel.navigateToDetail(UUID.randomUUID().toString())
                                    }) {
                                        Text(text = "Go to detail")
                                    }
                                    Spacer(modifier = Modifier.padding(vertical = 12.dp))
                                    Button(onClick = {
                                        viewModel.logout()
                                    }) {
                                        Text(text = "Logout")
                                    }
                                }
                            }

                            composable<Destination.DetailScreen> {
                                val viewModel = koinViewModel<DetailViewModel>()
                                val args = it.toRoute<Destination.DetailScreen>()
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Text(text = "ID: ${args.id}")
                                    Button(onClick = viewModel::navigateUp) {
                                        Text(text = "Go back")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
