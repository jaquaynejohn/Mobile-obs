
package com.mobileobs

enum class StreamStatus {
    OFFLINE,
    CONNECTING,
    LIVE,
    STOPPING,
    ERROR
}

data class StreamInfo(
    var status: StreamStatus = StreamStatus.OFFLINE,
    var platform: StreamingPlatform = StreamingPlatform.YOUTUBE,
    var title: String = "My Live Stream",
    var viewers: Int = 0,
    var elapsedSeconds: Long = 0
)

class StreamManager {

    private var streamInfo = StreamInfo()

    fun getStreamInfo(): StreamInfo {
        return streamInfo.copy()
    }

    fun setPlatform(platform: StreamingPlatform) {
        if (streamInfo.status == StreamStatus.OFFLINE) {
            streamInfo.platform = platform
        }
    }

    fun setTitle(title: String): Boolean {
        if (title.isBlank()) return false
        if (streamInfo.status != StreamStatus.OFFLINE) return false

        streamInfo.title = title.trim()
        return true
    }

    fun startStream(): Boolean {
        if (streamInfo.status != StreamStatus.OFFLINE) return false

        streamInfo.status = StreamStatus.CONNECTING
        return true
    }

    fun markLive(): Boolean {
        if (streamInfo.status != StreamStatus.CONNECTING) return false

        streamInfo.status = StreamStatus.LIVE
        return true
    }

    fun stopStream(): Boolean {
        if (
            streamInfo.status != StreamStatus.LIVE &&
            streamInfo.status != StreamStatus.CONNECTING
        ) {
            return false
        }

        streamInfo.status = StreamStatus.OFFLINE
        streamInfo.viewers = 0
        streamInfo.elapsedSeconds = 0
        return true
    }

    fun setViewerCount(count: Int): Boolean {
        if (count < 0) return false

        streamInfo.viewers = count
        return true
    }

    fun setElapsedSeconds(seconds: Long): Boolean {
        if (seconds < 0) return false

        streamInfo.elapsedSeconds = seconds
        return true
    }

    fun setError(): Boolean {
        if (streamInfo.status == StreamStatus.OFFLINE) return false

        streamInfo.status = StreamStatus.ERROR
        return true
    }

    fun reset(): Boolean {
        streamInfo = StreamInfo()
        return true
    }
}
