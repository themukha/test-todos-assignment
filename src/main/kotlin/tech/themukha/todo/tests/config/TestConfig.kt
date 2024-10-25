package tech.themukha.todo.tests.config

object TestConfig {
    private var config: TestConfigDto = YamlConfigLoader.loadConfig("src/test/resources/configs/test.yaml")

    val BASE_URL = config.service.baseUrl
    val USERNAME = config.service.username
    val PASSWORD = config.service.password
}