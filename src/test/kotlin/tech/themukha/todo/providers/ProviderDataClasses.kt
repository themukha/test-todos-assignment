package tech.themukha.todo.providers

import tech.themukha.todo.tests.model.TodoDto

data class WebSocketTestData(
    val todos: List<TodoDto>,
    val expectedResponseCode: Int
)

