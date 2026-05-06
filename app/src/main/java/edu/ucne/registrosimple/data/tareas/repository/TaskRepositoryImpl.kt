package edu.ucne.registrosimple.data.tareas.repository

import edu.ucne.registrosimple.data.tareas.local.TaskDao
import edu.ucne.registrosimple.data.tareas.mapper.toDomain
import edu.ucne.registrosimple.data.tareas.mapper.toEntity
import edu.ucne.registrosimple.domain.tareas.model.Task
import edu.ucne.registrosimple.domain.tareas.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val localDataSource: TaskDao
) : TaskRepository {
    override fun observeTasks(): Flow<List<Task>> {
        return localDataSource.observeAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }
    
    override suspend fun getTask(id: Int): Task? {
        return localDataSource.getById(id)?.toDomain()
    }
    
    override suspend fun upsert(task: Task): Int {
        localDataSource.upsert(task.toEntity())
        return task.tareaId ?: 0
    }
    
    override suspend fun delete(id: Int) {
        localDataSource.deleteById(id)
    }

    override suspend fun exists(id: Int): Boolean {
        return localDataSource.exists(id)
    }
}
