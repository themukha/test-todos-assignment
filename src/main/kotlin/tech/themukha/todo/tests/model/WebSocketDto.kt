package tech.themukha.todo.tests.model

import kotlinx.serialization.Serializable

@Serializable
data class WebSocketDto(
    val data: TodoDto,
    val type: WebSocketType,
)

@Suppress("unused")
enum class WebSocketType {
    new_todo,
}
