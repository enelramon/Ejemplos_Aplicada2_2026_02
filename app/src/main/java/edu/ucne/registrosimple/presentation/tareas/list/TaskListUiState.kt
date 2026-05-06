package edu.ucne.registrosimple.presentation.tareas.list

import edu.ucne.registrosimple.domain.tareas.model.Task

data class TaskListUiState(
    val isLoading: Boolean = false,
    val tasks: List<Task> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null,
    val error: String? = null
)
