package tech.themukha.todo.tests.model

data class TodoDto(
    val id: ULong,
    val text: String,
    val completed: Boolean = false
)
