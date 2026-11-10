
package com.mobileobs

enum class DeviceOrientationMode {
    AUTO,
    PORTRAIT,
    LANDSCAPE
}

data class DeviceOrientationSettings(
    var mode: DeviceOrientationMode = DeviceOrientationMode.AUTO,
    var rotatePreview: Boolean = true,
    var keepControlsVisible: Boolean = true
)

class DeviceOrientationManager {

    private var settings = DeviceOrientationSettings()

    fun getSettings(): DeviceOrientationSettings {
        return settings.copy()
    }

    fun setMode(mode: DeviceOrientationMode) {
        settings.mode = mode
    }

    fun setRotatePreview(enabled: Boolean) {
        settings.rotatePreview = enabled
    }

    fun setKeepControlsVisible(enabled: Boolean) {
        settings.keepControlsVisible = enabled
    }

    fun updateSettings(
        newSettings: DeviceOrientationSettings
    ) {
        settings = newSettings.copy()
    }

    fun reset() {
        settings = DeviceOrientationSettings()
    }
}
