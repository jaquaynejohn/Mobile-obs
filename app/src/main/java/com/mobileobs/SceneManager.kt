
package com.mobileobs

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID

data class OBSScene(
    val id: String = UUID.randomUUID().toString(),
    var name: String,
    var isActive: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

class SceneManager(context: Context) {

    private val preferences = context.applicationContext
        .getSharedPreferences("mobile_obs_scenes", Context.MODE_PRIVATE)

    private val scenes = mutableListOf<OBSScene>()

    init {
        loadScenes()

        if (scenes.isEmpty()) {
            createScene("Starting Soon")
            createScene("Gameplay")
            createScene("Be Right Back")
        }
    }

    fun getScenes(): List<OBSScene> {
        return scenes.map { it.copy() }
    }

    fun createScene(name: String): OBSScene? {
        if (name.isBlank()) return null

        val scene = OBSScene(
            name = name.trim(),
            isActive = scenes.isEmpty()
        )

        scenes.add(scene)
        saveScenes()

        return scene.copy()
    }

    fun renameScene(id: String, newName: String): Boolean {
        if (newName.isBlank()) return false

        val scene = scenes.find { it.id == id } ?: return false
        scene.name = newName.trim()

        saveScenes()
        return true
    }

    fun duplicateScene(id: String): OBSScene? {
        val original = scenes.find { it.id == id } ?: return null

        val duplicate = OBSScene(
            name = "${original.name} Copy"
        )

        scenes.add(duplicate)
        saveScenes()

        return duplicate.copy()
    }

    fun switchScene(id: String): Boolean {
        if (scenes.none { it.id == id }) return false

        scenes.forEach {
            it.isActive = it.id == id
        }

        saveScenes()
        return true
    }

    fun deleteScene(id: String): Boolean {
        val index = scenes.indexOfFirst { it.id == id }
        if (index == -1) return false

        val wasActive = scenes[index].isActive
        scenes.removeAt(index)

        if (wasActive && scenes.isNotEmpty()) {
            scenes.first().isActive = true
        }

        saveScenes()
        return true
    }

    fun getActiveScene(): OBSScene? {
        return scenes.find { it.isActive }?.copy()
    }

    fun clearScenes() {
        scenes.clear()
        saveScenes()
    }

    private fun saveScenes() {
        val array = JSONArray()

        scenes.forEach { scene ->
            val item = JSONObject().apply {
                put("id", scene.id)
                put("name", scene.name)
                put("isActive", scene.isActive)
                put("createdAt", scene.createdAt)
            }

            array.put(item)
        }

        preferences.edit()
            .putString("scenes", array.toString())
            .apply()
    }

    private fun loadScenes() {
        val savedData = preferences.getString("scenes", null) ?: return

        try {
            val array = JSONArray(savedData)

            for (index in 0 until array.length()) {
                val item = array.getJSONObject(index)

                scenes.add(
                    OBSScene(
                        id = item.getString("id"),
                        name = item.getString("name"),
                        isActive = item.getBoolean("isActive"),
                        createdAt = item.getLong("createdAt")
                    )
                )
            }

            if (scenes.isNotEmpty() && scenes.none { it.isActive }) {
                scenes.first().isActive = true
                saveScenes()
            }
        } catch (_: Exception) {
            scenes.clear()
        }
    }
}
