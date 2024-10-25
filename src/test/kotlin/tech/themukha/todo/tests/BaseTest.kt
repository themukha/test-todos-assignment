package tech.themukha.todo.tests

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Testcontainers
import tech.themukha.todo.tests.logging.TestLogger
import java.io.File

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class BaseTest {

    companion object {
        private val logger = TestLogger()

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