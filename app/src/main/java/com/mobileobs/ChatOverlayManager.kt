
package com.mobileobs

data class ChatOverlaySettings(
    var visible: Boolean = true,
    var minimized: Boolean = false,
    var showUsernames: Boolean = true,
    var showMessages: Boolean = true,
    var opacity: Float = 1f,
    var width: Float = 350f,
    var height: Float = 450f,
    var x: Float = 0f,
    var y: Float = 0f
)

class ChatOverlayManager {

    private var settings = ChatOverlaySettings()

    fun getSettings(): ChatOverlaySettings {
        return settings.copy()
    }

    fun setVisible(visible: Boolean) {
        settings.visible = visible
    }

    fun toggleMinimized() {
        settings.minimized = !settings.minimized
    }

    fun setShowUsernames(show: Boolean) {
        settings.showUsernames = show
    }

    fun setShowMessages(show: Boolean) {
        settings.showMessages = show
    }

    fun setOpacity(opacity: Float): Boolean {
        if (opacity !in 0f..1f) return false

        settings.opacity = opacity
        return true
    }

    fun setSize(width: Float, height: Float): Boolean {
        if (width <= 0f || height <= 0f) return false

        settings.width = width
        settings.height = height
        return true
    }

    fun setPosition(x: Float, y: Float) {
        settings.x = x
        settings.y = y
    }

    fun updateSettings(
        newSettings: ChatOverlaySettings
    ): Boolean {
        if (
            newSettings.opacity !in 0f..1f ||
            newSettings.width <= 0f ||
            newSettings.height <= 0f
        ) {
            return false
        }

        settings = newSettings.copy()
        return true
    }
}
