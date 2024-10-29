package tech.themukha.todo.tests.utils

import io.restassured.response.Response

object RestAssuredExtensions {

    inline fun <reified T> Response.extractAs(): T {
        return JsonUtils.fromJson(this.body.asString())
    }

}