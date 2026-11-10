
package com.mobileobs

enum class StreamEventType {
    FOLLOWER,
    SUBSCRIBER,
    MEMBER,
    DONATION,
    STREAM_STARTED,
    STREAM_ENDED,
    VIEWER_JOINED,
    CUSTOM
}

data class OBSStreamEvent(
    val id: String,
    val type: StreamEventType,
    val username: String,
    val message: String,
    val platform: StreamingPlatform,
    val createdAt: Long = System.currentTimeMillis()
)

class StreamEventManager {

    private val events = mutableListOf<OBSStreamEvent>()
    private var nextId = 1

    fun getEvents(): List<OBSStreamEvent> {
        return events.toList()
    }

    fun addEvent(
        type: StreamEventType,
        username: String,
        message: String,
        platform: StreamingPlatform
    ): Boolean {
        if (username.isBlank() || message.isBlank()) {
            return false
        }

        events.add(
            OBSStreamEvent(
                id = (nextId++).toString(),
                type = type,
                username = username.trim(),
                message = message.trim(),
                platform = platform
            )
        )

        return true
    }

    fun getEventsByType(
        type: StreamEventType
    ): List<OBSStreamEvent> {
        return events.filter {
            it.type == type
        }
    }

    fun removeEvent(id: String): Boolean {
        return events.removeAll {
            it.id == id
        }
    }

    fun clearEvents() {
        events.clear()
        nextId = 1
    }

    fun getLatestEvent(): OBSStreamEvent? {
        return events.lastOrNull()
    }
}
