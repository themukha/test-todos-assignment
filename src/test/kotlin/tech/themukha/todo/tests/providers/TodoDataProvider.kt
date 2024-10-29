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
                text = "C".repeat(100000)
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

    @JvmStatic
    fun getTodoProvider(): Stream<Arguments> = Stream.of(
        Arguments.of(null, null, 15, HttpStatus.SC_OK, 15),
        Arguments.of(0, 5, 5, HttpStatus.SC_OK, 15),
        Arguments.of(5, 10, 5, HttpStatus.SC_OK, 15),
        Arguments.of(2, 0, 0, HttpStatus.SC_OK, 15),
        Arguments.of(1000, null, 15, HttpStatus.SC_OK, 15),
        Arguments.of(-1, null, null, HttpStatus.SC_BAD_REQUEST, 0),
        Arguments.of(null, -1, null, HttpStatus.SC_BAD_REQUEST, 0),
    )

    @JvmStatic
    fun putTodoProvider(): Stream<Arguments> = Stream.of(
        Arguments.of(
            "Successful update",
            TodoDto(id = IdGenerator.generateRandomLong(), text = "Initial text", completed = false),
            TodoDto(id = IdGenerator.generateRandomLong(), text = "Updated text", completed = true),
            HttpStatus.SC_OK,
            true
        ),
        Arguments.of(
            "Update non-existent TODO",
            TodoDto(id = IdGenerator.generateRandomLong(), text = "Initial text", completed = false),
            TodoDto(id = IdGenerator.generateRandomLong(), text = "Updated text", completed = true),
            HttpStatus.SC_NOT_FOUND,
            false
        ),
        Arguments.of(
            "Update with empty text",
            TodoDto(id = IdGenerator.generateRandomLong(), text = "Initial text", completed = false),
            TodoDto(id = IdGenerator.generateRandomLong(), text = "", completed = false),
            HttpStatus.SC_OK,
            true
        ),
        Arguments.of(
            "Update completed status",
            TodoDto(id = IdGenerator.generateRandomLong(), text = "Initial text", completed = false),
            TodoDto(id = IdGenerator.generateRandomLong(), text = "Initial text", completed = true),
            HttpStatus.SC_OK,
            true
        )
    )

    @JvmStatic
    fun deleteTodoArguments(): Stream<Arguments> = Stream.of(
        Arguments.of(
            "Successful deletion",
            TodoDto(id = IdGenerator.generateRandomLong(), text = "Test TODO", completed = false),
            HttpStatus.SC_NO_CONTENT,
            false,
            true
        ),
        Arguments.of(
            "Deleting a non-existent TODO",
            TodoDto(id = IdGenerator.generateRandomLong(), text = "Test TODO", completed = false),
            HttpStatus.SC_NO_CONTENT,
            false,
            true
        ),
        Arguments.of(
            "Unauthorized deletion",
            TodoDto(id = IdGenerator.generateRandomLong(), text = "Test TODO", completed = false),
            HttpStatus.SC_UNAUTHORIZED,
            true,
            false
        )
    )
}