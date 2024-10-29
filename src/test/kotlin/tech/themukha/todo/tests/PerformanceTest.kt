package tech.themukha.todo.tests

import io.qameta.allure.Epic
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import tech.themukha.todo.tests.flow.BaseTest
import tech.themukha.todo.tests.flow.TestFlow
import tech.themukha.todo.tests.flow.TestFlow.Companion.logger
import tech.themukha.todo.tests.model.TodoDto
import tech.themukha.todo.tests.utils.PerformanceTestUtil.percentile
import java.util.concurrent.atomic.AtomicLong

@DisplayName("Performance Test")
@Epic("Performance testing")
class PerformanceTest : BaseTest() {
    @Test
    @DisplayName("Test Post Todos Performance")
    fun `Test Post Todos Performance`() {
        val queryQuality = 1000
        val counter = AtomicLong(0)
        val requestTimes = mutableListOf<Long>()

        TestFlow()
            .step("Performance Test") {
                repeat(queryQuality) {
                    val newTodo = TodoDto(
                        id = counter.getAndIncrement(),
                        text = "Performance Test $counter"
                    )
                    val startTime = System.currentTimeMillis()
                    `Add new TODO`(newTodo)
                    val endTime = System.currentTimeMillis()
                    requestTimes.add(endTime - startTime)
                }
            }
            .step("Performance Test Results") {
                requestTimes.sort()
                val totalTime = requestTimes.sum()
                val averageTime = totalTime / queryQuality
                val percentile95 = percentile(requestTimes, 95.0)
                val percentile99 = percentile(requestTimes, 99.0)

                logger.info("Total time: $totalTime ms")
                logger.info("Average time: $averageTime ms")
                logger.info("95th percentile: $percentile95 ms")
                logger.info("99th percentile: $percentile99 ms")
            }
    }
}