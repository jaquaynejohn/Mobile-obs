
package com.mobileobs

data class StreamMetrics(
    var viewerCount: Int = 0,
    var bitrateKbps: Int = 0,
    var droppedFrames: Int = 0,
    var totalFrames: Int = 0,
    var startedAt: Long = 0L
) {
    fun getDurationSeconds(): Long {
        if (startedAt <= 0L) return 0L

        return ((System.currentTimeMillis() - startedAt) / 1000L)
            .coerceAtLeast(0L)
    }

    fun getDroppedFramePercentage(): Double {
        if (totalFrames <= 0) return 0.0

        return (droppedFrames.toDouble() / totalFrames) * 100.0
    }
}

class StreamMetricsManager {

    private var metrics = StreamMetrics()

    fun getMetrics(): StreamMetrics {
        return metrics.copy()
    }

    fun updateViewerCount(count: Int) {
        metrics.viewerCount = count.coerceAtLeast(0)
    }

    fun updateBitrate(bitrateKbps: Int) {
        metrics.bitrateKbps = bitrateKbps.coerceAtLeast(0)
    }

    fun updateFrameStats(dropped: Int, total: Int) {
        metrics.droppedFrames = dropped.coerceAtLeast(0)
        metrics.totalFrames = total.coerceAtLeast(0)
    }

    fun startTracking() {
        metrics = StreamMetrics(
            startedAt = System.currentTimeMillis()
        )
    }

    fun reset() {
        metrics = StreamMetrics()
    }
}
