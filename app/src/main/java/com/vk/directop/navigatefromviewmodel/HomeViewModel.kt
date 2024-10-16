package com.vk.directop.navigatefromviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HomeViewModel(
    private val navigator: Navigator,
) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            navigator.navigate(
                destination = Destination.AuthGraph,
                navOptions = {
                    popUpTo(Destination.HomeGraph) {
                        inclusive = true
                    }
                }
            )
        }
    }

    fun navigateToDetail(id: String) {
        viewModelScope.launch {
            navigator.navigate(
                destination = Destination.DetailScreen(id),
            )
        }
    }
}