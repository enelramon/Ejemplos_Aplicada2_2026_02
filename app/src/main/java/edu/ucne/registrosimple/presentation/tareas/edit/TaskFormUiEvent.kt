package edu.ucne.registrosimple.presentation.tareas.edit

sealed interface TaskFormUiEvent {
    data class Load(val id: Int?) : TaskFormUiEvent
    data class DescripcionChanged(val value: String) : TaskFormUiEvent
    data class TiempoChanged(val value: String) : TaskFormUiEvent
    data object Save : TaskFormUiEvent
    data object Delete : TaskFormUiEvent
}
