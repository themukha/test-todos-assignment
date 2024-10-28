package tech.themukha.todo.tests.providers

import org.apache.http.HttpStatus
import org.junit.jupiter.params.provider.Arguments
import tech.themukha.todo.tests.model.TodoDto
import tech.themukha.todo.tests.utils.IdGenerator
import java.util.stream.Stream

object TodoDataProvider {
    @JvmStatic
    fun createTodoProvider(): Stream<Arguments> = Stream.of(
        Arguments.of(
            "Create Todo",
            TodoDto(
                id = IdGenerator.generateRandomLong(),
                text = "New todo item 777"
            ),
            HttpStatus.SC_CREATED,
            true
        ),
        Arguments.of(
            "with empty text",
            TodoDto(
                id = IdGenerator.generateRandomLong(),
                text = ""
            ),
            HttpStatus.SC_CREATED,
            true
        ),
        Arguments.of(
            "with max long text",
            TodoDto(
                id = IdGenerator.generateRandomLong(),
                text = "C".repeat(16330)
            ),
            HttpStatus.SC_CREATED,
            true
        ),
        Arguments.of(
            "with over than max long text",
            TodoDto(
                id = IdGenerator.generateRandomLong(),
                text = "C".repeat(16331)
            ),
            HttpStatus.SC_REQUEST_TOO_LONG,
            false
        ),
        Arguments.of(
            "with completed status",
            TodoDto(
                id = IdGenerator.generateRandomLong(),
                text = "Completed todo item 111",
                completed = true,
            ),
            HttpStatus.SC_CREATED,
            true
        )
    )
}