package tech.themukha.todo.tests.utils

import kotlin.random.Random
import kotlin.random.nextULong

object IdGenerator {

    fun generateRandomULong(): ULong = Random.nextULong()

    fun generateRandomULongInRange(min: ULong, max: ULong): ULong = Random.nextULong(min, max)
}