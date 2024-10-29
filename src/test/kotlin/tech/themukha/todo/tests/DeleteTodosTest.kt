package tech.themukha.todo.tests

import io.qameta.allure.Epic
import io.qameta.allure.Feature
import org.apache.http.HttpStatus
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import tech.themukha.todo.tests.flow.TestFlow
import tech.themukha.todo.tests.model.TodoDto

@DisplayName("Delete Todo Tests")
@Epic("Todos Endpoint")
@Feature("DELETE /todos")
class DeleteTodosTest : BaseTest() {

    @ParameterizedTest(name = "{index} => {0}")
    @MethodSource("tech.themukha.todo.tests.providers.TodoDataProvider#deleteTodoArguments")
    @DisplayName("Delete Todo Test")
    fun `Delete Todo Test`(
        testCase: String,
        todoToDelete: TodoDto,
        expectedResponseCode: Int,
        isExists: Boolean,
        isAuthorized: Boolean
    ) {
        TestFlow()
            .step("Create TODO for deletion") {
                `Add new TODO`(
                    todoToDelete,
                    HttpStatus.SC_CREATED
                )
            }
            .step("Delete TODO") {
                `Delete existing TODO`(
                    todoToDelete.id,
                    expectedResponseCode,
                    isAuthorized
                )
            }
            .step("Check that TODO is deleted") {
                `Check TODO by ID`(
                    todoToDelete,
                    isExists
                )
            }
    }

}