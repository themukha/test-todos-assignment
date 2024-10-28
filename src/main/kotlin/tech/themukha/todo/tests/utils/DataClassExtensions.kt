package tech.themukha.todo.tests.utils

import tech.themukha.todo.tests.model.TodoDto
import kotlin.reflect.full.memberProperties

object DataClassExtensions {
    inline fun <reified T : Any> T.toParams(): Map<String, Any>? {
        return T::class.memberProperties
            .filter { property -> property.get(this) != null }
            .associate { property -> property.name to property.get(this) as Any }
            .takeIf { it.isNotEmpty() }
    }

    fun List<TodoDto>.trimWithOffsetAndLimit(offset: Int?, limit: Int?): List<TodoDto> {
        if (this.isEmpty()) return this

        if (offset != null && limit != null && offset >= 0 && limit >= 0) {
            val fromIndex = offset.coerceAtMost(this.size)
            val toIndex = (offset + limit.coerceAtMost(this.size)).coerceAtMost(this.size)
            if (fromIndex <= toIndex) {
                return this.subList(fromIndex, toIndex)
            } else {
                return emptyList()
            }
        } else if (offset != null && offset >= 0) {
            return this.subList(offset.coerceAtMost(this.size), this.size)
        } else if (limit != null && limit >= 0) {
            return this.subList(0, limit.coerceAtMost(this.size))
        }
        return this
    }

}