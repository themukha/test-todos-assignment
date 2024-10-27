package tech.themukha.todo.tests

import org.apache.http.HttpStatus
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import tech.themukha.todo.tests.flow.TestFlow
import tech.themukha.todo.tests.model.TodoDto
import tech.themukha.todo.tests.utils.IdGenerator

@DisplayName("Create Todo tests")
class CreateTodoDtoTest : BaseTest() {

    @Test
    @DisplayName("Create Todo")
    fun `Create Todo`() {
        val newTodo = TodoDto(
            id = IdGenerator.generateRandomLong(),
            text = "New todo item 777"
        )

        TestFlow()
            .step("Create todo") {
                `Add new TODO`(
                    newTodo,
                    expectedResponseCode = HttpStatus.SC_CREATED
                )
            }
            .step("Check todo") {
                `Check TODO by ID`(
                    newTodo
                )
            }
    }

    @Test
    @DisplayName("Create Todo with empty text")
    fun `Create Todo with empty text`() {
        val newTodo = TodoDto(
            id = IdGenerator.generateRandomLong(),
            text = ""
        )

        TestFlow()
            .step("Create todo") {
                `Add new TODO`(
                    newTodo,
                    expectedResponseCode = HttpStatus.SC_CREATED
                )
            }
            .step("Check todo") {
                `Check TODO by ID`(
                    newTodo
                )
            }
    }

    @Test
    @DisplayName("Create Todo with max long text")
    fun `Create Todo with max long text`() {
        val newTodo = TodoDto(
            id = IdGenerator.generateRandomLong(),
            text = "C".repeat(16330)
        )

        TestFlow()
            .step("Create todo") {
                `Add new TODO`(
                    newTodo,
                    expectedResponseCode = HttpStatus.SC_CREATED
                )
            }
            .step("Check todo") {
                `Check TODO by ID`(
                    newTodo
                )
            }
    }

    @Test
    @DisplayName("Create Todo with over than max long text")
    fun `Create Todo with over than max long text`() {
        val newTodo = TodoDto(
            id = IdGenerator.generateRandomLong(),
            text = "C".repeat(16331)
        )

        TestFlow()
            .step("Create todo") {
                `Add new TODO`(
                    newTodo,
                    expectedResponseCode = HttpStatus.SC_REQUEST_TOO_LONG
                )
            }
            .step("Check todo") {
                `Check TODO by ID`(
                    newTodo,
                    isExists = false
                )
            }
    }

}