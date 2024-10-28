package tech.themukha.todo.tests

import io.qameta.allure.Epic
import io.qameta.allure.Feature
import org.apache.http.HttpStatus
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import tech.themukha.todo.tests.flow.TestFlow
import tech.themukha.todo.tests.model.TodoDto
import tech.themukha.todo.tests.utils.DataClassExtensions.trimWithOffsetAndLimit
import tech.themukha.todo.tests.utils.IdGenerator

@DisplayName("Get Todo List tests")
@Epic("Todos Endpoint")
@Feature("GET /todos")
class GetTodosTest : BaseTest() {

    @ParameterizedTest(name = "{index} => offset = {0}, limit = {1}, expectedSize = {2}, expectedResponseCode = {3}")
    @MethodSource("tech.themukha.todo.tests.providers.TodoDataProvider#getTodoProvider")
    fun `Get TODOs with offset and limit`(
        offset: Int?,
        limit: Int?,
        expectedSize: Int?,
        expectedResponseCode: Int,
        createTodos: Int
    ) {
        val expectedTodos = mutableListOf<TodoDto>()
        TestFlow()
            .step("Create TODOs for test") {
                repeat(createTodos) {
                    val id = IdGenerator.generateRandomLong()
                    val newTodo = TodoDto(
                        id = id,
                        text = "Test TODO $id"
                    )
                    println(newTodo)
                    `Add new TODO`(newTodo, expectedResponseCode = HttpStatus.SC_CREATED)
                    expectedTodos.add(newTodo)
                }
            }
            .step("Get all TODOs with offset $offset and limit $limit") {
                val trimmedTodos = expectedTodos.trimWithOffsetAndLimit(offset = offset, limit = limit)
                `Check TODOs by ID`(trimmedTodos)
            }
    }

}