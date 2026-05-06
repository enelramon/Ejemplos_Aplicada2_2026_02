package edu.ucne.registrosimple.data.tareas.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import edu.ucne.registrosimple.data.tareas.local.TaskDao
import edu.ucne.registrosimple.data.tareas.local.TaskEntity
import edu.ucne.registrosimple.domain.tareas.model.Task
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TaskRepositoryImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: TaskRepositoryImpl
    private lateinit var dao: TaskDao

    @Before
    fun setup() {
        dao = mockk(relaxed = true)
        repository = TaskRepositoryImpl(dao)
    }

    @Test
    fun `upsert guarda tarea correctamente`() = runTest {
        // Given
        val task = Task(
            tareaId = 0,
            descripcion = "Nueva tarea",
            tiempo = 30
        )
        val taskSlot = slot<TaskEntity>()
        coEvery { dao.upsert(capture(taskSlot)) } just Runs

        // When
        val result = repository.upsert(task)

        // Then
        assertEquals(0, result)
        coVerify { dao.upsert(any()) }
        assertEquals("Nueva tarea", taskSlot.captured.descripcion)
        assertEquals(30, taskSlot.captured.tiempo)
    }

    @Test
    fun `upsert actualiza tarea correctamente`() = runTest {
        // Given
        val task = Task(tareaId = 1, descripcion = "Tarea actualizada", tiempo = 45)
        coEvery { dao.upsert(any()) } just Runs

        // When
        val result = repository.upsert(task)

        // Then
        assertEquals(1, result)
        coVerify { dao.upsert(any()) }
    }

    @Test
    fun `delete elimina tarea correctamente`() = runTest {
        // Given
        val taskId = 1
        coEvery { dao.deleteById(taskId) } just Runs

        // When
        repository.delete(taskId)

        // Then
        coVerify { dao.deleteById(taskId) }
    }

    @Test
    fun `observeTasks retorna flow de tareas`() = runTest {
        // Given
        val entities = listOf(
            TaskEntity(1, "Tarea 1", 30),
            TaskEntity(2, "Tarea 2", 45)
        )
        every { dao.observeAll() } returns flowOf(entities)

        // When
        val result = repository.observeTasks().first()

        // Then
        assertEquals(2, result.size)
        assertEquals("Tarea 1", result[0].descripcion)
        assertEquals("Tarea 2", result[1].descripcion)
    }

    @Test
    fun `getTask retorna tarea por id`() = runTest {
        // Given
        val entity = TaskEntity(1, "Tarea Test", 30)
        coEvery { dao.getById(1) } returns entity

        // When
        val result = repository.getTask(1)

        // Then
        assertNotNull(result)
        assertEquals("Tarea Test", result?.descripcion)
        assertEquals(30, result?.tiempo)
    }
}
