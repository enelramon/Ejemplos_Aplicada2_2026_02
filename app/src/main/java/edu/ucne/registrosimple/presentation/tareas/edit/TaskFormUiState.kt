package edu.ucne.registrosimple.presentation.tareas.edit

data class TaskFormUiState(
    val tareaId: Int? = null,
    val descripcion: String = "",
    val tiempo: String = "",
    val descripcionError: String? = null,
    val tiempoError: String? = null,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val isNew: Boolean = true,
    val saved: Boolean = false,
    val deleted: Boolean = false
)
