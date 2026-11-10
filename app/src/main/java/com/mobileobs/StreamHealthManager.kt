
package com.mobileobs

enum class ConnectionQuality {
    EXCELLENT,
    GOOD,
    FAIR,
    POOR,
    DISCONNECTED
}

data class StreamHealth(
    var fps: Int = 0,
    var bitrateKbps: Int = 0,
    var droppedFrames: Int = 0,
    var totalFrames: Long = 0,
    var quality: ConnectionQuality = ConnectionQuality.DISCONNECTED
)

class StreamHealthManager {

    private var health = StreamHealth()

    fun getHealth(): StreamHealth {
        return health.copy()
    }

    fun updateFPS(fps: Int): Boolean {
        if (fps !in 0..120) return false

        health.fps = fps
        return true
    }

    fun updateBitrate(bitrateKbps: Int): Boolean {
        if (bitrateKbps < 0) return false

        health.bitrateKbps = bitrateKbps
        return true
    }

    fun updateDroppedFrames(frames: Int): Boolean {
        if (frames < 0) return false

        health.droppedFrames = frames
        return true
    }

    fun updateTotalFrames(frames: Long): Boolean {
        if (frames < 0) return false

        health.totalFrames = frames
        return true
    }

    fun updateQuality(quality: ConnectionQuality) {
        health.quality = quality
    }

    fun reset() {
        health = StreamHealth()
    }
}
