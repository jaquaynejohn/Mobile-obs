
package com.mobileobs

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

data class SceneSource(
    val sceneId: String,
    val sourceId: String,
    var visible: Boolean = true,
    var locked: Boolean = false,
    var order: Int = 0
)

class SceneSourceManager(context: Context) {

    private val preferences = context.applicationContext
        .getSharedPreferences("mobile_obs_scene_sources", Context.MODE_PRIVATE)

    private val sceneSources = mutableListOf<SceneSource>()

    init {
        loadSceneSources()
    }

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

        saveSceneSources()
        return true
    }

    fun setVisibility(
        sceneId: String,
        sourceId: String,
        visible: Boolean
    ): Boolean {
        val item = findItem(sceneId, sourceId) ?: return false

        item.visible = visible
        saveSceneSources()
        return true
    }

    fun setLocked(
        sceneId: String,
        sourceId: String,
        locked: Boolean
    ): Boolean {
        val item = findItem(sceneId, sourceId) ?: return false

        item.locked = locked
        saveSceneSources()
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
            .toMutableList()

        val oldIndex = items.indexOfFirst {
            it.sourceId == sourceId
        }

        if (oldIndex == -1 || newOrder !in items.indices) {
            return false
        }

        val item = items.removeAt(oldIndex)
        items.add(newOrder, item)

        items.forEachIndexed { index, source ->
            source.order = index
        }

        saveSceneSources()
        return true
    }

    fun removeSourceFromScene(
        sceneId: String,
        sourceId: String
    ): Boolean {
        val removed = sceneSources.removeAll {
            it.sceneId == sceneId && it.sourceId == sourceId
        }

        if (removed) saveSceneSources()

        return removed
    }

    fun removeScene(sceneId: String): Boolean {
        val removed = sceneSources.removeAll {
            it.sceneId == sceneId
        }

        if (removed) saveSceneSources()

        return removed
    }

    fun clear() {
        sceneSources.clear()
        saveSceneSources()
    }

    private fun findItem(
        sceneId: String,
        sourceId: String
    ): SceneSource? {
        return sceneSources.find {
            it.sceneId == sceneId && it.sourceId == sourceId
        }
    }

    private fun saveSceneSources() {
        val array = JSONArray()

        sceneSources.forEach { item ->
            array.put(
                JSONObject().apply {
                    put("sceneId", item.sceneId)
                    put("sourceId", item.sourceId)
                    put("visible", item.visible)
                    put("locked", item.locked)
                    put("order", item.order)
                }
            )
        }

        preferences.edit()
            .putString("scene_sources", array.toString())
            .apply()
    }

    private fun loadSceneSources() {
        val savedData = preferences.getString("scene_sources", null) ?: return

        try {
            val array = JSONArray(savedData)

            for (index in 0 until array.length()) {
                val item = array.getJSONObject(index)

                sceneSources.add(
                    SceneSource(
                        sceneId = item.getString("sceneId"),
                        sourceId = item.getString("sourceId"),
                        visible = item.optBoolean("visible", true),
                        locked = item.optBoolean("locked", false),
                        order = item.optInt("order", index)
                    )
                )
            }
        } catch (_: Exception) {
            sceneSources.clear()
        }
    }
}
