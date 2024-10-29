package tech.themukha.todo.tests

import io.qameta.allure.Epic
import io.qameta.allure.Feature
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import tech.themukha.todo.tests.flow.BaseTest
import tech.themukha.todo.tests.flow.TestFlow
import tech.themukha.todo.tests.model.TodoDto

@DisplayName("WebSocket Todos tests")
@Epic("WebSocket Todos Notifications")
@Feature("/ws")
class WebSocketTest : BaseTest() {

    @ParameterizedTest(name = "{index} => text={0}, completed={1}")
    @MethodSource("tech.themukha.todo.providers.WebSocketDataProvider#webSocketTestArguments")
    @DisplayName("Test WebSocket Notifications")
    fun `Test WebSocket Notifications`(
        newTodos: List<TodoDto>,
        expectedResponseCode: Int,
    ) {

        TestFlow()
            .step("Create, Update and Delete TODO via REST API") {
                newTodos.forEach { todo ->
                    `Add new TODO`(
                        todo,
                        expectedResponseCode
                    )
                    `Update existing TODO`(
                        todo.id,
                        todo.copy(id = todo.id + 1)
                    )
                    `Delete existing TODO`(
                        todo.id + 1
                    )
                }
            }
            .step("Check TODO notification") {
                `Check all expected WebSocket messages`(
                    newTodos
                )
            }
    }

}