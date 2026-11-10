
package com.mobileobs

data class OBSOverlay(
    val id: String,
    var name: String,
    var type: OverlayType,
    var visible: Boolean = true,
    var minimized: Boolean = false,
    var x: Float = 0f,
    var y: Float = 0f,
    var width: Float = 300f,
    var height: Float = 200f,
    var opacity: Float = 1f
)

enum class OverlayType {
    CHAT,
    TEXT,
    IMAGE,
    BROWSER
}

class OverlayManager {

    private val overlays = mutableListOf<OBSOverlay>()
    private var nextId = 1

    fun getOverlays(): List<OBSOverlay> {
        return overlays.toList()
    }

    fun addOverlay(
        name: String,
        type: OverlayType
    ): Boolean {
        if (name.isBlank()) return false

        overlays.add(
            OBSOverlay(
                id = (nextId++).toString(),
                name = name.trim(),
                type = type
            )
        )

        return true
    }

    fun renameOverlay(
        id: String,
        newName: String
    ): Boolean {
        if (newName.isBlank()) return false

        val overlay = overlays.find {
            it.id == id
        } ?: return false

        overlay.name = newName.trim()
        return true
    }

    fun toggleVisibility(id: String): Boolean {
        val overlay = overlays.find {
            it.id == id
        } ?: return false

        overlay.visible = !overlay.visible
        return true
    }

    fun toggleMinimized(id: String): Boolean {
        val overlay = overlays.find {
            it.id == id
        } ?: return false

        overlay.minimized = !overlay.minimized
        return true
    }

    fun updatePosition(
        id: String,
        x: Float,
        y: Float
    ): Boolean {
        val overlay = overlays.find {
            it.id == id
        } ?: return false

        overlay.x = x
        overlay.y = y
        return true
    }

    fun updateSize(
        id: String,
        width: Float,
        height: Float
    ): Boolean {
        if (width <= 0f || height <= 0f) return false

        val overlay = overlays.find {
            it.id == id
        } ?: return false

        overlay.width = width
        overlay.height = height
        return true
    }

    fun updateOpacity(
        id: String,
        opacity: Float
    ): Boolean {
        if (opacity !in 0f..1f) return false

        val overlay = overlays.find {
            it.id == id
        } ?: return false

        overlay.opacity = opacity
        return true
    }

    fun deleteOverlay(id: String): Boolean {
        return overlays.removeAll {
            it.id == id
        }
    }
}
