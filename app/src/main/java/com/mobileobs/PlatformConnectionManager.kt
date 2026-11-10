
package com.mobileobs

data class PlatformConnection(
    val platform: StreamingPlatform,
    var connected: Boolean = false,
    var accountName: String = ""
)

class PlatformConnectionManager {

    private val connections = mutableListOf(
        PlatformConnection(StreamingPlatform.TIKTOK),
        PlatformConnection(StreamingPlatform.YOUTUBE),
        PlatformConnection(StreamingPlatform.TWITCH),
        PlatformConnection(StreamingPlatform.CUSTOM_RTMP)
    )

    fun getConnections(): List<PlatformConnection> {
        return connections.map { it.copy() }
    }

    fun isConnected(platform: StreamingPlatform): Boolean {
        return connections.find {
            it.platform == platform
        }?.connected ?: false
    }

    fun connect(
        platform: StreamingPlatform,
        accountName: String
    ): Boolean {
        if (accountName.isBlank()) return false

        val connection = connections.find {
            it.platform == platform
        } ?: return false

        connection.connected = true
        connection.accountName = accountName.trim()

        return true
    }

    fun disconnect(platform: StreamingPlatform): Boolean {
        val connection = connections.find {
            it.platform == platform
        } ?: return false

        connection.connected = false
        connection.accountName = ""

        return true
    }

    fun disconnectAll() {
        connections.forEach {
            it.connected = false
            it.accountName = ""
        }
    }
}
