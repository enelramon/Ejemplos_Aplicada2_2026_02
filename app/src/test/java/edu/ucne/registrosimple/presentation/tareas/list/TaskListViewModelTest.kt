package edu.ucne.registrosimple.presentation.tareas.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import edu.ucne.registrosimple.domain.tareas.model.Task
import edu.ucne.registrosimple.domain.tareas.usecase.DeleteTaskUseCase
import edu.ucne.registrosimple.domain.tareas.usecase.ObserveTasksUseCase
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class TaskListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: TaskListViewModel
    private lateinit var observeTasksUseCase: ObserveTasksUseCase
    private lateinit var deleteTaskUseCase: DeleteTaskUseCase

    @Before
    fun setup() {
        observeTasksUseCase = mockk()
        deleteTaskUseCase = mockk()
        
        every { observeTasksUseCase() } returns flowOf(emptyList())
        
        viewModel = TaskListViewModel(
            observeTasksUseCase,
            deleteTaskUseCase
        )
    }

    @Test
    fun `loadTasks carga lista de tareas correctamente`() = runTest {
        // Given
        val tasks = listOf(
            Task(tareaId = 1, descripcion = "Tarea 1", tiempo = 30),
            Task(tareaId = 2, descripcion = "Tarea 2", tiempo = 45)
        )
        every { observeTasksUseCase() } returns flowOf(tasks)
        
        // When
        viewModel = TaskListViewModel(observeTasksUseCase, deleteTaskUseCase)
        advanceUntilIdle()

        // Then
        assertFalse(viewModel.state.value.isLoading)
        assertEquals(2, viewModel.state.value.tasks.size)
        assertEquals("Tarea 1", viewModel.state.value.tasks[0].descripcion)
    }

    @Test
    fun `onEvent Delete elimina tarea`() = runTest {
        // Given
        val taskId = 1
        coEvery { deleteTaskUseCase(taskId) } just Runs

        // When
        viewModel.onEvent(TaskListUiEvent.Delete(taskId))
        advanceUntilIdle()

        // Then
        coVerify { deleteTaskUseCase(taskId) }
        assertEquals("Eliminado", viewModel.state.value.message)
    }

    @Test
    fun `onEvent CreateNew activa navegacion a crear`() {
        // When
        viewModel.onEvent(TaskListUiEvent.CreateNew)

        // Then
        assertTrue(viewModel.state.value.navigateToCreate)
    }

    @Test
    fun `onEvent Edit activa navegacion a editar con id`() {
        // Given
        val taskId = 5

        // When
        viewModel.onEvent(TaskListUiEvent.Edit(taskId))

        // Then
        assertEquals(taskId, viewModel.state.value.navigateToEditId)
    }

    @Test
    fun `onEvent ClearMessage limpia mensaje`() {
        // Given
        viewModel.onEvent(TaskListUiEvent.ShowMessage("Test message"))

        // When
        viewModel.onEvent(TaskListUiEvent.ClearMessage)

        // Then
        assertNull(viewModel.state.value.message)
    }
}

// Regla para el Dispatcher Main en tests
@ExperimentalCoroutinesApi
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
