
package com.mobileobs

data class OBSTextOverlay(
    val id: String,
    var name: String,
    var text: String,
    var fontSize: Float = 24f,
    var colorHex: String = "#FFFFFF",
    var opacity: Float = 1f,
    var visible: Boolean = true,
    var x: Float = 0f,
    var y: Float = 0f
)

class TextOverlayManager {

    private val overlays = mutableListOf<OBSTextOverlay>()
    private var nextId = 1

    fun getOverlays(): List<OBSTextOverlay> {
        return overlays.map { it.copy() }
    }

    fun addOverlay(
        name: String,
        text: String
    ): Boolean {
        if (name.isBlank() || text.isBlank()) return false

        overlays.add(
            OBSTextOverlay(
                id = (nextId++).toString(),
                name = name.trim(),
                text = text.trim()
            )
        )

        return true
    }

    fun updateText(
        id: String,
        newText: String
    ): Boolean {
        if (newText.isBlank()) return false

        val overlay = overlays.find {
            it.id == id
        } ?: return false

        overlay.text = newText
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

    fun setFontSize(
        id: String,
        size: Float
    ): Boolean {
        if (size !in 8f..120f) return false

        val overlay = overlays.find {
            it.id == id
        } ?: return false

        overlay.fontSize = size
        return true
    }

    fun setColor(
        id: String,
        colorHex: String
    ): Boolean {
        if (!colorHex.matches(Regex("#[0-9A-Fa-f]{6}"))) {
            return false
        }

        val overlay = overlays.find {
            it.id == id
        } ?: return false

        overlay.colorHex = colorHex
        return true
    }

    fun setOpacity(
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

    fun toggleVisibility(id: String): Boolean {
        val overlay = overlays.find {
            it.id == id
        } ?: return false

        overlay.visible = !overlay.visible
        return true
    }

    fun setPosition(
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

    fun deleteOverlay(id: String): Boolean {
        return overlays.removeAll {
            it.id == id
        }
    }
}
