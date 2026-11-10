
package com.mobileobs

import java.util.UUID

data class OBSScene(
    val id: String = UUID.randomUUID().toString(),
    var name: String,
    var isActive: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

class SceneManager {

    private val scenes = mutableListOf<OBSScene>()

    fun getScenes(): List<OBSScene> {
        return scenes.map { it.copy() }
    }

    fun createScene(name: String): OBSScene? {
        if (name.isBlank()) return null

        val scene = OBSScene(name = name.trim())
        scenes.add(scene)

        if (scenes.size == 1) {
            scene.isActive = true
        }

        return scene.copy()
    }

    fun renameScene(id: String, newName: String): Boolean {
        if (newName.isBlank()) return false

        val scene = scenes.find { it.id == id } ?: return false
        scene.name = newName.trim()

        return true
    }

    fun duplicateScene(id: String): OBSScene? {
        val original = scenes.find { it.id == id } ?: return null

        val duplicate = OBSScene(
            name = "${original.name} Copy"
        )

        scenes.add(duplicate)
        return duplicate.copy()
    }

    fun switchScene(id: String): Boolean {
        if (scenes.none { it.id == id }) return false

        scenes.forEach {
            it.isActive = it.id == id
        }

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

        return true
    }

    fun getActiveScene(): OBSScene? {
        return scenes.find { it.isActive }?.copy()
    }

    fun clearScenes() {
        scenes.clear()
    }
}
