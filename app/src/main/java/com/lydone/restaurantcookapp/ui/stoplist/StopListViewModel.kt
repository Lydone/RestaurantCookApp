package com.lydone.restaurantcookapp.ui.stoplist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lydone.restaurantcookapp.data.Dish
import com.lydone.restaurantcookapp.data.StopListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StopListViewModel @Inject constructor(private val repository: StopListRepository) :
    ViewModel() {

    private val _state = MutableStateFlow<State?>(null)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch { loadDishes() }
    }

    fun changeDishInStopList(dish: Dish) = viewModelScope.launch {
        if (dish.inStopList) {
            repository.removeFromStopList(dish.id)
        } else {
            repository.addToStopList(dish.id)
        }
        loadDishes()
    }

    private suspend fun loadDishes() {
        _state.value = State(
            repository.getDishes()
                .sortedWith(compareByDescending(Dish::inStopList).thenBy(Dish::name))
        )
    }

    data class State(val dishes: List<Dish>)
}