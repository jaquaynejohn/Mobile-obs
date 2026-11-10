
package com.mobileobs

data class SceneSource(
    val sceneId: String,
    val sourceId: String,
    var visible: Boolean = true,
    var locked: Boolean = false,
    var order: Int = 0
)

class SceneSourceManager {

    private val sceneSources = mutableListOf<SceneSource>()

    fun getSourcesForScene(sceneId: String): List<SceneSource> {
        return sceneSources
            .filter { it.sceneId == sceneId }
            .sortedBy { it.order }
            .map { it.copy() }
    }

    fun addSourceToScene(
        sceneId: String,
        sourceId: String
    ): Boolean {
        if (sceneId.isBlank() || sourceId.isBlank()) return false

        if (sceneSources.any {
                it.sceneId == sceneId && it.sourceId == sourceId
            }) {
            return false
        }

        val nextOrder = sceneSources
            .filter { it.sceneId == sceneId }
            .maxOfOrNull { it.order + 1 } ?: 0

        sceneSources.add(
            SceneSource(
                sceneId = sceneId,
                sourceId = sourceId,
                order = nextOrder
            )
        )

        return true
    }

    fun setVisibility(
        sceneId: String,
        sourceId: String,
        visible: Boolean
    ): Boolean {
        val item = findItem(sceneId, sourceId) ?: return false
        item.visible = visible
        return true
    }

    fun setLocked(
        sceneId: String,
        sourceId: String,
        locked: Boolean
    ): Boolean {
        val item = findItem(sceneId, sourceId) ?: return false
        item.locked = locked
        return true
    }

    fun moveSource(
        sceneId: String,
        sourceId: String,
        newOrder: Int
    ): Boolean {
        val items = sceneSources
            .filter { it.sceneId == sceneId }
            .sortedBy { it.order }

        val item = items.find { it.sourceId == sourceId }
            ?: return false

        if (newOrder !in items.indices) return false

        val reordered = items.toMutableList()
        reordered.remove(item)
        reordered.add(newOrder, item)

        reordered.forEachIndexed { index, source ->
            source.order = index
        }

        return true
    }

    fun removeSourceFromScene(
        sceneId: String,
        sourceId: String
    ): Boolean {
        return sceneSources.removeAll {
            it.sceneId == sceneId && it.sourceId == sourceId
        }
    }

    fun removeScene(sceneId: String): Boolean {
        return sceneSources.removeAll {
            it.sceneId == sceneId
        }
    }

    fun clear() {
        sceneSources.clear()
    }

    private fun findItem(
        sceneId: String,
        sourceId: String
    ): SceneSource? {
        return sceneSources.find {
            it.sceneId == sceneId && it.sourceId == sourceId
        }
    }
}
