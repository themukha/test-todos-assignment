package tech.themukha.todo.tests.api

import io.restassured.http.Method


enum class RestEndpoint(val method: Method, val path: String, val requiredAuth: Boolean = false) {
    GET_ALL_TODOS(Method.GET, "/todos"),
    POST_TODO(Method.POST, "/todos"),
    PUT_TODO(Method.PUT, "/todos/{todoId}"),
    DELETE_TODO(Method.DELETE, "/todos/{todoId}", true),
    // TODO: check if need to move to another class when WS is implemented
    WEBSOCKET_UPDATES_TODO(Method.GET, "/ws");

    fun setPathParams(pathParams: Map<String, Any?>? = emptyMap()): String {
        var resultPath = path
        if (!pathParams.isNullOrEmpty()) {
            pathParams.forEach { (key, value) ->
                resultPath = resultPath.replace("{$key}", value.toString())
            }
        }
        return resultPath
    }
}