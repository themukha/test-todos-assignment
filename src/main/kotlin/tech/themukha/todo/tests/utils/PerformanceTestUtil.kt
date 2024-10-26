package tech.themukha.todo.tests.utils

import kotlin.math.roundToInt

object PerformanceTestUtil {
    fun percentile(sortedList: List<Long>, percentile: Double): Long {
        val index = ((percentile / 100) * sortedList.size).roundToInt() - 1
        return if (index >= 0 && index < sortedList.size) {
            sortedList[index]
        } else {
            throw IndexOutOfBoundsException("Index $index is out of bounds for sorted list $sortedList")
        }
    }
}