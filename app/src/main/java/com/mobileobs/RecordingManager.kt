
package com.mobileobs

enum class RecordingStatus {
    IDLE,
    RECORDING,
    PAUSED
}

data class RecordingSettings(
    var resolution: String = "1080p",
    var frameRate: Int = 30,
    var saveAudio: Boolean = true
)

class RecordingManager {

    private var status = RecordingStatus.IDLE

    private var settings = RecordingSettings()

    private var fileName = "MobileOBS_Recording"

    fun getStatus(): RecordingStatus {
        return status
    }

    fun getSettings(): RecordingSettings {
        return settings.copy()
    }

    fun setSettings(
        newSettings: RecordingSettings
    ): Boolean {
        if (
            newSettings.resolution !in
            listOf("480p", "720p", "1080p") ||
            newSettings.frameRate !in listOf(30, 60)
        ) {
            return false
        }

        settings = newSettings.copy()
        return true
    }

    fun startRecording(): Boolean {
        if (status != RecordingStatus.IDLE) {
            return false
        }

        status = RecordingStatus.RECORDING
        return true
    }

    fun pauseRecording(): Boolean {
        if (status != RecordingStatus.RECORDING) {
            return false
        }

        status = RecordingStatus.PAUSED
        return true
    }

    fun resumeRecording(): Boolean {
        if (status != RecordingStatus.PAUSED) {
            return false
        }

        status = RecordingStatus.RECORDING
        return true
    }

    fun stopRecording(): Boolean {
        if (status == RecordingStatus.IDLE) {
            return false
        }

        status = RecordingStatus.IDLE
        return true
    }

    fun setFileName(name: String): Boolean {
        if (name.isBlank()) return false

        fileName = name.trim()
        return true
    }

    fun getFileName(): String {
        return fileName
    }
}
