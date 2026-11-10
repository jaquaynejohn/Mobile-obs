
package com.mobileobs

enum class StudioOrientation {
    PORTRAIT,
    LANDSCAPE
}

data class StudioLayout(
    var orientation: StudioOrientation = StudioOrientation.PORTRAIT,
    var showPreview: Boolean = true,
    var showScenes: Boolean = true,
    var showSources: Boolean = true,
    var showAudioMixer: Boolean = true,
    var compactMode: Boolean = false
)

class LayoutManager {

    private var layout = StudioLayout()

    fun getLayout(): StudioLayout {
        return layout.copy()
    }

    fun setOrientation(
        orientation: StudioOrientation
    ) {
        layout.orientation = orientation
    }

    fun setPreviewVisible(visible: Boolean) {
        layout.showPreview = visible
    }

    fun setScenesVisible(visible: Boolean) {
        layout.showScenes = visible
    }

    fun setSourcesVisible(visible: Boolean) {
        layout.showSources = visible
    }

    fun setAudioMixerVisible(visible: Boolean) {
        layout.showAudioMixer = visible
    }

    fun setCompactMode(enabled: Boolean) {
        layout.compactMode = enabled
    }

    fun updateLayout(newLayout: StudioLayout) {
        layout = newLayout.copy()
    }
}
