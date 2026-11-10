
package com.mobileobs

enum class ChatPlatform {
    TIKTOK,
    YOUTUBE,
    TWITCH
}

data class ChatMessage(
    val id: String,
    val username: String,
    val message: String,
    val platform: ChatPlatform
)

data class ChatSettings(
    var visible: Boolean = true,
    var minimized: Boolean = false,
    var opacity: Float = 1f,
    var showUsernames: Boolean = true,
    var showMessages: Boolean = true
)

class ChatManager {

    private val messages = mutableListOf<ChatMessage>()

    private var settings = ChatSettings()

    private var nextId = 1

    fun getMessages(): List<ChatMessage> {
        return messages.toList()
    }

    fun addMessage(
        username: String,
        message: String,
        platform: ChatPlatform
    ): Boolean {
        if (username.isBlank() || message.isBlank()) {
            return false
        }

        messages.add(
            ChatMessage(
                id = (nextId++).toString(),
                username = username.trim(),
                message = message.trim(),
                platform = platform
            )
        )

        return true
    }

    fun clearMessages() {
        messages.clear()
    }

    fun getSettings(): ChatSettings {
        return settings.copy()
    }

    fun setVisible(visible: Boolean) {
        settings.visible = visible
    }

    fun toggleMinimized() {
        settings.minimized = !settings.minimized
    }

    fun setOpacity(opacity: Float): Boolean {
        if (opacity !in 0f..1f) return false

        settings.opacity = opacity
        return true
    }

    fun setShowUsernames(show: Boolean) {
        settings.showUsernames = show
    }

    fun setShowMessages(show: Boolean) {
        settings.showMessages = show
    }
}
