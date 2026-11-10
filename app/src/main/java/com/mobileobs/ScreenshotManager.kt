
package com.mobileobs

data class OBSScreenshot(
    val id: String,
    var name: String,
    var filePath: String,
    var createdAt: Long = System.currentTimeMillis()
)

class ScreenshotManager {

    private val screenshots = mutableListOf<OBSScreenshot>()
    private var nextId = 1

    fun getScreenshots(): List<OBSScreenshot> {
        return screenshots.map { it.copy() }
    }

    fun addScreenshot(
        name: String,
        filePath: String
    ): Boolean {
        if (name.isBlank() || filePath.isBlank()) {
            return false
        }

        screenshots.add(
            OBSScreenshot(
                id = (nextId++).toString(),
                name = name.trim(),
                filePath = filePath.trim()
            )
        )

        return true
    }

    fun renameScreenshot(
        id: String,
        newName: String
    ): Boolean {
        if (newName.isBlank()) return false

        val screenshot = screenshots.find {
            it.id == id
        } ?: return false

        screenshot.name = newName.trim()
        return true
    }

    fun deleteScreenshot(id: String): Boolean {
        return screenshots.removeAll {
            it.id == id
        }
    }

    fun getScreenshot(id: String): OBSScreenshot? {
        return screenshots.find {
            it.id == id
        }?.copy()
    }

    fun clearScreenshots() {
        screenshots.clear()
        nextId = 1
    }
}
