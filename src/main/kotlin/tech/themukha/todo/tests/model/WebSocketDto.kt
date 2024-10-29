package tech.themukha.todo.tests.model

import kotlinx.serialization.Serializable

@Serializable
data class WebSocketDto(
    val data: TodoDto,
    val type: WebSocketType,
)

enum class WebSocketType {
    new_todo,
}


fun WebSocketDto.toTodoDto(): TodoDto = data