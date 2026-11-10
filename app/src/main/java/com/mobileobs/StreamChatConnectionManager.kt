
package com.mobileobs

data class StreamChatConnection(
    val platform: ChatPlatform,
    var connected: Boolean = false,
    var connecting: Boolean = false,
    var errorMessage: String = ""
)

class StreamChatConnectionManager {

    private val connections = mutableListOf(
        StreamChatConnection(ChatPlatform.TIKTOK),
        StreamChatConnection(ChatPlatform.YOUTUBE),
        StreamChatConnection(ChatPlatform.TWITCH)
    )

    fun getConnections(): List<StreamChatConnection> {
        return connections.map { it.copy() }
    }

    fun connect(platform: ChatPlatform): Boolean {
        val connection = connections.find {
            it.platform == platform
        } ?: return false

        if (connection.connected || connection.connecting) {
            return false
        }

        connection.connecting = true
        connection.errorMessage = ""

        return true
    }

    fun markConnected(platform: ChatPlatform): Boolean {
        val connection = connections.find {
            it.platform == platform
        } ?: return false

        if (!connection.connecting) return false

        connection.connecting = false
        connection.connected = true

        return true
    }

    fun markFailed(
        platform: ChatPlatform,
        message: String
    ): Boolean {
        val connection = connections.find {
            it.platform == platform
        } ?: return false

        connection.connecting = false
        connection.connected = false
        connection.errorMessage = message

        return true
    }

    fun disconnect(platform: ChatPlatform): Boolean {
        val connection = connections.find {
            it.platform == platform
        } ?: return false

        connection.connected = false
        connection.connecting = false
        connection.errorMessage = ""

        return true
    }

    fun isConnected(platform: ChatPlatform): Boolean {
        return connections.find {
            it.platform == platform
        }?.connected ?: false
    }

    fun disconnectAll() {
        connections.forEach {
            it.connected = false
            it.connecting = false
            it.errorMessage = ""
        }
    }
}
