
package com.mobileobs

enum class CameraOverlayShape {
    RECTANGLE,
    ROUNDED,
    CIRCLE
}

data class CameraOverlaySettings(
    var visible: Boolean = true,
    var x: Float = 0f,
    var y: Float = 0f,
    var width: Float = 320f,
    var height: Float = 240f,
    var opacity: Float = 1f,
    var shape: CameraOverlayShape = CameraOverlayShape.ROUNDED,
    var borderEnabled: Boolean = true,
    var borderColorHex: String = "#7657FF"
)

class CameraOverlayManager {

    private var settings = CameraOverlaySettings()

    fun getSettings(): CameraOverlaySettings {
        return settings.copy()
    }

    fun setVisible(visible: Boolean) {
        settings.visible = visible
    }

    fun setPosition(x: Float, y: Float) {
        settings.x = x
        settings.y = y
    }

    fun setSize(width: Float, height: Float): Boolean {
        if (width <= 0f || height <= 0f) return false

        settings.width = width
        settings.height = height
        return true
    }

    fun setOpacity(opacity: Float): Boolean {
        if (opacity !in 0f..1f) return false

        settings.opacity = opacity
        return true
    }

    fun setShape(shape: CameraOverlayShape) {
        settings.shape = shape
    }

    fun setBorderEnabled(enabled: Boolean) {
        settings.borderEnabled = enabled
    }

    fun setBorderColor(colorHex: String): Boolean {
        if (!colorHex.matches(Regex("#[0-9A-Fa-f]{6}"))) {
            return false
        }

        settings.borderColorHex = colorHex
        return true
    }

    fun updateSettings(
        newSettings: CameraOverlaySettings
    ): Boolean {
        if (
            newSettings.width <= 0f ||
            newSettings.height <= 0f ||
            newSettings.opacity !in 0f..1f ||
            !newSettings.borderColorHex.matches(
                Regex("#[0-9A-Fa-f]{6}")
            )
        ) {
            return false
        }

        settings = newSettings.copy()
        return true
    }
}
