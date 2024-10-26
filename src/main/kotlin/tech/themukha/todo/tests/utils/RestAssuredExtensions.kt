package tech.themukha.todo.tests.utils

import io.restassured.response.Response
import io.restassured.response.ValidatableResponse

object RestAssuredExtensions {

    inline fun <reified T> Response.extractAs(): T {
        return JsonUtils.fromJson(this.body.asString())
    }

    inline fun <reified T> ValidatableResponse.extractAs(): T {
        return JsonUtils.fromJson(this.extract().body().asString())
    }
}