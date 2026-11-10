
package com.mobileobs

enum class CameraFacing {
    FRONT,
    BACK
}

data class CameraSettings(
    var enabled: Boolean = false,
    var facing: CameraFacing = CameraFacing.FRONT,
    var resolution: String = "720p",
    var mirrored: Boolean = true,
    var overlayWidth: Float = 320f,
    var overlayHeight: Float = 240f
)

class CameraSettingsManager {

    private var settings = CameraSettings()

    fun getSettings(): CameraSettings {
        return settings.copy()
    }

    fun setEnabled(enabled: Boolean) {
        settings.enabled = enabled
    }

    fun setFacing(facing: CameraFacing) {
        settings.facing = facing
    }

    fun setResolution(resolution: String): Boolean {
        if (resolution !in listOf("480p", "720p", "1080p")) {
            return false
        }

        settings.resolution = resolution
        return true
    }

    fun setMirrored(mirrored: Boolean) {
        settings.mirrored = mirrored
    }

    fun setOverlaySize(
        width: Float,
        height: Float
    ): Boolean {
        if (width <= 0f || height <= 0f) {
            return false
        }

        settings.overlayWidth = width
        settings.overlayHeight = height
        return true
    }
}
