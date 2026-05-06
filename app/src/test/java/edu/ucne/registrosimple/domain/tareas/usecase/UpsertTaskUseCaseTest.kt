package edu.ucne.registrosimple.domain.tareas.usecase

import edu.ucne.registrosimple.domain.tareas.model.Task
import edu.ucne.registrosimple.domain.tareas.repository.TaskRepository
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UpsertTaskUseCaseTest {

    private lateinit var useCase: UpsertTaskUseCase
    private lateinit var repository: TaskRepository

    @Before
    fun setup() {
        repository = mockk()
        useCase = UpsertTaskUseCase(repository)
    }

    @Test
    fun `invoke guarda tarea con datos validos`() = runTest {
        // Given
        val task = Task(tareaId = 0, descripcion = "Test task", tiempo = 30)
        coEvery { repository.upsert(task) } returns 1

        // When
        val result = useCase(task)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull())
        coVerify { repository.upsert(task) }
    }

    @Test
    fun `invoke falla con descripcion vacia`() = runTest {
        // Given
        val task = Task(tareaId = 0, descripcion = "", tiempo = 30)

        // When
        val result = useCase(task)

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
    }

    @Test
    fun `invoke falla con tiempo invalido`() = runTest {
        // Given
        val task = Task(tareaId = 0, descripcion = "Test task", tiempo = -5)

        // When
        val result = useCase(task)

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
    }
}
