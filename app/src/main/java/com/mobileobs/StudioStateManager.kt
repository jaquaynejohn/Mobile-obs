
package com.mobileobs

enum class StudioStatus {
    IDLE,
    PREPARING,
    LIVE,
    STOPPING,
    ERROR
}

class StudioStateManager {

    private var status = StudioStatus.IDLE
    private var activeSceneId: String? = null
    private var selectedPlatform: String? = null
    private var errorMessage: String? = null

    fun getStatus(): StudioStatus = status

    fun setStatus(newStatus: StudioStatus) {
        status = newStatus
    }

    fun setActiveScene(sceneId: String?) {
        activeSceneId = sceneId
    }

    fun getActiveScene(): String? = activeSceneId

    fun selectPlatform(platform: String): Boolean {
        if (platform.isBlank()) return false

        selectedPlatform = platform.trim()
        return true
    }

    fun getSelectedPlatform(): String? = selectedPlatform

    fun setError(message: String?) {
        errorMessage = message
        if (message != null) {
            status = StudioStatus.ERROR
        }
    }

    fun getError(): String? = errorMessage

    fun reset() {
        status = StudioStatus.IDLE
        activeSceneId = null
        selectedPlatform = null
        errorMessage = null
    }
}
