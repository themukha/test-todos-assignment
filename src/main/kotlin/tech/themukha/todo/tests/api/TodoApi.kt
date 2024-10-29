package tech.themukha.todo.tests.api

import io.qameta.allure.Step
import org.apache.http.HttpStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.assertAll
import tech.themukha.todo.tests.api.RestApiHelper.callApi
import tech.themukha.todo.tests.dto.GetAllTodoDto
import tech.themukha.todo.tests.model.TodoDto
import tech.themukha.todo.tests.utils.DataClassExtensions.toParams

open class TodoApi {

    @Step("Get all TODOs with offset {offset} and limit {limit}")
    fun `Get all TODOs`(
        expectedResponseCode: Int = HttpStatus.SC_OK,
        offset: Int? = null,
        limit: Int? = null,
    ): List<TodoDto>? {
        val queryParams = GetAllTodoDto(
            offset = offset,
            limit = limit,
        ).toParams()
        return callApi(
            RestEndpoint.GET_ALL_TODOS,
            requestBody = null,
            queryParams = queryParams,
            expectedResponseCode = expectedResponseCode
        )
    }

    @Step("Add new TODO with ID")
    fun `Add new TODO`(
        newTodo: TodoDto,
        expectedResponseCode: Int = HttpStatus.SC_CREATED,
    ) {
        callApi<TodoDto, TodoDto>(
            RestEndpoint.POST_TODO,
            requestBody = newTodo,
            expectedResponseCode = expectedResponseCode,
        )
    }

    @Step("Update existing TODO with ID")
    fun `Update existing TODO`(
        todoId: Long,
        updatedTodo: TodoDto,
        expectedResponseCode: Int = HttpStatus.SC_OK,
    ) {
        callApi<TodoDto, TodoDto>(
            RestEndpoint.PUT_TODO,
            requestBody = updatedTodo,
            pathParams = mapOf("todoId" to todoId),
            expectedResponseCode = expectedResponseCode,
        )
    }

    @Step("Delete existing TODO with ID")
    fun `Delete existing TODO`(
        todoId: Long,
        expectedResponseCode: Int = HttpStatus.SC_NO_CONTENT,
        isAuthorized: Boolean = true,
    ) {
        val pathParams = mapOf("todoId" to todoId)
        callApi<TodoDto, TodoDto>(
            RestEndpoint.DELETE_TODO,
            requestBody = null,
            pathParams = pathParams,
            expectedResponseCode = expectedResponseCode,
            isAuthorized = isAuthorized
        )
    }

    /**
     * @param isExists if [false], then will check if [expectedTodo] is not found
     * */
    @Step("Check TODO by ID")
    fun `Check TODO by ID`(
        expectedTodo: TodoDto,
        isExists: Boolean = true,
    ) {
        val allPosts = `Get all TODOs`()
        val existingPost = allPosts?.find { it.id == expectedTodo.id }
        if (!isExists) {
            assertNull(existingPost, "TODO with id ${expectedTodo.id} found")
            return
        }
        assertNotNull(existingPost, "TODO with id ${expectedTodo.id} not found")
        assertAll(
            { assertEquals(expectedTodo.id, existingPost!!.id) },
            { assertEquals(expectedTodo.text, existingPost!!.text) },
            { assertEquals(expectedTodo.completed, existingPost!!.completed) }
        )
    }

    /**
     * @param isExists if [false], then will check if [expectedTodos] are not found
     * */
    @Step("Check TODO by ID")
    fun `Check TODOs by ID`(
        expectedTodos: List<TodoDto>,
        offset: Int? = null,
        limit: Int? = null,
        isExists: Boolean = true,
    ) {
        val allPosts = `Get all TODOs`(offset = offset, limit = limit)
        if (!isExists) {
            expectedTodos.forEach { expectedTodo ->
                val existingPost = allPosts?.find { it.id == expectedTodo.id }
                assertNull(existingPost, "TODO with id ${expectedTodo.id} found")
            }
            return
        }

        expectedTodos.forEach { expectedTodo ->
            val existingPost = allPosts?.find { it.id == expectedTodo.id }
            assertNotNull(existingPost, "TODO with id ${expectedTodo.id} not found")
            assertAll(
                { assertEquals(expectedTodo.id, existingPost!!.id) },
                { assertEquals(expectedTodo.text, existingPost!!.text) },
                { assertEquals(expectedTodo.completed, existingPost!!.completed) }
            )
        }
    }
}