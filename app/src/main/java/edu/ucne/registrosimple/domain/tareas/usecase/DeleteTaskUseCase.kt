package edu.ucne.registrosimple.domain.tareas.usecase

import edu.ucne.registrosimple.domain.tareas.repository.TaskRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(id: Int) = repository.delete(id)
}
