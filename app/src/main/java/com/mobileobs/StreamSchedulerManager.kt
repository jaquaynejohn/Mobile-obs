
package com.mobileobs

data class ScheduledStream(
    val id: String,
    var title: String,
    var platform: StreamingPlatform,
    var scheduledTime: Long,
    var enabled: Boolean = true
)

class StreamSchedulerManager {

    private val scheduledStreams = mutableListOf<ScheduledStream>()
    private var nextId = 1

    fun getScheduledStreams(): List<ScheduledStream> {
        return scheduledStreams.map { it.copy() }
    }

    fun scheduleStream(
        title: String,
        platform: StreamingPlatform,
        scheduledTime: Long
    ): Boolean {
        if (title.isBlank()) return false
        if (scheduledTime <= System.currentTimeMillis()) return false

        scheduledStreams.add(
            ScheduledStream(
                id = (nextId++).toString(),
                title = title.trim(),
                platform = platform,
                scheduledTime = scheduledTime
            )
        )

        return true
    }

    fun updateScheduledStream(
        id: String,
        title: String,
        platform: StreamingPlatform,
        scheduledTime: Long
    ): Boolean {
        if (title.isBlank()) return false
        if (scheduledTime <= System.currentTimeMillis()) return false

        val stream = scheduledStreams.find {
            it.id == id
        } ?: return false

        stream.title = title.trim()
        stream.platform = platform
        stream.scheduledTime = scheduledTime

        return true
    }

    fun setEnabled(
        id: String,
        enabled: Boolean
    ): Boolean {
        val stream = scheduledStreams.find {
            it.id == id
        } ?: return false

        stream.enabled = enabled
        return true
    }

    fun cancelStream(id: String): Boolean {
        return scheduledStreams.removeAll {
            it.id == id
        }
    }

    fun getUpcomingStreams(): List<ScheduledStream> {
        val now = System.currentTimeMillis()

        return scheduledStreams
            .filter { it.enabled && it.scheduledTime > now }
            .sortedBy { it.scheduledTime }
    }

    fun clearSchedule() {
        scheduledStreams.clear()
        nextId = 1
    }
}
