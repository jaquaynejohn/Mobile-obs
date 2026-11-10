
package com.mobileobs

data class OBSStreamDestination(
    val id: String,
    var name: String,
    var platform: StreamingPlatform,
    var serverUrl: String,
    var enabled: Boolean = true,
    var isDefault: Boolean = false
)

class StreamDestinationManager {

    private val destinations = mutableListOf<OBSStreamDestination>()
    private var nextId = 1
    private var selectedDestinationId: String? = null

    fun getDestinations(): List<OBSStreamDestination> {
        return destinations.map { it.copy() }
    }

    fun getSelectedDestination(): OBSStreamDestination? {
        return destinations.find {
            it.id == selectedDestinationId
        }?.copy()
    }

    fun addDestination(
        name: String,
        platform: StreamingPlatform,
        serverUrl: String
    ): Boolean {
        if (name.isBlank() || !isValidServerUrl(serverUrl)) {
            return false
        }

        destinations.add(
            OBSStreamDestination(
                id = (nextId++).toString(),
                name = name.trim(),
                platform = platform,
                serverUrl = serverUrl.trim()
            )
        )

        return true
    }

    fun updateDestination(
        id: String,
        name: String,
        platform: StreamingPlatform,
        serverUrl: String
    ): Boolean {
        if (name.isBlank() || !isValidServerUrl(serverUrl)) {
            return false
        }

        val destination = destinations.find {
            it.id == id
        } ?: return false

        destination.name = name.trim()
        destination.platform = platform
        destination.serverUrl = serverUrl.trim()

        return true
    }

    fun selectDestination(id: String): Boolean {
        val destination = destinations.find {
            it.id == id && it.enabled
        } ?: return false

        selectedDestinationId = destination.id
        return true
    }

    fun setEnabled(
        id: String,
        enabled: Boolean
    ): Boolean {
        val destination = destinations.find {
            it.id == id
        } ?: return false

        destination.enabled = enabled

        if (!enabled && selectedDestinationId == id) {
            selectedDestinationId = null
        }

        return true
    }

    fun setDefault(id: String): Boolean {
        val destination = destinations.find {
            it.id == id && it.enabled
        } ?: return false

        destinations.forEach {
            it.isDefault = false
        }

        destination.isDefault = true
        selectedDestinationId = id

        return true
    }

    fun deleteDestination(id: String): Boolean {
        val removed = destinations.removeAll {
            it.id == id
        }

        if (selectedDestinationId == id) {
            selectedDestinationId = null
        }

        return removed
    }

    fun clearDestinations() {
        destinations.clear()
        selectedDestinationId = null
        nextId = 1
    }

    private fun isValidServerUrl(url: String): Boolean {
        return url.startsWith("rtmp://") ||
            url.startsWith("rtmps://")
    }
}
