
package com.mobileobs

enum class OBSFilterType {
    BRIGHTNESS,
    CONTRAST,
    SATURATION,
    BLUR,
    CHROMA_KEY,
    VOLUME,
    NOISE_SUPPRESSION
}

data class OBSFilter(
    val id: String,
    var name: String,
    val type: OBSFilterType,
    var enabled: Boolean = true,
    var intensity: Float = 1f
)

class SourceFilterManager {

    private val filters = mutableListOf<OBSFilter>()
    private var nextId = 1

    fun getFilters(): List<OBSFilter> {
        return filters.map { it.copy() }
    }

    fun addFilter(
        name: String,
        type: OBSFilterType
    ): Boolean {
        if (name.isBlank()) return false

        filters.add(
            OBSFilter(
                id = (nextId++).toString(),
                name = name.trim(),
                type = type
            )
        )

        return true
    }

    fun toggleFilter(id: String): Boolean {
        val filter = filters.find {
            it.id == id
        } ?: return false

        filter.enabled = !filter.enabled
        return true
    }

    fun setIntensity(
        id: String,
        intensity: Float
    ): Boolean {
        if (intensity !in 0f..2f) return false

        val filter = filters.find {
            it.id == id
        } ?: return false

        filter.intensity = intensity
        return true
    }

    fun renameFilter(
        id: String,
        newName: String
    ): Boolean {
        if (newName.isBlank()) return false

        val filter = filters.find {
            it.id == id
        } ?: return false

        filter.name = newName.trim()
        return true
    }

    fun deleteFilter(id: String): Boolean {
        return filters.removeAll {
            it.id == id
        }
    }

    fun clearFilters() {
        filters.clear()
    }
}
