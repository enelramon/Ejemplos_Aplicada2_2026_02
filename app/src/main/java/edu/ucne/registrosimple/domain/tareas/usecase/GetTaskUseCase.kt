package edu.ucne.registrosimple.domain.tareas.usecase

import edu.ucne.registrosimple.domain.tareas.model.Task
import edu.ucne.registrosimple.domain.tareas.repository.TaskRepository
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(id: Int): Task? = repository.getTask(id)
}
