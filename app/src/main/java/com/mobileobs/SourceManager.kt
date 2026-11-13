
package com.mobileobs

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID

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

class SourceManager(context: Context) {

    private val preferences = context.applicationContext
        .getSharedPreferences("mobile_obs_sources", Context.MODE_PRIVATE)

    private val sources = mutableListOf<OBSSource>()

    init {
        loadSources()
    }

    fun getSources(): List<OBSSource> {
        return sources.map { it.copy() }
    }

    fun addSource(
        name: String,
        type: SourceType
    ): Boolean {
        if (name.isBlank()) return false

        sources.add(
            OBSSource(
                id = UUID.randomUUID().toString(),
                name = name.trim(),
                type = type
            )
        )

        saveSources()
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

        saveSources()
        return true
    }

    fun toggleSource(id: String): Boolean {
        val source = sources.find {
            it.id == id
        } ?: return false

        source.enabled = !source.enabled

        saveSources()
        return true
    }

    fun deleteSource(id: String): Boolean {
        val removed = sources.removeAll {
            it.id == id
        }

        if (removed) saveSources()

        return removed
    }

    fun clearSources() {
        sources.clear()
        saveSources()
    }

    private fun saveSources() {
        val array = JSONArray()

        sources.forEach { source ->
            array.put(
                JSONObject().apply {
                    put("id", source.id)
                    put("name", source.name)
                    put("type", source.type.name)
                    put("enabled", source.enabled)
                }
            )
        }

        preferences.edit()
            .putString("sources", array.toString())
            .apply()
    }

    private fun loadSources() {
        val savedData = preferences.getString("sources", null) ?: return

        try {
            val array = JSONArray(savedData)

            for (index in 0 until array.length()) {
                val item = array.getJSONObject(index)

                val type = try {
                    SourceType.valueOf(item.getString("type"))
                } catch (_: Exception) {
                    continue
                }

                sources.add(
                    OBSSource(
                        id = item.getString("id"),
                        name = item.getString("name"),
                        type = type,
                        enabled = item.optBoolean("enabled", true)
                    )
                )
            }
        } catch (_: Exception) {
            sources.clear()
        }
    }
}
