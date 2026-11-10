
package com.mobileobs

data class ScreenCaptureSettings(
    var enabled: Boolean = false,
    var audioEnabled: Boolean = true,
    var captureSystemAudio: Boolean = true,
    var captureMicrophone: Boolean = true,
    var showTouchIndicators: Boolean = false,
    var frameRate: Int = 30
)

class ScreenCaptureManager {

    private var settings = ScreenCaptureSettings()
    private var permissionGranted = false

    fun getSettings(): ScreenCaptureSettings {
        return settings.copy()
    }

    fun isPermissionGranted(): Boolean {
        return permissionGranted
    }

    fun setPermissionGranted(granted: Boolean) {
        permissionGranted = granted

        if (!granted) {
            settings.enabled = false
        }
    }

    fun setEnabled(enabled: Boolean): Boolean {
        if (enabled && !permissionGranted) {
            return false
        }

        settings.enabled = enabled
        return true
    }

    fun setAudioEnabled(enabled: Boolean) {
        settings.audioEnabled = enabled
    }

    fun setSystemAudioEnabled(enabled: Boolean) {
        settings.captureSystemAudio = enabled
    }

    fun setMicrophoneEnabled(enabled: Boolean) {
        settings.captureMicrophone = enabled
    }

    fun setTouchIndicatorsEnabled(enabled: Boolean) {
        settings.showTouchIndicators = enabled
    }

    fun setFrameRate(fps: Int): Boolean {
        if (fps !in listOf(15, 24, 30, 60)) {
            return false
        }

        settings.frameRate = fps
        return true
    }

    fun stopCapture() {
        settings.enabled = false
        permissionGranted = false
    }

    fun updateSettings(
        newSettings: ScreenCaptureSettings
    ): Boolean {
        if (
            newSettings.frameRate !in listOf(15, 24, 30, 60)
        ) {
            return false
        }

        if (newSettings.enabled && !permissionGranted) {
            return false
        }

        settings = newSettings.copy()
        return true
    }
}
