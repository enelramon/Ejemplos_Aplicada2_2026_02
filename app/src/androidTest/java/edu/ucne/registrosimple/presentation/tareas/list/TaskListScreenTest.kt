package edu.ucne.registrosimple.presentation.tareas.list

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import edu.ucne.registrosimple.domain.tareas.model.Task
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalMaterial3Api::class)
class TaskListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun taskListBody_muestra_loading_cuando_isLoading_es_true() {
        // Given
        val state = TaskListUiState(isLoading = true)

        // When
        composeTestRule.setContent {
            MaterialTheme {
                TaskListBody(state = state, onEvent = {}, onAddTask = {})
            }
        }

        // Then
        composeTestRule.onNodeWithTag("loading").assertIsDisplayed()
    }

    @Test
    fun taskListBody_muestra_mensaje_vacio_cuando_no_hay_tareas() {
        // Given
        val state = TaskListUiState(isLoading = false, tasks = emptyList())

        // When
        composeTestRule.setContent {
            MaterialTheme {
                TaskListBody(state = state, onEvent = {}, onAddTask = {})
            }
        }

        // Then
        composeTestRule.onNodeWithTag("empty_message").assertIsDisplayed()
        composeTestRule.onNodeWithText("No hay tareas").assertIsDisplayed()
    }

    @Test
    fun taskListBody_muestra_lista_de_tareas() {
        // Given
        val tasks = listOf(
            Task(tareaId = 1, descripcion = "Tarea 1", tiempo = 30),
            Task(tareaId = 2, descripcion = "Tarea 2", tiempo = 45)
        )
        val state = TaskListUiState(isLoading = false, tasks = tasks)

        // When
        composeTestRule.setContent {
            MaterialTheme {
                TaskListBody(state = state, onEvent = {}, onAddTask = {})
            }
        }

        // Then
        composeTestRule.onNodeWithText("Tarea 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tarea 2").assertIsDisplayed()
    }

    @Test
    fun fab_dispara_evento_CreateNew() {
        // Given
        val state = TaskListUiState(isLoading = false)
        var eventCaptured: TaskListUiEvent? = null

        // When
        composeTestRule.setContent {
            MaterialTheme {
                TaskListBody(
                    state = state,
                    onEvent = { eventCaptured = it },
                    onAddTask = { eventCaptured = TaskListUiEvent.CreateNew }
                )
            }
        }
        composeTestRule.onNodeWithTag("fab_add").performClick()

        // Then
        assertTrue(eventCaptured is TaskListUiEvent.CreateNew)
    }
}
