package com.lydone.restaurantcookapp.ui.queue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lydone.restaurantcookapp.data.Entry
import com.lydone.restaurantcookapp.data.QueueRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QueueViewModel @Inject constructor(private val repository: QueueRepository) : ViewModel() {

    private val _state = MutableStateFlow(State(emptyList()))
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = State(repository.getEntries())
        }
    }

    fun updateEntryStatus(id: Int) {
        viewModelScope.launch {
            repository.updateEntryStatus(id)
            _state.value = State(repository.getEntries())
        }
    }


    data class State(val queue: List<Entry>)
}

