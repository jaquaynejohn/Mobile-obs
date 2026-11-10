
package com.mobileobs

data class BrowserOverlay(
    val id: String,
    var name: String,
    var url: String,
    var visible: Boolean = true,
    var width: Float = 400f,
    var height: Float = 300f,
    var opacity: Float = 1f
)

class BrowserOverlayManager {

    private val overlays = mutableListOf<BrowserOverlay>()
    private var nextId = 1

    fun getOverlays(): List<BrowserOverlay> {
        return overlays.map { it.copy() }
    }

    fun addOverlay(
        name: String,
        url: String
    ): Boolean {
        if (name.isBlank() || !isValidUrl(url)) return false

        overlays.add(
            BrowserOverlay(
                id = (nextId++).toString(),
                name = name.trim(),
                url = url.trim()
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

    fun updateUrl(
        id: String,
        newUrl: String
    ): Boolean {
        if (!isValidUrl(newUrl)) return false

        val overlay = overlays.find {
            it.id == id
        } ?: return false

        overlay.url = newUrl.trim()
        return true
    }

    fun setVisibility(
        id: String,
        visible: Boolean
    ): Boolean {
        val overlay = overlays.find {
            it.id == id
        } ?: return false

        overlay.visible = visible
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

    private fun isValidUrl(url: String): Boolean {
        return url.startsWith("https://") ||
            url.startsWith("http://")
    }
}
