
package com.mobileobs

data class StreamSession(
    val id: String,
    val platform: StreamingPlatform,
    val startedAt: Long,
    var endedAt: Long = 0L,
    var peakViewers: Int = 0
)

data class OBSAnalytics(
    var totalStreams: Int = 0,
    var totalStreamTimeMs: Long = 0L,
    var totalRecordings: Int = 0,
    var totalRecordingTimeMs: Long = 0L
)

class AnalyticsManager {

    private val sessions = mutableListOf<StreamSession>()
    private var analytics = OBSAnalytics()
    private var nextId = 1

    private var activeSessionId: String? = null

    fun getAnalytics(): OBSAnalytics {
        return analytics.copy()
    }

    fun getSessions(): List<StreamSession> {
        return sessions.map { it.copy() }
    }

    fun startSession(platform: StreamingPlatform): Boolean {
        if (activeSessionId != null) return false

        val session = StreamSession(
            id = (nextId++).toString(),
            platform = platform,
            startedAt = System.currentTimeMillis()
        )

        sessions.add(session)
        activeSessionId = session.id
        analytics.totalStreams++

        return true
    }

    fun updatePeakViewers(count: Int): Boolean {
        if (count < 0) return false

        val session = sessions.find {
            it.id == activeSessionId
        } ?: return false

        if (count > session.peakViewers) {
            session.peakViewers = count
        }

        return true
    }

    fun endSession(): Boolean {
        val session = sessions.find {
            it.id == activeSessionId
        } ?: return false

        session.endedAt = System.currentTimeMillis()

        val duration = session.endedAt - session.startedAt
        analytics.totalStreamTimeMs += duration.coerceAtLeast(0L)

        activeSessionId = null
        return true
    }

    fun addRecording(durationMs: Long): Boolean {
        if (durationMs < 0) return false

        analytics.totalRecordings++
        analytics.totalRecordingTimeMs += durationMs

        return true
    }

    fun clearAnalytics() {
        sessions.clear()
        analytics = OBSAnalytics()
        activeSessionId = null
        nextId = 1
    }
}
