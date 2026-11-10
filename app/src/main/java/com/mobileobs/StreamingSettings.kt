
package com.mobileobs

enum class StreamingPlatform {
    TIKTOK,
    YOUTUBE,
    TWITCH,
    CUSTOM_RTMP
}

data class StreamingSettings(
    var platform: StreamingPlatform = StreamingPlatform.YOUTUBE,
    var resolution: String = "720p",
    var frameRate: Int = 30,
    var videoBitrate: Int = 3000,
    var audioBitrate: Int = 128,
    var recordingEnabled: Boolean = false
) {
    fun isValid(): Boolean {
        val validResolutions = listOf(
            "480p",
            "720p",
            "1080p"
        )

        return resolution in validResolutions &&
            frameRate in listOf(30, 60) &&
            videoBitrate in 500..20000 &&
            audioBitrate in 64..320
    }
}

class StreamingSettingsManager {

    private var settings = StreamingSettings()

    fun getSettings(): StreamingSettings {
        return settings.copy()
    }

    fun updateSettings(
        newSettings: StreamingSettings
    ): Boolean {
        if (!newSettings.isValid()) return false

        settings = newSettings.copy()
        return true
    }

    fun setPlatform(
        platform: StreamingPlatform
    ) {
        settings.platform = platform
    }

    fun setResolution(
        resolution: String
    ): Boolean {
        if (resolution !in listOf("480p", "720p", "1080p")) {
            return false
        }

        settings.resolution = resolution
        return true
    }

    fun setFrameRate(
        fps: Int
    ): Boolean {
        if (fps !in listOf(30, 60)) return false

        settings.frameRate = fps
        return true
    }

    fun setVideoBitrate(
        bitrate: Int
    ): Boolean {
        if (bitrate !in 500..20000) return false

        settings.videoBitrate = bitrate
        return true
    }

    fun setAudioBitrate(
        bitrate: Int
    ): Boolean {
        if (bitrate !in 64..320) return false

        settings.audioBitrate = bitrate
        return true
    }

    fun setRecordingEnabled(
        enabled: Boolean
    ) {
        settings.recordingEnabled = enabled
    }
}
