package tech.themukha.todo.providers

import org.apache.http.HttpStatus
import org.junit.jupiter.params.provider.Arguments
import tech.themukha.todo.tests.model.TodoDto
import tech.themukha.todo.tests.utils.IdGenerator
import java.util.stream.Stream

object WebSocketDataProvider {
    @JvmStatic
    fun webSocketTestArguments(): Stream<Arguments> = Stream.of(
        Arguments.of(
            WebSocketTestData(
                todos = listOf(
                    TodoDto(
                        id  = IdGenerator.generateRandomLong(),
                        text = "WebSocket Test TODO 1",
                        completed = false
                    ),
                    TodoDto(
                        id = IdGenerator.generateRandomLong(),
                        text = "WebSocket Test TODO 2",
                        completed = true
                    ),
                    TodoDto(
                        id = IdGenerator.generateRandomLong(),
                        text = "WebSocket Test TODO 3",
                        completed = false
                    )
                ),
                expectedResponseCode = HttpStatus.SC_CREATED
            )
        )
    )

}