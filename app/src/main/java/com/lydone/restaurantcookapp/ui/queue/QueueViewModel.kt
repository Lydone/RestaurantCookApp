package com.lydone.restaurantcookapp.ui.queue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lydone.restaurantcookapp.data.Entry
import com.lydone.restaurantcookapp.data.QueueRepository
import com.lydone.restaurantcookapp.data.StopListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QueueViewModel @Inject constructor(
    private val queueRepository: QueueRepository,
    private val stopListRepository: StopListRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(State(emptyList()))
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = State(queueRepository.getActiveEntries())
        }
    }

    fun updateEntryStatus(id: Int) = viewModelScope.launch {
        queueRepository.updateEntryStatus(id)
        _state.value = State(queueRepository.getActiveEntries())
    }

    fun addDishToStopList(dish: Int) = viewModelScope.launch {
        stopListRepository.addToStopList(dish)
    }


    data class State(val queue: List<Entry>)
}

