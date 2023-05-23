package com.lydone.restaurantcookapp.ui.queue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lydone.restaurantcookapp.data.Entry
import com.lydone.restaurantcookapp.data.QueueRepository
import com.lydone.restaurantcookapp.data.StopListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class QueueViewModel @Inject constructor(
    private val queueRepository: QueueRepository,
    private val stopListRepository: StopListRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(State(Result.success(emptyList())))
    val state = _state.asStateFlow()

    private var job: Job? = null

    init {
        updatePeriodicallyUpdateQueueJob()
    }

    fun updatePeriodicallyUpdateQueueJob() {
        job?.cancel()
        job = viewModelScope.launch {
            while (isActive) {
                try {
                    _state.value = State(Result.success(queueRepository.getActiveEntries()))
                } catch (e: Exception) {
                    _state.value = State(Result.failure(e))
                    cancel()
                }
                delay(30L.seconds)
            }
        }
    }

    fun updateEntryStatus(id: Int) = viewModelScope.launch {
        queueRepository.updateEntryStatus(id)
        updatePeriodicallyUpdateQueueJob()
    }

    fun addDishToStopList(dish: Int) = viewModelScope.launch {
        stopListRepository.addToStopList(dish)
    }


    data class State(val queue: Result<List<Entry>>)
}

