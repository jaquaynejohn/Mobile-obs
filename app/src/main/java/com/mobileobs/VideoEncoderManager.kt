
package com.mobileobs

enum class VideoCodec {
    H264,
    H265
}

data class VideoEncoderSettings(
    var resolution: String = "720p",
    var frameRate: Int = 30,
    var bitrateKbps: Int = 3000,
    var codec: VideoCodec = VideoCodec.H264,
    var keyframeIntervalSeconds: Int = 2
)

class VideoEncoderManager {

    private var settings = VideoEncoderSettings()

    fun getSettings(): VideoEncoderSettings {
        return settings.copy()
    }

    fun setResolution(resolution: String): Boolean {
        if (resolution !in listOf("480p", "720p", "1080p")) {
            return false
        }

        settings.resolution = resolution
        return true
    }

    fun setFrameRate(fps: Int): Boolean {
        if (fps !in listOf(24, 30, 60)) return false

        settings.frameRate = fps
        return true
    }

    fun setBitrate(bitrateKbps: Int): Boolean {
        if (bitrateKbps !in 500..20000) return false

        settings.bitrateKbps = bitrateKbps
        return true
    }

    fun setCodec(codec: VideoCodec) {
        settings.codec = codec
    }

    fun setKeyframeInterval(seconds: Int): Boolean {
        if (seconds !in 1..5) return false

        settings.keyframeIntervalSeconds = seconds
        return true
    }

    fun updateSettings(
        newSettings: VideoEncoderSettings
    ): Boolean {
        if (
            newSettings.resolution !in listOf("480p", "720p", "1080p") ||
            newSettings.frameRate !in listOf(24, 30, 60) ||
            newSettings.bitrateKbps !in 500..20000 ||
            newSettings.keyframeIntervalSeconds !in 1..5
        ) {
            return false
        }

        settings = newSettings.copy()
        return true
    }
}
