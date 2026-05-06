package edu.ucne.registrosimple.presentation.tareas.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrosimple.domain.tareas.usecase.DeleteTaskUseCase
import edu.ucne.registrosimple.domain.tareas.usecase.ObserveTasksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val observeTasksUseCase: ObserveTasksUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(TaskListUiState(isLoading = true))
    val state: StateFlow<TaskListUiState> = _state.asStateFlow()

    init {
        loadTasks()
    }

    fun onEvent(event: TaskListUiEvent) {
        when (event) {
            TaskListUiEvent.Load -> loadTasks()
            TaskListUiEvent.Refresh -> loadTasks()
            is TaskListUiEvent.Delete -> onDelete(event.id)
            is TaskListUiEvent.ShowMessage -> _state.update { it.copy(message = event.message) }
            TaskListUiEvent.ClearMessage -> _state.update { it.copy(message = null) }
            TaskListUiEvent.CreateNew -> _state.update { it.copy(navigateToCreate = true) }
            is TaskListUiEvent.Edit -> _state.update { it.copy(navigateToEditId = event.id) }
        }
    }

    fun loadTasks() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            observeTasksUseCase().collectLatest { list ->
                _state.update { it.copy(isLoading = false, tasks = list, message = null) }
            }
        }
    }

    private fun onDelete(id: Int) {
        viewModelScope.launch {
            deleteTaskUseCase(id)
            onEvent(TaskListUiEvent.ShowMessage("Eliminado"))
        }
    }
}
