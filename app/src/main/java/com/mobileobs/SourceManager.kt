
package com.mobileobs

enum class SourceType {
    SCREEN_CAPTURE,
    CAMERA,
    IMAGE,
    TEXT,
    BROWSER,
    CHAT
}

data class OBSSource(
    val id: String,
    var name: String,
    val type: SourceType,
    var enabled: Boolean = true
)

class SourceManager {

    private val sources = mutableListOf<OBSSource>()
    private var nextId = 1

    fun getSources(): List<OBSSource> {
        return sources.toList()
    }

    fun addSource(
        name: String,
        type: SourceType
    ): Boolean {
        if (name.isBlank()) return false

        sources.add(
            OBSSource(
                id = (nextId++).toString(),
                name = name.trim(),
                type = type
            )
        )

        return true
    }

    fun renameSource(
        id: String,
        newName: String
    ): Boolean {
        if (newName.isBlank()) return false

        val source = sources.find {
            it.id == id
        } ?: return false

        source.name = newName.trim()
        return true
    }

    fun toggleSource(id: String): Boolean {
        val source = sources.find {
            it.id == id
        } ?: return false

        source.enabled = !source.enabled
        return true
    }

    fun deleteSource(id: String): Boolean {
        return sources.removeAll {
            it.id == id
        }
    }

    fun clearSources() {
        sources.clear()
    }
}
