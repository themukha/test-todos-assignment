package tech.themukha.todo.tests.api

import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import io.restassured.http.Method
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification
import org.apache.http.HttpStatus
import tech.themukha.todo.tests.config.TestConfig
import tech.themukha.todo.tests.exceptions.ApiException
import tech.themukha.todo.tests.utils.JsonUtils.toJson
import tech.themukha.todo.tests.utils.RestAssuredExtensions.extractAs

object RestApiHelper {

    inline fun <reified T, reified R : Any> callApi(
        endpoint: RestEndpoint,
        requestBody: R? = null,
        pathParams: Map<String, Any>? = null,
        queryParams: Map<String, Any>? = null,
        expectedResponseCode: Int = HttpStatus.SC_OK,
        isAuthorized: Boolean = true
    ): T? {
        val response = executeRequest(
            endpoint,
            requestBody,
            pathParams = pathParams,
            queryParams = queryParams,
            expectedResponseCode,
            isAuthorized = isAuthorized
        )

        if (response.statusCode != expectedResponseCode) throw ApiException(expectedResponseCode, response.statusCode, response.body.asString())

        return if (response.statusCode in 200..299) {
            val responseBody = response.body.asString()
            if (responseBody.isEmpty()) {
                null
            } else {
                response.extractAs<T>()
            }
        } else {
            null
        }
    }

    @PublishedApi
    internal inline fun <reified R> executeRequest(
        endpoint: RestEndpoint,
        requestBody: R? = null,
        pathParams: Map<String, Any?>? = null,
        queryParams: Map<String, Any>? = null,
        expectedResponseCode: Int = HttpStatus.SC_OK,
        isAuthorized: Boolean = true,
    ): Response {

        val formattedPath = endpoint.setPathParams(pathParams)
        val request: RequestSpecification = given()

        requestBody?.let {
            request.contentType(ContentType.JSON).body(toJson(requestBody))
        }

        queryParams?.let {
            request.queryParams(queryParams)
        }

        if (endpoint.requiredAuth && isAuthorized) {
            val user = TestConfig.USERNAME
            val password = TestConfig.PASSWORD
            request.auth().preemptive().basic(user, password)
        }

        return when (endpoint.method) {
            Method.GET -> request.get(formattedPath)
            Method.POST -> request.post(formattedPath)
            Method.PUT -> request.put(formattedPath)
            Method.PATCH -> request.patch(formattedPath)
            Method.DELETE -> request.delete(formattedPath)
            else -> throw IllegalArgumentException("Unsupported method")
        }.then().statusCode(expectedResponseCode).extract().response()
    }
}