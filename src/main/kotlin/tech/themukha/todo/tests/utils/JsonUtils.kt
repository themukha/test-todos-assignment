package tech.themukha.todo.tests.utils

import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

object JsonUtils {
    val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    inline fun <reified T> fromJson(jsonString: String): T = json.decodeFromString(jsonString)

    inline fun <reified T> toJson(obj: T): String = json.encodeToString(serializer(), obj)

}