package tech.themukha.todo.tests.flow

import io.qameta.allure.Step
import tech.themukha.todo.tests.api.TodoApi
import tech.themukha.todo.tests.api.WebSocketManager
import tech.themukha.todo.tests.logging.TestLogger

class TestFlow(webSocketClient: WebSocketManager = BaseTest.webSocketClient) : TodoApi(webSocketClient) {

    @Step("{stepName}")
    fun step(stepName: String, action: TestFlow.() -> Unit): TestFlow {
        logger.trace("Starting step `$stepName`")
        try {
            this.action()
            logger.trace("Step `$stepName` completed successfully")
        } catch (e: Throwable) {
            logger.error("Step `$stepName` failed: ${e.message}")
            throw e
        }
        return this
    }

    companion object {
        val logger = TestLogger()
    }
}