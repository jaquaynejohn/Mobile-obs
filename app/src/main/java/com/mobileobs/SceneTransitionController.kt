
package com.mobileobs

data class SceneTransitionState(
    var isTransitioning: Boolean = false,
    var fromSceneId: String = "",
    var toSceneId: String = "",
    var progress: Float = 0f,
    var startedAt: Long = 0L
)

class SceneTransitionController {

    private var state = SceneTransitionState()

    fun getState(): SceneTransitionState {
        return state.copy()
    }

    fun startTransition(
        fromSceneId: String,
        toSceneId: String
    ): Boolean {
        if (fromSceneId.isBlank() || toSceneId.isBlank()) {
            return false
        }

        if (fromSceneId == toSceneId || state.isTransitioning) {
            return false
        }

        state = SceneTransitionState(
            isTransitioning = true,
            fromSceneId = fromSceneId,
            toSceneId = toSceneId,
            progress = 0f,
            startedAt = System.currentTimeMillis()
        )

        return true
    }

    fun updateProgress(progress: Float): Boolean {
        if (!state.isTransitioning || progress !in 0f..1f) {
            return false
        }

        state.progress = progress

        if (progress >= 1f) {
            completeTransition()
        }

        return true
    }

    fun completeTransition() {
        state.progress = 1f
        state.isTransitioning = false
    }

    fun cancelTransition() {
        state = SceneTransitionState()
    }

    fun isTransitioning(): Boolean {
        return state.isTransitioning
    }
}
