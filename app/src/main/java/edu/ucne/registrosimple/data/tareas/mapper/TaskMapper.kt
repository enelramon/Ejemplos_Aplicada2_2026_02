package edu.ucne.registrosimple.data.tareas.mapper

import edu.ucne.registrosimple.data.tareas.local.TaskEntity
import edu.ucne.registrosimple.domain.tareas.model.Task

fun TaskEntity.toDomain(): Task = Task(
    tareaId = tareaId,
    descripcion = descripcion,
    tiempo = tiempo
)

fun Task.toEntity(): TaskEntity = TaskEntity(
    tareaId = tareaId,
    descripcion = descripcion,
    tiempo = tiempo
)
