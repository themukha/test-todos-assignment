package tech.themukha.todo.tests

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import tech.themukha.todo.tests.flow.TestFlow
import tech.themukha.todo.tests.model.TodoDto

@DisplayName("Create Todo tests")
class PostTodosTest : BaseTest() {

    @ParameterizedTest(name = "{index} => {0}")
    @MethodSource("tech.themukha.todo.tests.providers.TodoDataProvider#createTodoProvider")
    @DisplayName("Create Todo")
    fun `Create Todo`(
        testCase: String,
        newTodo: TodoDto,
        expectedResponse: Int,
        isExists: Boolean = true
    ) {
        TestFlow()
            .step("Create todo") {
                `Add new TODO`(
                    newTodo,
                    expectedResponseCode = expectedResponse
                )
            }
            .step("Check todo") {
                `Check TODO by ID`(
                    newTodo,
                    isExists = isExists
                )
            }
    }

}