package tech.themukha.todo.tests

import io.qameta.allure.Epic
import io.qameta.allure.Feature
import org.apache.http.HttpStatus
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import tech.themukha.todo.tests.flow.BaseTest
import tech.themukha.todo.tests.flow.TestFlow
import tech.themukha.todo.tests.model.TodoDto

@DisplayName("Update Todo Tests")
@Epic("Todos Endpoint")
@Feature("PUT /todos")
class PutTodosTest : BaseTest() {

    @ParameterizedTest(name = "{index} => {0}")
    @MethodSource("tech.themukha.todo.providers.TodoDataProvider#putTodoProvider")
    @DisplayName("Update existing TODO")
    fun `Update existing TODO`(
        testCase: String,
        initialTodo: TodoDto,
        updatedTodo: TodoDto,
        expectedResponseCode: Int,
        isExists: Boolean
    ) {
        TestFlow()
            .step("Create initial TODO") {
                `Add new TODO`(initialTodo, expectedResponseCode = HttpStatus.SC_CREATED)
            }
            .step("Update TODO") {
                if (isExists) {
                    `Update existing TODO`(
                        initialTodo.id,
                        updatedTodo,
                        expectedResponseCode = expectedResponseCode,
                    )
                } else {
                    `Update existing TODO`(
                        updatedTodo.id,
                        initialTodo,
                        expectedResponseCode = expectedResponseCode,
                    )
                }
            }
            .step("Check updated TODO") {
                `Check TODO by ID`(updatedTodo, isExists = isExists)
            }
    }

}