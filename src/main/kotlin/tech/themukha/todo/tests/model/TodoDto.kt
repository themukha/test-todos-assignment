package tech.themukha.todo.tests.model

import kotlinx.serialization.Serializable

@Serializable
data class TodoDto(
    val id: Long,
    val text: String,
    val completed: Boolean = false
)
