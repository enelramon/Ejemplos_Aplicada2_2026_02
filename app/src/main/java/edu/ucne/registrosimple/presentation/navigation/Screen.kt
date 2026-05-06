package edu.ucne.registrosimple.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object TaskList : Screen()
    
    @Serializable
    data class TaskForm(val taskId: Int) : Screen()
}
