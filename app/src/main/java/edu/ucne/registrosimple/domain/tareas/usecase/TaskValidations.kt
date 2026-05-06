package edu.ucne.registrosimple.domain.tareas.usecase

data class ValidationResult(
    val isValid: Boolean,
    val error: String? = null
)

fun validateDescripcion(descripcion: String): ValidationResult {
    return when {
        descripcion.isBlank() -> ValidationResult(false, "La descripción no puede estar vacía")
        descripcion.length < 3 -> ValidationResult(false, "La descripción debe tener al menos 3 caracteres")
        else -> ValidationResult(true)
    }
}

fun validateTiempo(tiempo: String): ValidationResult {
    return when {
        tiempo.isBlank() -> ValidationResult(false, "El tiempo no puede estar vacío")
        tiempo.toIntOrNull() == null -> ValidationResult(false, "El tiempo debe ser un número")
        tiempo.toInt() <= 0 -> ValidationResult(false, "El tiempo debe ser mayor a 0")
        else -> ValidationResult(true)
    }
}
