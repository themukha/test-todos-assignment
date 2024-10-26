package tech.themukha.todo.tests.api

import io.qameta.allure.Step
import io.restassured.RestAssured.baseURI
import org.apache.http.HttpStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertAll
import tech.themukha.todo.tests.api.RestApiHelper.callApi
import tech.themukha.todo.tests.config.TestConfig
import tech.themukha.todo.tests.dto.GetAllTodoDto
import tech.themukha.todo.tests.model.TodoDto
import tech.themukha.todo.tests.utils.DataClassExtensions.toParams

open class TodoApi {
    init {
        baseURI = TestConfig.BASE_URL
    }

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
            queryParams = queryParams,
            expectedResponseCode = expectedResponseCode
        )
    }

    @Step("Add new TODO with ID {newTodo.id}")
    fun `Add new TODO`(
        newTodo: TodoDto,
        expectedResponseCode: Int = HttpStatus.SC_CREATED,
    ): Void? {
        return callApi(
            RestEndpoint.POST_TODO,
            requestBody = newTodo,
            expectedResponseCode = expectedResponseCode,
        )
    }

    @Step("Update existing TODO with ID {putTodo.id}")
    fun `Update existing TODO`(
        putTodo: TodoDto,
        expectedResponseCode: Int = HttpStatus.SC_OK,
    ): Void? {
        return callApi(
            RestEndpoint.PUT_TODO,
            requestBody = putTodo,
            expectedResponseCode = expectedResponseCode,
        )
    }

    @Step("Delete existing TODO with ID {todoId}")
    fun `Delete existing TODO`(
        todoId: ULong,
        expectedResponseCode: Int = HttpStatus.SC_NO_CONTENT,
    ): Void? {
        val pathParams = mapOf("todoId" to todoId)
        return callApi(
            RestEndpoint.DELETE_TODO,
            pathParams = pathParams,
            expectedResponseCode = expectedResponseCode,
        )
    }

    @Step("Check TODO by ID {expectedTodo.id}")
    fun `Check TODO by ID`(
        expectedTodo: TodoDto,
    ) {
        val allPosts = `Get all TODOs`()
        val existingPost = allPosts?.find { it.id == expectedTodo.id }
        assertTrue(existingPost != null, "TODO with id ${expectedTodo.id} not found")
        assertAll(
            { assertEquals(expectedTodo.id, existingPost!!.id) },
            { assertEquals(expectedTodo.text, existingPost!!.text) },
            { assertEquals(expectedTodo.completed, existingPost!!.completed) }
        )
    }
}