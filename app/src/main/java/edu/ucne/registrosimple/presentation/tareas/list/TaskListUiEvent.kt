package edu.ucne.registrosimple.presentation.tareas.list

sealed class TaskListUiEvent {
    object Load : TaskListUiEvent()
    object Refresh : TaskListUiEvent()
    data class Delete(val id: Int) : TaskListUiEvent()
    data class ShowMessage(val message: String) : TaskListUiEvent()
    object ClearMessage : TaskListUiEvent()
    object CreateNew : TaskListUiEvent()
    data class Edit(val id: Int) : TaskListUiEvent()
}
