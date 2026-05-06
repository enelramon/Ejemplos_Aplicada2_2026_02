package edu.ucne.registrosimple.domain.tareas.repository

import edu.ucne.registrosimple.domain.tareas.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun observeTasks(): Flow<List<Task>>
    suspend fun getTask(id: Int): Task?
    suspend fun upsert(task: Task): Int
    suspend fun delete(id: Int)
    suspend fun exists(id: Int): Boolean 
}
