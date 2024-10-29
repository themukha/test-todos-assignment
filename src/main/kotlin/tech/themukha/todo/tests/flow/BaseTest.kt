package tech.themukha.todo.tests.flow

import io.restassured.RestAssured.baseURI
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Testcontainers
import tech.themukha.todo.tests.api.WebSocketManager
import tech.themukha.todo.tests.config.TestConfig.BASE_URL
import tech.themukha.todo.tests.config.TestConfig.PORT
import tech.themukha.todo.tests.logging.TestLogger
import java.io.File

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class BaseTest {

    @BeforeEach
    fun beforeEach() {
        try {
            webSocketClient.startConnection()
            Thread.sleep(1000)
            logger.debug("Websocket connection started")
        } catch (e: Exception) {
            logger.error("Error while starting websocket connection: ${e.message}")
        }
    }

    @AfterEach
    fun afterEach() {
        try {
            webSocketClient.closeConnection()
            logger.debug("Websocket connection closed")
        } catch (e: Exception) {
            logger.error("Error while closing websocket connection: ${e.message}")
        }
    }

    companion object {
        private val logger = TestLogger()
        val webSocketClient = WebSocketManager().webSocketClient

        private val composeContainer = DockerComposeContainer(File("docker-compose.yml"))
            .withExposedService("todo-app", 4242)
            .withCopyFilesInContainer("/docker-compose.yml")
            .waitingFor("todo-app", Wait.forHttp("/todos").forStatusCode(200))

        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            try {
                composeContainer.start()
                logger.info("Container with service started")
                baseURI = "$BASE_URL:$PORT"
            } catch (e: Exception) {
                logger.error("Error while starting container: ${e.message}")
                throw e
            }
        }

        @AfterAll
        @JvmStatic
        fun afterAll() {
            try {
                composeContainer.stop()
                logger.info("Container with service stopped")
            } catch (e: Exception) {
                logger.error("Error while stopping container: ${e.message}")
            }
        }

    }
}