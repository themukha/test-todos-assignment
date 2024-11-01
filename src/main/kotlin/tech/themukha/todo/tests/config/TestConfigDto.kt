package tech.themukha.todo.tests.config

data class TestConfigDto(
    val service: ServiceConfig,
)

data class ServiceConfig(
    val baseUrl: String,
    val port: Int,
    val username: String,
    val password: String,
)
