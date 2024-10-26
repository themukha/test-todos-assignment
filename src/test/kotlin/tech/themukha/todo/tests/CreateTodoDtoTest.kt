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
            id = IdGenerator.generateRandomULong(),
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
            id = IdGenerator.generateRandomULong(),
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
            id = IdGenerator.generateRandomULong(),
            text = "C".repeat(128)
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
    @DisplayName("Create Todo with max ULong id")
    fun `Create Todo with max ULong id`() {
        val newTodo = TodoDto(
            id = ULong.MAX_VALUE,
            text = "New todo item 888"
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

}