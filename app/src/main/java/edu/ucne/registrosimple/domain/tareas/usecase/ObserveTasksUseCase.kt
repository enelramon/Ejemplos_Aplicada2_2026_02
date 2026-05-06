package edu.ucne.registrosimple.domain.tareas.usecase

import edu.ucne.registrosimple.domain.tareas.model.Task
import edu.ucne.registrosimple.domain.tareas.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    operator fun invoke(): Flow<List<Task>> = repository.observeTasks()
}
