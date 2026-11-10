
package com.mobileobs

data class StreamDestination(
    val platform: StreamingPlatform,
    var serverUrl: String = "",
    var configured: Boolean = false
)

class StreamKeyManager {

    private val destinations = mutableListOf(
        StreamDestination(StreamingPlatform.TIKTOK),
        StreamDestination(StreamingPlatform.YOUTUBE),
        StreamDestination(StreamingPlatform.TWITCH),
        StreamDestination(StreamingPlatform.CUSTOM_RTMP)
    )

    fun getDestinations(): List<StreamDestination> {
        return destinations.map { it.copy() }
    }

    fun configureDestination(
        platform: StreamingPlatform,
        serverUrl: String
    ): Boolean {
        if (
            serverUrl.isBlank() ||
            !serverUrl.startsWith("rtmps://") &&
            !serverUrl.startsWith("rtmp://")
        ) {
            return false
        }

        val destination = destinations.find {
            it.platform == platform
        } ?: return false

        destination.serverUrl = serverUrl.trim()
        destination.configured = true

        return true
    }

    fun isConfigured(
        platform: StreamingPlatform
    ): Boolean {
        return destinations.find {
            it.platform == platform
        }?.configured ?: false
    }

    fun removeConfiguration(
        platform: StreamingPlatform
    ): Boolean {
        val destination = destinations.find {
            it.platform == platform
        } ?: return false

        destination.serverUrl = ""
        destination.configured = false

        return true
    }
}
